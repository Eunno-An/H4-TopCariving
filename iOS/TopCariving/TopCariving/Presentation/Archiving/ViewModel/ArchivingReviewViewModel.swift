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
    var bag = Set<AnyCancellable>()
    private let utilityQueue = DispatchQueue(label: "utilityQueue", qos: .utility)
    var loadPage = 1
    let reviewCellData = CurrentValueSubject<[ArchivingReviewCellModel], Never>([])
    private let httpClient: HTTPClientProtocol
    
    // MARK: - LifeCycle
    init(httpClient: HTTPClientProtocol) {
        self.httpClient = httpClient
        //  self.fetchReviewCellData(page: 1)
    }
    
    // MARK: - Helper
    func transform(input: Input) -> Output {
        let output = Output()
        return output
    }
    func fetchArchivingData(page: Int) -> Future<Archiving, Never> {
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
                    print("Promise success")
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
    }
    func fetchReviewCellData(page: Int) {
        fetchArchivingData(page: page).subscribe(on: utilityQueue)
            .sink(receiveValue: { [weak self] receivedValue in
                guard let self = self else { return }
                if page == 1 {
                    let sendValue = convertToReviewCellModels(from: receivedValue)
                    print("send Now!")
                    self.reviewCellData.send(sendValue)
                } else {
                    let oldValue = self.reviewCellData.value
                    let newValue = oldValue + convertToReviewCellModels(from: receivedValue)
                    self.reviewCellData.send(newValue)
                }
            }).store(in: &bag)
    }
    func requestMoreData(page: Int) {
        self.fetchReviewCellData(page: page)
    }
    func convertToReviewCellModels(from archiving: Archiving) -> [ArchivingReviewCellModel] {
        let dateFormatter: DateFormatter = {
            let formatter = DateFormatter()
            formatter.dateFormat = "yy년M월d일"
            return formatter
        }()
        
        var cellModels: [ArchivingReviewCellModel] = []
        
        for archiveFeed in archiving.archiveSearchResponses {
            let selectOptions = archiveFeed.carArchiveResult["선택품목"] ?? []
            let tags = archiveFeed.tags.map { $0.tagContent }
            
            let cellModel = ArchivingReviewCellModel(
                carName: archiveFeed.carArchiveResult["모델"]?.first ?? "",
                searchType: archiveFeed.type,
                date: formatDateString(archiveFeed.dayTime) ?? "정확한 날을 조회할 수 없어요",
                trim: archiveFeed.carArchiveResult["트림"]?.joined(separator: " ") ?? "",
                outColorName: archiveFeed.carArchiveResult["외장색상"]?.first ?? "",
                inColorName: archiveFeed.carArchiveResult["내장색상"]?.first ?? "",
                selectOptions: selectOptions,
                tags: tags
            )
            
            cellModels.append(cellModel)
            
        }
        return cellModels
    }
    func formatDateString(_ dateString: String) -> String? {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        
        if let date = dateFormatter.date(from: dateString) {
            let outputFormatter = DateFormatter()
            outputFormatter.locale = Locale(identifier: "ko_KR")
            outputFormatter.dateFormat = "yy년 MM월 dd일에 시승했어요"
            
            return outputFormatter.string(from: date)
        }
        
        return nil
    }

}
