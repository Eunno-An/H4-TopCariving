//
//  HTTPClient.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/03.
//

import Foundation

protocol HTTPClientProtocol {
    func sendRequest<T: Decodable>(
        endPoint: EndPoint,
        responseModel: T.Type
    ) async -> Result<T, RequestError>
}

class HTTPClient: HTTPClientProtocol {
    func sendRequest<T: Decodable>(
        endPoint: EndPoint,
        responseModel: T.Type
    ) async -> Result<T, RequestError> {
        var urlComponents = URLComponents()
        urlComponents.scheme = endPoint.scheme
        urlComponents.host = endPoint.host
        urlComponents.path = endPoint.path
        
        guard let url = urlComponents.url else {
            return .failure(.invalidURL)
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = endPoint.method.rawValue
        request.allHTTPHeaderFields = endPoint.header
        
        if let body = endPoint.body {
            guard let encodedBody = try? JSONEncoder().encode(body) else { return .failure(.encode) }
            request.httpBody = encodedBody
        }
        
        var (data, response): (Data, URLResponse)
        do {
            (data, response) = try await URLSession.shared.data(for: request, delegate: nil)
        } catch {
            return .failure(.unknown(error))
        }
        
        guard let response = response as? HTTPURLResponse else {
            return .failure(.noResponse)
        }
        switch response.statusCode {
        case 200...299:
            guard let decodeResponse = try? JSONDecoder().decode(responseModel, from: data) else {
                return .failure(.decode)
            }
            return .success(decodeResponse)
        case 401:
            return .failure(.unauthorized)
        default:
            return .failure(.unexpectedStatusCode(response.statusCode))
        }
    }
}
