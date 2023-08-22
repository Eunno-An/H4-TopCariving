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
    func isExistAccessToken() {
        
    }
    func emailLogin(url: URL, loginInfo: LoginInfo) {
        let requestData: [String: Any] = [
            "email": loginInfo.email,
            "password": loginInfo.password
        ]
        do {
            let jsonData = try JSONSerialization.data(withJSONObject: requestData, options: [])
            var request = URLRequest(url: url)
            request.httpMethod = "POST"
            request.httpBody = jsonData
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            
            let task = URLSession.shared.dataTask(with: request) { (data, _, error) in
                if let error = error {
                    print("Error: \(error)")
                    return
                }
                guard let data = data else {
                    print("No data received")
                    return
                }
                do {
                    if let jsonResponse = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] {
                        if let accessToken = jsonResponse["accessToken"] as? String,
                            let refreshToken = jsonResponse["refreshToken"] as? String {
                            _ = saveStringToKeychain(value: accessToken, key: "accessToken")
                            _ = saveStringToKeychain(value: refreshToken, key: "refreshToken")
                        } else {
                            print("Tokens not found in response")
                        }
                    } else {
                        print("Invalid JSON response")
                    }
                } catch {
                    print("JSON parsing error: \(error)")
                }
            }
            task.resume()
        } catch {
            print("Json serialization error: \(error)")
        }
    }
    // 액세스 토큰 로그인(키체인에 액세스토큰이 존재 할 때)
    func accessTokenLogin(accessToken: String) async -> TokenInfo {
        
        return TokenInfo(accessToken: "", refreshToken: "")
    }
    // 근데 401에러가 뜬다? 리 이슈 해야함. Authorization 헤더에 Bearer + 공백 + 리프레시토큰을 붙이고 요청. 만약 성공하면 액세스토큰 재저장. 만약 실패하면 키체인 값 다 삭제하고, 로그인 화면으로
}
