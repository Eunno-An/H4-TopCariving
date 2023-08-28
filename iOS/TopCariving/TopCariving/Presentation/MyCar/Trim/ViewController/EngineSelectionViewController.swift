//
//  EngineSelectionViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/23.
//

import Combine
import UIKit

class EngineSelectionViewController: TrimSelectionViewController {
    // MARK: - UI properties
    
    // MARK: - Properties
    let viewModel: EngineSelectionViewModel
    
    // MARK: - Lifecycles
    init(viewModel: EngineSelectionViewModel) {
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
    }
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        setProgress(to: .engine)
        bind()
    }
    
    // MARK: - Helpers
    private func bind() {
        let output = viewModel.transform(input: .init(viewDidLoadPublisher: Just(()).eraseToAnyPublisher(),
                                                      tapNextButtonPublisher: footerView.tapNextButton.eraseToAnyPublisher(),
                                                      tapEngineIndexPublisher: foldableStackView.tapSubject.eraseToAnyPublisher()))
        output.errorSubject
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] error in
                guard let self else { return }
                self.showAlert(with: NSAttributedString(string: error), acceptTitle: "확인", acceptHandler: {})
            }).store(in: &bag)
        
        output.unauthorizedSubject
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] in
                guard let self else { return }
                self.showAlert(with: NSAttributedString(string: "인증에 실패하였습니다."),
                               acceptTitle: "로그인",
                               acceptHandler: { [weak self] in
                    guard let self else { return }
                    self.navigationController?.popToRootViewController(animated: true)
                })
            }).store(in: &bag)
        
        output.pushSubject
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] archivingID in
                guard let self else { return }
                let httpClient = HTTPClient()
                let viewModel = BodyTypeSelectionViewModel(httpClient: httpClient,
                                                                archivingID: archivingID.data)
                let viewController = BodyTypeSelectionViewController(viewModel: viewModel)
                navigationController?.pushViewController(viewController, animated: true)
            }).store(in: &bag)
        
        output.modelSubject
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] models in
                guard let self else { return }
                setContainer(with: models)
            }).store(in: &bag)
        
        output.trimImageSubject
            .sink(receiveValue: { [weak self] image in
                guard let self else { return }
                trimImageView.setAsyncImage(url: image)
            }).store(in: &bag)
        
        output.trimNameSubject
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] name in
                guard let self else { return }
                trimReviewedView.setUp(with: name)
            }).store(in: &bag)
        
        output.priceSubject
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] price in
                guard let self else { return }
                footerView.setPrice(to: price)
            }).store(in: &bag)
    }
    private func setContainer(with models: [EngineModel]) {
        models.forEach {
            let container = EngineSelectionContainer()
            container.setUp(with: $0)
            foldableStackView.addArrangedSubview(container)
        }
    }
}
