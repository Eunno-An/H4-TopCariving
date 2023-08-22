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
    var accessToken: String
}
struct TokenInfo: Codable {
    var accessToken: String
    var refreshToken: String
}

class LoginService {
    static let shared = LoginService()
    func login(url: URL, loginInfo: LoginInfo)  async throws -> TokenInfo {
        let requestData = [
            "email": loginInfo.email,
            "password": loginInfo.password
        ]
        let jsonData = try JSONSerialization.data(withJSONObject: requestData, options: [])
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.httpBody = jsonData
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        let (data, response) = try await URLSession.shared.data(for: request)
        guard let httpResponse = response as? HTTPURLResponse,
              httpResponse.statusCode == 200 else {
            return .init(accessToken: "", refreshToken: "")
        }
        let tokenInfo = try JSONDecoder().decode(TokenInfo.self, from: data)
        return tokenInfo
    }
}
