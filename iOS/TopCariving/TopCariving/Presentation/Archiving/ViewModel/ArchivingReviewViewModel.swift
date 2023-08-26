//
//  ArchivingReviewViewModel.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/25.
//

import Combine
import Foundation

class ArchivingReviewViewModel: ViewModelType {
    enum Process {
        case ready
        case inProcess
        case finished
        case finishedWithEmptyResult
        case failedWithError(error: Error)
    }
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
    private let reviewCellData = CurrentValueSubject<Archiving, Never>(.init(options: [], archiveSearchResponses: []))
    private let httpClient: HTTPClientProtocol
    let process = CurrentValueSubject<Process, Never>(.ready)
    // MARK: - LifeCycle
    init(httpClient: HTTPClientProtocol) {
        self.httpClient = httpClient
        self.fetchReviewCellData(page: 1)
    }
    
    // MARK: - Helper
    func transform(input: Input) -> Output {
        let output = Output()
        return output
    }
    func fetchArchivingData(page: Int) -> AnyPublisher<Archiving, Never> {
        return Future<Archiving, Never> { promise in
            var endPoint = ArchiveReviewEndPoint.getModels(page)
            var urlComponents = URLComponents()
            urlComponents.scheme = endPoint.scheme
            urlComponents.host = endPoint.host
            urlComponents.path = endPoint.path
            urlComponents.queryItems = endPoint.queryItems(withPage: page)
            guard let url = urlComponents.url else {
                print("urlError")
                return
            }
            var request = URLRequest(url: url)
            request.httpMethod = "GET"
            request.addValue("Bearer \(LoginService.shared.myAccessToken)", forHTTPHeaderField: "authorization")
            
            let task = URLSession.shared.dataTask(with: request) { data, response, error in
                if let error = error {
                    print("serverError")
                    return
                }
                
                guard let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 200 else {
                    print("badServerResponse")
                    return
                }
                
                do {
                    let decodedData = try JSONDecoder().decode(ArchiveResponseDTO.self, from: data!)
                    let archiving = decodedData.toDomain()
                    promise(.success(archiving))
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
                }
            }
            task.resume()
        }
        .eraseToAnyPublisher()
    }
    func fetchReviewCellData(page: Int) {
        fetchArchivingData(page: page).subscribe(on: utilityQueue)
            .sink(receiveCompletion: { [weak self] result in
                guard let self = self else { return }
                switch result {
                case .failure(let error):
                    self.process.send(.failedWithError(error: error))
                    break
                default:
                    self.process.send(.finished)
                }
                self.bag.removeAll()
            }, receiveValue: { [weak self] receivedValue in
                guard let self = self else { return }
                if page == 1 {
                    self.reviewCellData.send(receivedValue)
                } else {
                    var newValue = self.reviewCellData.value
                    newValue.options.append(contentsOf: receivedValue.options)
                    newValue.archiveSearchResponses.append(contentsOf: receivedValue.archiveSearchResponses)
                    self.reviewCellData.send(newValue)
                }
            }).store(in: &bag)
    }
    func requestMoreData(page: Int) async {
        self.fetchReviewCellData(page: page)
    }
}
