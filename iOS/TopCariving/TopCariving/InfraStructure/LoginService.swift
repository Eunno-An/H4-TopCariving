//
//  LoginService.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/22.
//

import Foundation

struct LoginInfo: Codable {
    var email: String
    var password: String
}
struct TokenInfo: Codable {
    var accessToken: String
    var refreshToken: String
}

class LoginService {
    enum LoginResult {
        case success
        case failure(LoginError)
    }
    enum LoginError: Error {
        case invalidURL
        case transportError
        case serverError(code: Int)
        case missingData
        case decodingError
        case tokenNotFound
        case invalidJsonResponse
        case keyChainError
        case other(Error)
    }
    
    func isExistAccessToken() -> Bool {
        KeyChain.getStringFromKeychain(key: "accessToken") != nil
    }
    // swiftlint: disable cyclomatic_complexity
    func emailLogin(url: URL, loginInfo: LoginInfo) async throws -> LoginResult {
        let requestData: [String: Any] = [
            "email": loginInfo.email,
            "password": loginInfo.password
        ]
        var jsonData: Data = Data()
        do {
            jsonData = try JSONSerialization.data(withJSONObject: requestData, options: [])
        } catch {
            NSLog(error.localizedDescription)
            return .failure(.decodingError)
        }
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        return try await withCheckedThrowingContinuation { continuation in
            let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
                if error != nil {
                    continuation.resume(returning: .failure(.transportError))
                }
                guard let data = data else {
                    continuation.resume(returning: .failure(.missingData))
                    return
                }
                guard let httpResponse = response as? HTTPURLResponse else {
                    continuation.resume(returning: .failure(.transportError))
                    return
                }
                switch httpResponse.statusCode {
                case 200:
                    NSLog("Login: statusCode 200")
                default:
                    continuation.resume(returning: .failure(.serverError(code: httpResponse.statusCode)))
                    return
                }
                
                do {
                    if let jsonResponse = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] {
                        if let accessToken = jsonResponse["accessToken"] as? String,
                           let refreshToken = jsonResponse["refreshToken"] as? String {
                            KeyChain.saveStringToKeychain(value: accessToken, key: "accessToken")
                            && KeyChain.saveStringToKeychain(value: refreshToken, key: "refreshToken")
                            ? continuation.resume(returning: .success)
                            : continuation.resume(returning: .failure(.keyChainError))
                        } else {
                            continuation.resume(returning: .failure(.tokenNotFound))
                        }
                    } else {
                        continuation.resume(returning: .failure(.invalidJsonResponse))
                    }
                } catch {
                    continuation.resume(returning: .failure(.other(error)))
                }
            }
            task.resume()
        }
    }
    // swiftlint: enable cyclomatic_complexity
    func reIssue() async throws -> LoginResult {
        guard let url = URL(string: "https://dev.topcariving.com/reissue") else {
            return .failure(.invalidURL)
        }
        var request = URLRequest(url: url)
        let refreshToken: String = KeyChain.getStringFromKeychain(key: "refreshToken") ?? ""
        request.httpMethod = "GET"
        request.setValue("Bearer \(refreshToken)", forHTTPHeaderField: "Authorization")
        return await withCheckedContinuation { continuation in
            let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
                if error != nil {
                    continuation.resume(returning: .failure(.transportError))
                }
                guard let data = data else {
                    continuation.resume(returning: .failure(.missingData))
                    return
                }
                guard let httpResponse = response as? HTTPURLResponse else {
                    continuation.resume(returning: .failure(.transportError))
                    return
                }
                
                switch httpResponse.statusCode {
                case 401:
                    KeyChain.deleteStringFromKeychain(key: "accessToken")
                    && KeyChain.deleteStringFromKeychain(key: "refreshToken")
                    ? continuation.resume(returning: .failure(.serverError(code: 401)))
                    : continuation.resume(returning: .failure(.keyChainError))
                    return
                case 200:
                    NSLog("statusCode 200")
                default:
                    continuation.resume(returning: .failure(.serverError(code: httpResponse.statusCode)))
                    return
                }
                do {
                    if let jsonResponse = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] {
                        if let accessToken = jsonResponse["accessToken"] as? String,
                           let refreshToken = jsonResponse["refreshToken"] as? String {
                            KeyChain.saveStringToKeychain(value: accessToken, key: "accessToken")
                            && KeyChain.saveStringToKeychain(value: refreshToken, key: "refreshToken")
                            ? continuation.resume(returning: .success)
                            : continuation.resume(returning: .failure(.keyChainError))
                        } else {
                            continuation.resume(returning: .failure(.tokenNotFound))
                        }
                    } else {
                        continuation.resume(returning: .failure(.invalidJsonResponse))
                    }
                } catch {
                    continuation.resume(returning: .failure(.other(error)))
                }
            }
            task.resume()
        }
    }
}
