//
//  RequestError.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/03.
//

import Foundation

enum RequestError: Error {
    case encode
    case decode
    case invalidURL
    case noResponse
    case unauthorized
    case unexpectedStatusCode(Int)
    case unknown(Error)
}

extension RequestError: LocalizedError {
    var errorDescription: String? {
        switch self {
        case .encode:
            return "Encode Error"
        case .decode:
            return "Decode error"
        case .invalidURL:
            return "invalidURL"
        case .noResponse:
            return "noResponse"
        case .unauthorized:
            return "Session expired"
        case .unexpectedStatusCode(let code):
            return "unexpectedStatusCode\(code)"
        case .unknown(let error):
            return "Request Error \(error)"
        }
    }
}
