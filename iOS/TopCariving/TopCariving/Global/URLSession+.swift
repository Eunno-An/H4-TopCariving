//
//  URLSession+.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/26.
//

import Foundation

extension URLSession {
    func data(from url: URL) async throws -> (Data, URLResponse) {
        try await withCheckedThrowingContinuation { continuation in
            let task = self.dataTask(with: url) { data, response, error in
                guard let data = data, let response = response else {
                    let error = error ?? URLError(.badServerResponse)
                    return continuation.resume(throwing: error)
                }
                
                continuation.resume(returning: (data, response))
            }
            
            task.resume()
        }
    }
    func data(for request: URLRequest) async throws -> (Data, URLResponse) {
        try await withCheckedThrowingContinuation { continuation in
            let task = self.dataTask(with: request) { data, response, error in
                guard let data = data, let response = response else {
                    let error = error ?? URLError(.badServerResponse)
                    return continuation.resume(throwing: error)
                }
                
                continuation.resume(returning: (data, response))
            }
            
            task.resume()
        }
    }
    func downloadTask(with url: URL) async throws -> (URL, URLResponse) {
        try await withCheckedThrowingContinuation { continuation in
            let task = self.downloadTask(with: url) { fileURL, response, error in
                if let fileURL = fileURL, let response = response {
                    continuation.resume(returning: (fileURL, response))
                } else {
                    let error = error ?? URLError(.unknown)
                    continuation.resume(throwing: error)
                }
            }
            
            task.resume()
        }
    }
}
