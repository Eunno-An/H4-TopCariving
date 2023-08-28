//
//  ExteriorColorSelectionViewModel.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/24.
//

import Combine
import Foundation

func colorURL(_ index: Int, _ name: String) -> String {
    "https://d37c20hjk1kqzz.cloudfront.net/360/\(name)/image_0\(String(format: "%02d", index)).png"
}

class ExteriorColorSelectionViewModel: ViewModelType {
    // MARK: - Input
    struct Input {
        let viewDidLoadPublisher: AnyPublisher<Void, Never>
        let tapNextButtonPublisher: AnyPublisher<Void, Never>
        let tapColorIndexPublisher: AnyPublisher<Int, Never>
        let rotateIndexPublisher: AnyPublisher<Int, Never>
    }
    // MARK: - Output
    struct Output {
        let colorImageSubject = PassthroughSubject<[String], Never>()
        let unauthorizedSubject = PassthroughSubject<Void, Never>()
        let errorSubject = PassthroughSubject<String, Never>()
        let pushSubject = PassthroughSubject<SuccessResponseLong, Never>()
        let carImageSubject = PassthroughSubject<String, Never>()
        let colorNameSubject = PassthroughSubject<String, Never>()
        let tagsSubject = PassthroughSubject<[String], Never>()
    }
    // MARK: - Dependency
    var bag = Set<AnyCancellable>()
    let httpClient: HTTPClientProtocol
    
    // MARK: - State
    var archivingID: Int64
    var colors = [ExteriorColor]()
    var selectedIndex: Int = 0

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
        transformTapColorIndex(input, output)
        transformRotateIndex(input, output)
        return output
    }
    private func transformViewDidLoad(_ input: Input, _ output: Output) {
        input.viewDidLoadPublisher.sink(receiveValue: { [weak self] _ in
            guard let self else { return }
            Task { [weak self] in
                guard let self else { return }
                let result = await self.httpClient.sendRequest(
                    endPoint: ColorEndPoint.getExteriors,
                    responseModel: [ExteriorColorResponseDTO].self
                ).map { $0.map { $0.toDomain() } }
                
                switch result {
                case .success(let success):
                    let model = success.map { $0.colorUrl }
                    output.colorImageSubject.send(model)
                    self.colors = success
                    guard let initialChoice = colors.first else { return }
                    output.colorNameSubject.send(initialChoice.optionName)
                    output.tagsSubject.send(initialChoice.tagResponses.map { $0.tagContent })
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
//        input.tapNextButtonPublisher
//            .compactMap { [weak self] () -> Int? in
//                guard let self else { return nil }
//                return self.selectedIndex
//            }
//            .sink(receiveValue: { [weak self] index in
//                guard let self else { return }
//                guard (0..<colors.count) ~= index else {
//                    output.errorSubject.send("에러가 발생했습니다.")
//                    return
//                }
//                Task { [weak self] in
//                    guard let self else { return }
//                    let result = await self.httpClient.sendRequest(
//                        endPoint: TrimEndPoint.postEngines(.init(carOptionId: self.colors[index].carOptionId,
//                                                                archivingId: archivingID)),
//                        responseModel: SuccessResponseLong.self
//                    )
//                    switch result {
//                    case .success(let success):
//                        output.pushSubject.send(success)
//                    case .failure(let failure):
//                        switch failure {
//                        case .unauthorized:
//                            output.unauthorizedSubject.send(())
//                        default:
//                            output.errorSubject.send(failure.localizedDescription)
//                        }
//                    }
//                }
//        }).store(in: &bag)
    }
    private func transformTapColorIndex(_ input: Input, _ output: Output) {
        input.tapColorIndexPublisher.sink(receiveValue: { [weak self] index in
            guard let self else { return }
            self.selectedIndex = index
            guard (0..<colors.count) ~= index else { return }
            output.colorNameSubject.send(colors[index].optionName)
            output.tagsSubject.send(colors[index].tagResponses.map { $0.tagContent })
            guard let colorName = car360ColorImage(from: self.colors[index].colorUrl) else { return }
            output.carImageSubject.send(colorURL(index+1, colorName))
        }).store(in: &bag)
    }
    private func transformRotateIndex(_ input: Input, _ output: Output) {
        input.rotateIndexPublisher.sink(receiveValue: { [weak self] index in
            guard let self else { return }
            
            if colors.count == 0 {
                output.carImageSubject.send(colorURL(1, "abyss"))
                return
            }
            guard let colorName = car360ColorImage(from: self.colors[self.selectedIndex].colorUrl) else { return }
            output.carImageSubject.send(colorURL(index+1, colorName == "black" ? "abyss" : colorName))
        }).store(in: &bag)
    }
    private func car360ColorImage(from photoURL: String) -> String? {
        guard let url = URL(string: photoURL)?.lastPathComponent else { return nil }
        let colorName = url.replacingOccurrences(of: ".png", with: "")
        return colorName == "black" ? "abyss" : colorName
    }
}
