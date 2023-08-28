//
//  DrivingMethodSelectionViewModel.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/24.
//

import Combine
import Foundation

class DrivingMethodSelectionViewModel: ViewModelType {
    // MARK: - Input
    struct Input {
        let viewDidLoadPublisher: AnyPublisher<Void, Never>
        let tapNextButtonPublisher: AnyPublisher<Void, Never>
        let tapDrivingMethodIndexPublisher: AnyPublisher<Int, Never>
    }
    // MARK: - Output
    struct Output {
        let modelSubject = PassthroughSubject<[TrimSelectionModel], Never>()
        let unauthorizedSubject = PassthroughSubject<Void, Never>()
        let errorSubject = PassthroughSubject<String, Never>()
        let pushSubject = PassthroughSubject<SuccessResponseLong, Never>()
        let priceSubject = PassthroughSubject<String, Never>()
        let trimImageSubject = PassthroughSubject<String, Never>()
        let trimNameSubject = PassthroughSubject<String, Never>()
    }
    // MARK: - Dependency
    var bag = Set<AnyCancellable>()
    let httpClient: HTTPClientProtocol
    
    // MARK: - State
    var archivingID: Int64
    var options = [OptionResponse]()
    var selectedIndex: Int?
    
    // MARK: - LifeCycle
    init(httpClient: HTTPClientProtocol, archivingID: Int64) {
        self.httpClient = httpClient
        self.archivingID = archivingID
    }
    
    // MARK: - Helper
    func transform(input: Input) -> Output {
        let output = Output()
        transformViewDidLoad(input, output)
        transformTapNextButton(input, output)
        transformTapDrivingMethodIndex(input, output)
        return output
    }
    private func transformViewDidLoad(_ input: Input, _ output: Output) {
        input.viewDidLoadPublisher.sink(receiveValue: { [weak self] _ in
            guard let self else { return }
            Task { [weak self] in
                guard let self else { return }
                let result = await self.httpClient.sendRequest(
                    endPoint: TrimEndPoint.getDrivingMethods,
                    responseModel: [OptionResponseDTO].self
                ).map { $0.map { $0.toDomain() } }
                
                switch result {
                case .success(let success):
                    let model = success.enumerated().map { offset, modelDTO in
                        return TrimSelectionModel.init(title: "\(offset + 1). " + modelDTO.optionName,
                                                       price: "+" + String.decimalStyle(from: modelDTO.price),
                                                       description: modelDTO.optionDetail)
                    }

                    output.modelSubject.send(model)
                    self.options = success
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
    }
    private func transformTapNextButton(_ input: Input, _ output: Output) {
        input.tapNextButtonPublisher
            .map { [weak self] () -> Int? in
                guard let self else { return nil }
                return self.selectedIndex
            }
            .sink(receiveValue: { [weak self] index in
                guard let self,
                      let index = index else { return }
                guard (0..<options.count) ~= index else {
                    output.errorSubject.send("에러가 발생했습니다.")
                    return
                }
                Task { [weak self] in
                    guard let self else { return }
                    let result = await self.httpClient.sendRequest(
                        endPoint: TrimEndPoint.postDrivingMethods(.init(carOptionId: self.options[index].carOptionId,
                                                                archivingId: archivingID)),
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
    }
    private func transformTapDrivingMethodIndex(_ input: Input, _ output: Output) {
        input.tapDrivingMethodIndexPublisher.sink(receiveValue: { [weak self] index in
            guard let self else { return }
            self.selectedIndex = index
            guard (0..<options.count) ~= index else { return }
            output.priceSubject.send("\(String.decimalStyle(from: Int(options[index].price)))")
            output.trimImageSubject.send(options[index].photoUrl)
            output.trimNameSubject.send(options[index].optionName)
        }).store(in: &bag)
    }
}
