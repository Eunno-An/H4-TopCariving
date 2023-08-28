//
//  ArchiveReviewEndPoint.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/25.
//

import Foundation

enum ArchiveReviewEndPoint {
    case getModels(Int)
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
        let baseHeader = ["authorization": "Bearer \(LoginService.shared.myAccessToken)"]
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
    
    var queryItems: [URLQueryItem] {
        switch self {
        case .getModels(let page):
            return queryItems(withPage: page)
        }
    }
    
    func queryItems(withPage page: Int) -> [URLQueryItem] {
        return [
            URLQueryItem(name: "pageNumber", value: String(page)),
            URLQueryItem(name: "pageSize", value: "10")
        ]
    }
}
