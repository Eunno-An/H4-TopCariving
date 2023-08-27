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
        urlComponents.queryItems = endPoint.queryItems
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
        
        do {
            let (data, response) = try await dataTask(with: request)
            
            guard let httpResponse = response as? HTTPURLResponse else {
                return .failure(.noResponse)
            }
            
            switch httpResponse.statusCode {
            case 200...299:
                guard let decodeResponse = try? JSONDecoder().decode(responseModel, from: data) else {
                    return .failure(.decode)
                }
                return .success(decodeResponse)
            case 401:
                return .failure(.unauthorized)
            default:
                return .failure(.unexpectedStatusCode(httpResponse.statusCode))
            }
        } catch {
            return .failure(.unknown(error))
        }
    }
    
    func dataTask(with request: URLRequest) async throws -> (Data, URLResponse) {
        return try await withCheckedThrowingContinuation { continuation in
            URLSession.shared.dataTask(with: request) { data, response, error in
                if let error = error {
                    continuation.resume(throwing: error)
                } else if let data = data, let response = response {
                    continuation.resume(returning: (data, response))
                } else {
                    continuation.resume(throwing: RequestError.unknown(error!))
                }
            }.resume()
        }
    }
}
