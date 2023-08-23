//
//  ModelOptionViewModel.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/22.
//

import Combine
import Foundation

class ModelOptionViewModel: ViewModelType {
    // MARK: - Input
    struct Input {
        let viewDidLoadPublisher: AnyPublisher<Void, Never>
        let tapNextButtonPublisher: AnyPublisher<Void, Never>
        let tapCarIndexPublisher: AnyPublisher<Int, Never>
    }
    // MARK: - Output
    struct Output {
        let modelSubject = PassthroughSubject<[CarSummaryContainerModel], Never>()
        let unauthorizedSubject = PassthroughSubject<Void, Never>()
        let errorSubject = PassthroughSubject<String, Never>()
        let pushSubject = PassthroughSubject<SuccessResponseLong, Never>()
        let priceSubject = PassthroughSubject<String, Never>()
    }
    // MARK: - Dependency
    var bag = Set<AnyCancellable>()
    let httpClient: HTTPClientProtocol
    
    // MARK: - State
    var models = [Model]()
    var selectedIndex: Int?
    
    // MARK: - LifeCycle
    init(httpClient: HTTPClientProtocol) {
        self.httpClient = httpClient
    }
    
    // MARK: - Helper
    func transform(input: Input) -> Output {
        let output = Output()
        input.viewDidLoadPublisher.sink(receiveValue: { [weak self] _ in
            guard let self else { return }
            Task { [weak self] in
                guard let self else { return }
                let result = await self.httpClient.sendRequest(
                    endPoint: TrimEndPoint.getModels,
                    responseModel: [ModelResponseDTO].self
                ).map { $0.map { $0.toDomain() } }
                
                switch result {
                case .success(let success):
                    let model = success.enumerated().map { offset, modelDTO in
                        let icons = modelDTO.photos.map { ($0.photoPNGURL, $0.content) }
                        return CarSummaryContainerModel.init(
                            icons: icons,
                            title: "\(offset+1). " + modelDTO.optionName,
                            price: String.decimalStyle(from: Int(modelDTO.price))
                        )
                    }
                    output.modelSubject.send(model)
                    self.models = success
                case .failure(let failure):
                    switch failure {
                    case .unauthorized:
                        output.unauthorizedSubject.send(())
                    default:
                        output.errorSubject.send(failure.localizedDescription)
                    }
                }
            }
        }).store(in: &bag)
        
        input.tapNextButtonPublisher
            .map { [weak self] () -> Int? in
                guard let self else { return nil }
                return self.selectedIndex
            }
            .sink(receiveValue: { [weak self] index in
                guard let self,
                      let index = index else { return }
                guard index == 0 else {
                    output.errorSubject.send("현재 지원하지 않는 트림입니다.")
                    return
                }
                Task { [weak self] in
                    guard let self else { return }
                    let result = await self.httpClient.sendRequest(
                        endPoint: TrimEndPoint.postModels(.init(carOptionId: self.models[index].id,
                                                                archivingId: nil)),
                        responseModel: SuccessResponseLong.self
                    )
                    switch result {
                    case .success(let success):
                        output.pushSubject.send(success)
                    case .failure(let failure):
                        switch failure {
                        case .unauthorized:
                            output.unauthorizedSubject.send(())
                        default:
                            output.errorSubject.send(failure.localizedDescription)
                        }
                    }
                }
        }).store(in: &bag)
        
        input.tapCarIndexPublisher.sink(receiveValue: { [weak self] index in
            guard let self else { return }
            self.selectedIndex = index
            guard (0..<models.count) ~= index else { return }
            output.priceSubject.send("\(String.decimalStyle(from: Int(models[index].price)))")
        }).store(in: &bag)
        return output
    }
}
