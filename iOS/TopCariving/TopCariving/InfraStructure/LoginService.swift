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
        case other(Error)
    }
    
    func isExistAccessToken() -> Bool {
        KeyChain.getStringFromKeychain(key: "accessToken") != nil
    }
    
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
                if let error = error {
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
                    Task {
                        do {
                            _ = try await self.reIssue()
                            continuation.resume(returning: .success)
                        } catch {
                            NSLog(error.localizedDescription)
                        }
                    }
                    return
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
                            _ = KeyChain.saveStringToKeychain(value: accessToken, key: "accessToken")
                            _ = KeyChain.saveStringToKeychain(value: refreshToken, key: "refreshToken")
                            continuation.resume(returning: .success)
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
                if let error = error {
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
                    _ = KeyChain.deleteStringFromKeychain(key: "accessToken")
                    _ = KeyChain.deleteStringFromKeychain(key: "refreshToken")
                    continuation.resume(returning: .failure(.serverError(code: 401)))
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
                            NSLog("reIssue Ok")
                            _ = KeyChain.saveStringToKeychain(value: accessToken, key: "accessToken")
                            _ = KeyChain.saveStringToKeychain(value: refreshToken, key: "refreshToken")
                            continuation.resume(returning: .success)
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
    
    // 근데 401에러가 뜬다? 리 이슈 해야함. Authorization 헤더에 Bearer + 공백 + 리프레시토큰을 붙이고 요청. 만약 성공하면 액세스토큰 재저장. 만약 실패하면 키체인 값 다 삭제하고, 로그인 화면으로
}
