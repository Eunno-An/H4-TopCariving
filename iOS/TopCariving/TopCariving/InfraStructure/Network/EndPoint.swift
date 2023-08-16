//
//  EndPoint.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/03.
//

import Foundation

protocol EndPoint {
    var scheme: String { get }
    var host: String { get }
    var path: String { get }
    var method: RequestMethod { get }
    var header: [String: String]? { get }
    var body: [String: String]? { get }
}

extension EndPoint {
    var scheme: String {
        return "https"
    }

    var host: String {
        return ""
    }
}