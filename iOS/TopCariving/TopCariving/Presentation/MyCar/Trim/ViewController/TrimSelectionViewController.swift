//
//  TrimSelectionViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/20.
//

import UIKit

class TrimSelectionViewController: BaseMyCarViewController {
    // MARK: - UI properties
    private let scrollView: UIScrollView = UIScrollView()
    private let engineImageView = UIImageView()
    private let trimReviewedView = TrimReviewedView()
    private let foldableStackView = FoldableStackView()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setEvent()
//        testEngine()
//        testBodyType()
        testDrivingMethod()
    }
    
    // MARK: - Helpers
    override func setUI() {
        super.setUI()
        setProgress(to: .engine)
        view.backgroundColor = .white
        view.addSubview(scrollView)
        [engineImageView, trimReviewedView, foldableStackView].forEach {
            scrollView.addSubview($0)
        }
    }
    override func setLayout() {
        super.setLayout()
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        engineImageView.translatesAutoresizingMaskIntoConstraints = false

        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: progressView.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: footerView.topAnchor),
            
            engineImageView.topAnchor.constraint(equalTo: scrollView.contentLayoutGuide.topAnchor),
            engineImageView.leadingAnchor.constraint(equalTo: scrollView.leadingAnchor),
            engineImageView.trailingAnchor.constraint(equalTo: scrollView.trailingAnchor),
            engineImageView.widthAnchor.constraint(equalTo: scrollView.widthAnchor),
            engineImageView.heightAnchor.constraint(equalTo: view.widthAnchor, multiplier: 0.63),

            trimReviewedView.topAnchor.constraint(equalTo: engineImageView.bottomAnchor, constant: 10),
            trimReviewedView.leadingAnchor.constraint(equalTo: scrollView.leadingAnchor, constant: 16),
            trimReviewedView.trailingAnchor.constraint(equalTo: scrollView.trailingAnchor, constant: -16),
            trimReviewedView.heightAnchor.constraint(equalToConstant: 36),

            foldableStackView.topAnchor.constraint(equalTo: trimReviewedView.bottomAnchor, constant: 22),
            foldableStackView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            foldableStackView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            foldableStackView.bottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor, constant: -8)
        ])
    }
    func setEvent() {
        foldableStackView.tapSubject.sink(receiveValue: { [weak self] index in
            guard let self else { return }
        })
        .store(in: &bag)
    }
    private func testEngine() {
        setProgress(to: .engine)
        let trim2 = EngineSelectionContainer()
        trim2.setUp(with: .init(title: "1. 디젤 2.2",
                                price: "+1,480,000",
                                description: "높은 토크로 파워풀한 드라이빙이 가능하며, 차급대비 연비 효율이 우수합니다.",
                                outputDescription: "202/3,800PS/rpm",
                                torqueDescription: "45.0/1,750~2,750kgf-m/rpm"))
        let trim3 = EngineSelectionContainer()
        trim3.setUp(with: .init(title: "1. 디젤 2.2",
                                price: "+1,480,000",
                                description: "높은 토크로 파워풀한 드라이빙이 가능하며, 차급대비 연비 효율이 우수합니다.",
                                outputDescription: "202/3,800PS/rpm",
                                torqueDescription: "45.0/1,750~2,750kgf-m/rpm"))
        foldableStackView.addArrangedSubview(trim2)
        foldableStackView.addArrangedSubview(trim3)
        trimReviewedView.setUp(with: "트림 2.2")
        engineImageView.setAsyncImage(url: "https://topcariving.s3.ap-northeast-2.amazonaws.com/power/diesel.jpg")
    }
    private func testBodyType() {
        setProgress(to: .bodyType)
        let trim2 = TrimSelectionContainer()
        trim2.setUp(with: .init(title: "1. 7인승",
                                price: "+0",
                                description: "기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론, 3열 탑승객의 승하차가 편리합니다."))
        let trim3 = TrimSelectionContainer()
        trim3.setUp(with: .init(title: "2. 8인승",
                                price: "+0",
                                description: "기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론, 3열 탑승객의 승하차가 편리합니다."))
        foldableStackView.addArrangedSubview(trim2)
        foldableStackView.addArrangedSubview(trim3)
        trimReviewedView.setUp(with: "7인승")
        engineImageView.setAsyncImage(url: "https://topcariving.s3.ap-northeast-2.amazonaws.com/body_type/seven.jpg")
    }
    private func testDrivingMethod() {
        setProgress(to: .drivingMethod)
        let trim2 = TrimSelectionContainer()
        trim2.setUp(with: .init(title: "1. 2WD", price: "+0", description: "전자식 상시 4륜 구동 시스템 입니다도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다"))
        let trim3 = TrimSelectionContainer()
        trim3.setUp(with: .init(title: "2. 4WD", price: "+0", description: "전자식 상시 4륜 구동 시스템 입니다도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다")
        )
        foldableStackView.addArrangedSubview(trim2)
        foldableStackView.addArrangedSubview(trim3)
        trimReviewedView.setUp(with: "2WD")
        engineImageView.setAsyncImage(url: "https://topcariving.s3.ap-northeast-2.amazonaws.com/driving_method/2wd.jpg")
    }
}
