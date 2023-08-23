//
//  TrimEndPoint.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/23.
//

import Foundation

enum TrimEndPoint {
    case getModels
    case postModels(SelectOptionRequestDTO)
}
extension TrimEndPoint: EndPoint {
    var path: String {
        switch self {
        case .getModels, .postModels:
            return "/api/options/trims/models"
        }
    }
    
    var method: RequestMethod {
        switch self {
        case .getModels:
            return .get
        case .postModels:
            return .post
        }
    }
    
    var header: [String: String]? {
        // swiftlint: disable line_length
        var baseHeader = ["authorization": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2OTIxNjk5NzAsImV4cCI6MTcyMzcwNTk3MCwiaXNzIjoiVG9wQ2FyaXZpbmciLCJzdWIiOiIxIn0.p1bkF0pLsHkobfdkyPyGBjaClOHDhXbUFpeagBUWvx4"]
        switch self {
        case .getModels:
            return baseHeader
        case .postModels:
            baseHeader["accept"] = "*/*"
            baseHeader["Content-Type"] = "application/json"
            return baseHeader
        }
    }
    
    var body: Encodable? {
        switch self {
        case .postModels(let selectOption):
            return selectOption
        default:
            return nil
        }
    }
}
