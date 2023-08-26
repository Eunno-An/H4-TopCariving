//
//  ArchivingReviewViewModel.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/25.
//

import Combine
import Foundation

class ArchivingReviewViewModel: ViewModelType {
    // MARK: - Input
    struct Input {
        let viewDidLoadPublisher: AnyPublisher<Void, Never>
    }
    // MARK: - Output
    struct Output {
        
    }
    // MARK: - Dependency
    private var bag = Set<AnyCancellable>()
    private let utilityQueue = DispatchQueue(label: "utilityQueue", qos: .utility)
    private var loadPage = 1
    private let reviewCellData = CurrentValueSubject<[ArchivingReviewCellModel], Never>([])
    private let httpClient: HTTPClientProtocol
    // MARK: - LifeCycle
    init(httpClient: HTTPClientProtocol) {
        self.httpClient = httpClient
    }
    
    // MARK: - Helper
    func transform(input: Input) -> Output {
        let output = Output()
        return output
    }
    func fetchReviewCellData(page: Int) async throws -> ArchiveResponseDTO {
        return try await withCheckedThrowingContinuation { continuation in
            var endPoint = ArchiveReviewEndPoint.getModels(page)
            var urlComponents = URLComponents()
            urlComponents.scheme = endPoint.scheme
            urlComponents.host = endPoint.host
            urlComponents.path = endPoint.path
            urlComponents.queryItems = endPoint.queryItems(withPage: page)
            guard let url = urlComponents.url else {
                print("urlError")
                continuation.resume(throwing: URLError(.badURL))
                return
            }
            var request = URLRequest(url: url)
            request.httpMethod = "GET"
            request.addValue("Bearer \(LoginService.shared.myAccessToken)", forHTTPHeaderField: "authorization")

            let task = URLSession.shared.dataTask(with: request) { data, response, error in
                if let error = error {
                    print("serverError")
                    continuation.resume(throwing: error)
                    return
                }

                guard let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 200 else {
                    print("badServerResponse")
                    continuation.resume(throwing: URLError(.badServerResponse))
                    return
                }

                do {
                    let decodedData = try JSONDecoder().decode(ArchiveResponseDTO.self, from: data!)
                    continuation.resume(returning: decodedData)
                } catch let decodingError {
                    
                    if let underlyingError = decodingError as? DecodingError {
                        switch underlyingError {
                        case .dataCorrupted(let context):
                            print("Data Corrupted:", context.debugDescription)
                        case .keyNotFound(let key, let context):
                            print("Key '\(key)' not found:", context.debugDescription)
                        case .typeMismatch(let type, let context):
                            print("Type '\(type)' mismatch:", context.debugDescription)
                        case .valueNotFound(let type, let context):
                            print("Value '\(type)' not found:", context.debugDescription)
                        @unknown default:
                            print("An unknown decoding error occurred.")
                        }
                    } else {
                        print("An unknown error occurred.")
                    }
                    continuation.resume(throwing: decodingError)
                }
            }
            task.resume()
        }
    }
}
