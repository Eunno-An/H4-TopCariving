//
//  ColorEndPoint.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/24.
//

import Foundation

enum ColorEndPoint {
    case getNames
    case getExteriors
    case postExteriors
}
extension ColorEndPoint: EndPoint {
    var queryItems: [URLQueryItem] {
        []
    }
    
    var path: String {
        switch self {
        case .getNames:
            return "/api/options/colors"
        case .getExteriors, .postExteriors:
            return "/api/options/colors/exteriors"
        }
    }
    
    var method: RequestMethod {
        switch self {
        case .getNames, .getExteriors:
            return .get
        case .postExteriors:
            return .post
        }
    }
    
    var header: [String: String]? {
        var baseHeader = ["authorization": "Bearer \(LoginService.shared.myAccessToken)"]
        switch self {
        case .getNames:
            return nil
        case .getExteriors:
            return baseHeader
        case .postExteriors:
            baseHeader["accept"] = "*/*"
            baseHeader["Content-Type"] = "application/json"
            return baseHeader
        }
    }
    
    var body: Encodable? {
        switch self {
        default:
            return nil
        }
    }
}
