//
//  SelectNPerformanceViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/28.
//

import Combine
import UIKit

class SelectNPerformanceViewController: BaseMyCarViewController {
    
    // MARK: - UI properties
    private let scrollView: UIScrollView = UIScrollView()
    private let optionSelectView = OptionSelectView()
    private let separatorView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .hyundaiLightSand
        return view
    }()
    private let reviewView = TagReviewView()
    private let carImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.layer.cornerRadius = 8
        imageView.clipsToBounds = true
        return imageView
    }()
    private let optionDescriptionCollection = OptionDescriptionView()
    
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        testInject()
        optionSelectView.refresh()
        testSetEvent()
        testPush()
    }
    
    // MARK: - Helpers
    override func setUI() {
        super.setUI()
        setProgress(to: .selectOptions)
        optionSelectView.datasource = self
        view.addSubview(scrollView)
        [optionSelectView, separatorView, reviewView, optionDescriptionCollection, carImageView].forEach {
            scrollView.addSubview($0)
        }
    }
    override func setLayout() {
        super.setLayout()
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: progressView.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: footerView.topAnchor),
            
            optionSelectView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            optionSelectView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            optionSelectView.topAnchor.constraint(equalTo: scrollView.contentLayoutGuide.topAnchor),
            optionSelectView.heightAnchor.constraint(equalTo: optionSelectView.widthAnchor, multiplier: 0.65),
            
            separatorView.topAnchor.constraint(equalTo: optionSelectView.bottomAnchor, constant: 0),
            separatorView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            separatorView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            separatorView.heightAnchor.constraint(equalToConstant: 6),
            
            reviewView.topAnchor.constraint(equalTo: separatorView.bottomAnchor, constant: 20),
            reviewView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            reviewView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            
            carImageView.topAnchor.constraint(equalTo: reviewView.bottomAnchor, constant: -30),
            carImageView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            carImageView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            carImageView.heightAnchor.constraint(equalToConstant: 180 * CGRect.screenBounds.width / 352),
            
            optionDescriptionCollection.topAnchor.constraint(equalTo: carImageView.bottomAnchor, constant: 10),
            optionDescriptionCollection.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            optionDescriptionCollection.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            optionDescriptionCollection.heightAnchor.constraint(equalToConstant: 131),
            optionDescriptionCollection.bottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor,
                                                               constant: -8)
        ])
    }

    private func testInject() {
        testReviewView()
        testOptionDescriptionCollection()
        testOptionSelectView()
    }
    // swiftlint: disable line_length
    private func testReviewView() {
        reviewView.refresh(by: [], with: "듀얼 머플러 패키지")
    }
    private func testOptionDescriptionCollection() {
        carImageView.setAsyncImage(url: detailImages[0])
        optionDescriptionCollection.setUp(with: .init(index: 0, maxIndex: 6, title: names[0], optionDescription: details[0]))
    }
    
    private func testOptionSelectView() {
        optionSelectView.tapAddButtonSubject.sink(receiveValue: { [weak self] index in
            guard let self else { return }
            
            self.reviewView.refresh(by: [].shuffled(), with: self.names[index.row])
        }).store(in: &bag)
    }
    
    // data는 OptionSelectViewDataSource를 위한 데이터 소스 입니다.
    let images = [
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/20_darkwheel.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/20inchwheel.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/alconbreak.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/20inchwheel.jpeg"
    ]
    let names = [
        "20인치 다크 스퍼터링 휠",
        "20인치 블랙톤 전면 가공 휠",
        "알콘(alcon) 단조 브레이크 & 20인치 휠 패키지",
        "20인치 블랙톤 전면 가공 휠"
    ]
    let prices = [
        840000,
        840000,
        3660000,
        840000

    ]
    let detailImages = [
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/20_darkwheel.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/20inchwheel.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/alconbreak.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/20inchwheel.jpeg"
    ]
    let headers = [
        "20인치 다크 스퍼터링 휠",
        "20인치 블랙톤 전면 가공 휠",
        "알콘(alcon) 단조 브레이크 & 20인치 휠 패키지",
        "20인치 블랙톤 전면 가공 휠"
    ]
    let details = [
        "스포티한 디자인 감성을 원하는 고객을 위한 N 퍼포먼스 파츠 휠입니다.",
        "알콘 브레이크 시스템 장착을 위한 전용휠이며 다이아몬트 커팅 피니쉬로 플래그십 SUV에 어울리는 고급스러운 디자인 감성을 제공합니다.",
        "6피스톤 단조 캘리퍼 : 단조 소재를 적용하여 강성을 증대하였으며(기존: 주조) 피스톤 직경 증대로 제동 응답성이 향상되었습니다.",
        "알콘 브레이크 시스템 장착을 위한 전용휠이며 다이아몬트 커팅 피니쉬로 플래그십 SUV에 어울리는 고급스러운 디자인 감성을 제공합니다."
    ]
    lazy var data = (0..<4).map { OptionCardModel.init(image: self.images[$0], name: self.names[$0], price: self.prices[$0], isAdded: false) }
    private func testSetEvent() {
        optionSelectView.tapIncludedSubject.sink(receiveValue: { [weak self] in
            guard let self else { return }
            let modalVC = IncludedBaseItemModalViewController()
            modalVC.modalPresentationStyle = .automatic
            self.present(modalVC, animated: true)
        })
        .store(in: &bag)
        Publishers.Merge(optionSelectView.tapAddButtonSubject, optionSelectView.tapCellSubject)
            .sink(receiveValue: { [weak self] indexPath in
                guard let self else { return }
                
                optionDescriptionCollection.setUp(with: .init(index: indexPath.row,
                                                              maxIndex: 6,
                                                              title: names[indexPath.row],
                                                              optionDescription: details[indexPath.row]))
                reviewView.refresh(by: [], with: names[indexPath.row])
                self.carImageView.setAsyncImage(url: detailImages[indexPath.row],
                                                size: carImageView.bounds.size)
                
            })
            .store(in: &bag)
    }
    private func testPush() {
        footerView.tapNextButton.sink(receiveValue: {[weak self] _ in
            guard let self else { return }
            self.navigationController?.pushViewController(MyCarCompletionViewController(), animated: true)
        })
        .store(in: &bag)
    }
}

extension SelectNPerformanceViewController: OptionSelectViewDataSource {
    func numberOfOption(_ optionSelectView: OptionSelectView) -> Int {
        4
    }
    func optionSelectModel(_ optionSelectView: OptionSelectView, at indexPath: IndexPath) -> OptionCardModel {
        data[indexPath.row]
    }
}
