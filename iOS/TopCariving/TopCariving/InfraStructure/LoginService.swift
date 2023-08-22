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
    func isExistAccessToken() -> Bool {
        KeyChain.getStringFromKeychain(key: "accessToken") != nil
    }
    
    func emailLogin(url: URL, loginInfo: LoginInfo) async throws -> Bool {
        let requestData: [String: Any] = [
            "email": loginInfo.email,
            "password": loginInfo.password
        ]
        var jsonData: Data = Data()
        do {
            jsonData = try JSONSerialization.data(withJSONObject: requestData, options: [])
        } catch {
            NSLog(error.localizedDescription)
            return false
        }
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        return try await withCheckedThrowingContinuation { continuation in
            let task = URLSession.shared.dataTask(with: request) { (data, _, error) in
                if let error = error {
                    continuation.resume(throwing: error)
                    return
                }
                guard let data = data else {
                    continuation.resume(returning: false) // Login failed
                    return
                }
                do {
                    if let jsonResponse = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] {
                        if let accessToken = jsonResponse["accessToken"] as? String,
                           let refreshToken = jsonResponse["refreshToken"] as? String {
                            print(accessToken)
                            print(refreshToken)
                            _ = KeyChain.saveStringToKeychain(value: accessToken, key: "accessToken")
                            _ = KeyChain.saveStringToKeychain(value: refreshToken, key: "refreshToken")
                            continuation.resume(returning: true)
                        } else {
                            print("Tokens not found in response")
                            continuation.resume(returning: false)
                        }
                    } else {
                        print("Invalid JSON response")
                        continuation.resume(returning: false)
                    }
                } catch {
                    print("JSON parsing error: \(error)")
                    continuation.resume(returning: false)
                }
            }
            task.resume()
        }
    }

    
    // 근데 401에러가 뜬다? 리 이슈 해야함. Authorization 헤더에 Bearer + 공백 + 리프레시토큰을 붙이고 요청. 만약 성공하면 액세스토큰 재저장. 만약 실패하면 키체인 값 다 삭제하고, 로그인 화면으로
}
