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
    func fetchArchivingData(page: Int) -> AnyPublisher<Archiving, Never> {
        let endPoint = ArchiveReviewEndPoint.getModels(page)
        return Future<Archiving, Never> { promise in
            Task {
                let result = await self.httpClient.sendRequest(
                    endPoint: endPoint,
                    responseModel: ArchiveResponseDTO.self)
                switch result {
                case .success(let archivingResponseData):
                    let archivingData: Archiving = archivingResponseData.toDomain()
                    promise(.success(archivingData))
                case .failure(let error):
                    NSLog(error.localizedDescription)
                }
            }
        }.eraseToAnyPublisher()
    }
    func fetchReviewCellData(page: Int) {
        fetchArchivingData(page: page).subscribe(on: utilityQueue)
            .sink(receiveValue: { [weak self] receivedValue in
                guard let self = self else { return }
                if page == 1 {
                    let sendValue = convertToReviewCellModels(from: receivedValue)
                    DispatchQueue.main.async {
                        self.reviewCellData.send(sendValue)
                    }
                } else {
                    let oldValue = self.reviewCellData.value
                    let newValue = oldValue + convertToReviewCellModels(from: receivedValue)
                    DispatchQueue.main.async {
                        self.reviewCellData.send(newValue)
                    }
                }
            }).store(in: &bag)
    }
    func requestMoreData(page: Int) {
        self.fetchReviewCellData(page: page)
        loadPage += 1
    }
    func convertToReviewCellModels(from archiving: Archiving) -> [ArchivingReviewCellModel] {
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
