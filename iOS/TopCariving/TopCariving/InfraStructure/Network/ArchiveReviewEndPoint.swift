//
//  ArchiveReviewEndPoint.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/25.
//

import Foundation

enum ArchiveReviewEndPoint {
    case getModels
}
extension ArchiveReviewEndPoint: EndPoint {
    var path: String {
        switch self {
        case .getModels:
            return "/api/archiving/result"
        }
    }
    
    var method: RequestMethod {
        switch self {
        case .getModels:
            return .get
        }
    }
    
    var header: [String: String]? {
        var baseHeader = ["authorization": "Bearer \(LoginService.shared.accessToken)"]
        switch self {
        case .getModels:
            return baseHeader
        }
    }
    
    var body: Encodable? {
        switch self {
        case .getModels:
            return nil
        }
    }
}
