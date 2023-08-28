//
//  SelectGenuineOptionViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/28.
//

import Combine
import UIKit

class SelectGenuineOptionViewController: BaseMyCarViewController {
    
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
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/dualmufflerpackage.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/builtinaircleaner.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/sidestep.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/kneewarmer.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/carprotector.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/luggageprotectmat.jpeg"
    ]
    let names = [
        "듀얼 머플러 패키지",
        "빌트인 공기청정기",
        "사이드스텝",
        "적외선 무릎워머",
        "차량 보호 필름",
        "러기지 프로텍션 매트",
    ]
    let prices = [
        840000,
        400000,
        350000,
        300000,
        490000,
        250000

    ]
    let detailImages = [
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/dualmufflerpackage.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/builtinaircleaner.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/sidestep.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/kneewarmer.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/carprotector.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/luggageprotectmat.jpeg"
    ]
    let headers = [
        "듀얼 머플러 패키지",
        "빌트인 공기청정기",
        "사이드스텝",
        "적외선 무릎워머",
        "차량 보호 필름",
        "러기지 프로텍션 매트",
    ]
    let details = [
        "[내비게이션 기반 스마트 크루즈 컨트롤(진출입로)]차량 후면부에 스포티함을 한층 강화시켜주는 듀얼 머플러와 디자인 리어 스키드 상품입니다.",
        "H13 등급 및 활성탄+제올라이트 복합소재 필터를 적용하여 초미세먼지뿐 아니라 실내 유해가스(VOCs) 정화 기능이 우수한 상품입니다.",
        "뛰어난 일체감을 통해 사이드 디자인을 개선시켜 주며, 미끄럼 방지 패턴이 적용되어 어린이 및 노약자의 승하차 시 안정성을 높여주는 상품입니다.",
        "스티어링 컬럼 하단에 설치된 복사 열선이 운전자의 허벅지와 무릎에 복사열을 전달하여 저온에서 히터 바람 없이도 냉감을 줄여 보다 쾌적한 환경을 제공해주는 상품입니다.",
        "카 라이프중 손상되기 쉬운 프론트/리어 범프등 7부위에 우레탄 계열의 투명한 보호 필름을 부착하여 스톤칩으로 인한 페인트 손상 및 생활 스크래치를 예방하여 깨끗한 바디를 유지하는데, 도움을 줍니다.",
        "차량 설계 데이터를 반영한 3D 형상 디자인으로 차량에 딱 맞는 완벽한 일체감과 실내 고급감을 제공합니다.",
    ]
    lazy var data = (0..<6).map { OptionCardModel.init(image: self.images[$0], name: self.names[$0], price: self.prices[$0], isAdded: false) }
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
            self.navigationController?.pushViewController(SelectNPerformanceViewController(), animated: true)
        })
        .store(in: &bag)
    }
}

extension SelectGenuineOptionViewController: OptionSelectViewDataSource {
    func numberOfOption(_ optionSelectView: OptionSelectView) -> Int {
        5
    }
    func optionSelectModel(_ optionSelectView: OptionSelectView, at indexPath: IndexPath) -> OptionCardModel {
        data[indexPath.row]
    }
}
