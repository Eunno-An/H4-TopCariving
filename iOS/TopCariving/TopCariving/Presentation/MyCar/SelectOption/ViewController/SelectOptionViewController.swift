//
//  SelectOptionViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/18.
//

import Combine
import UIKit

class SelectOptionViewController: BaseMyCarViewController {
    
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
    private let optionDescriptionCollection = OptionSnapCarouselView()
    
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
            
            carImageView.topAnchor.constraint(equalTo: reviewView.bottomAnchor, constant: 10),
            carImageView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            carImageView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            carImageView.heightAnchor.constraint(equalToConstant: 180 * CGRect.screenBounds.width / 352),
            
            optionDescriptionCollection.topAnchor.constraint(equalTo: carImageView.bottomAnchor, constant: 10),
            optionDescriptionCollection.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 0),
            optionDescriptionCollection.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: 0),
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
        reviewView.refresh(by: tags[0], with: "컴포트 II")
    }
    private func testOptionDescriptionCollection() {
        carImageView.setAsyncImage(url: detailImages[0])
        optionDescriptionCollection.refresh(by: (0..<6).map {
            OptionDescriptionModel(index: $0, maxIndex: 6,
                                       title: "헤드업 디스플레이",
                                       optionDescription: "동승석의 시트 포지션을 조정하여 동승자의 체형에 맞는 편안한 자세를 유지할 수 있도록 돕는 기능입니다. 8방향(시트백 기울기, 시트 앞/뒤 이동,  앞/뒤 높이 조절)으로 조절이 기능하고 운전자 및 뒷좌석(2열) 승객이 동승석 시트 위치를 조절할 수 있는 워크인 디바이스를 적용하여 실내 공간 활용 편의성을 높였습니다.")
        })
    }
    
    private func testOptionSelectView() {
        optionSelectView.tapAddButtonSubject.sink(receiveValue: { [weak self] index in
            guard let self else { return }
            self.reviewView.refresh(by: tags[index.row], with: self.names[index.row])
        }).store(in: &bag)
    }
    
    // data는 OptionSelectViewDataSource를 위한 데이터 소스 입니다.
    let tags = [
        [ "어린이👶", "가족들도 좋은 옵션👨‍👩‍👧‍👦", "가격이 합리적이에요👍", "깨끗하게 유지할 수 있어요🧹", "대형견도 문제 없어요🐶"],
        ["편리해요☺️", "이것만 있으면 나도 주차고수🚘", "가격이 합리적이에요👍", "안전사고 예방🚨", "처음보는 옵션😲"],
        ["가격이 합리적이에요👍", "가족들을 위한 옵션👨‍👩‍👧‍👦", "여름에 쓰기 좋아요☀️", "편리해요☺️"],
        ["트렌디한 디자인😎", "가격이 합리적이에요?", "가족들도 좋은 옵션👨‍👩‍👧‍👦", "시원한 공기🪟", "여름에 쓰기 좋아요☀️"],
        ["고화질👀", "빠른영상공유📷", "합리적이에요👍", "방전걱정없어요🔋"],
        ["주행이 편안해요🚙", "가격이 합리적이에요👍", "편리해요☺️", "안전사고 예방🚨", "믿을 수 있는 성능🤝"]
    ]
    let images = [
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/pca.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/2_cooling.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/sun_roof.jpg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/cam.jpg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/smart_front_collision.jpeg"
    ]
    let names = [
        "컴포트 II",
        "주차보조 시스템 II",
        "2열 통풍 시트",
        "듀얼 와이드 선루프",
        "빌트인 캠(보조배터리 포함)",
        "현대스마트센스 I"
    ]
    let prices = [
        1090000,
        690000,
        400000,
        890000,
        690000,
        790000
    ]
    let detailImages = [
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/metalrearbumper.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/metaldoorscuff.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/powerfolding.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/3_heated.jpeg",
        "https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/headupdisplay.jpeg"
    ]
    let headers = [
        "[후석 승객 알림]",
        "[메탈 리어범퍼스텝]",
        "[메탈 도어스커프]",
        "[3열 파워폴딩시트]",
        "[3열 열선시트]",
        "[헤드업 디스플레이]"
    ]
    let details = [
        "초음파 센서를 통해 뒷좌석에 남아있는 승객의 움직임을 감지하여 운전자에게 경고함으로써 부주의에 의한 유아 또는 반려 동물 등의 방치 사고를 예방하는 신기술입니다.",
        "러기지 룸 앞쪽 하단부를 메탈로 만들어 물건을 싣고 내릴 때나 사람이 올라갈 때 차체를 보호해줍니다.",
        "자동차를 타고 내리는 도어의 문틈 하부를 보호하는 도어스커프 부분을 메탈로 만들어 차체를 보호하고 메탈 디자인으로 고급스러운 감성을 전달합니다.",
        "러기지 사이드에 있는 버튼으로 3열 시트를 접었다 펼 수 있으며, 2열 시트도 조작할 수 있어 화물 적재시나 3열 이용시 사용자의 편의성을 높였습니다.",
        "시동이 걸린 상태에서 해당 좌석 히터 스위치를 누르면 강약조절 표시등이 켜져 사용 중임을 나타내고 해당 좌석이 따뜻해집니다.",
        " 주요 주행 정보를 전면 윈드실드에 표시하며, 밝기가 최적화되어 주간에도 시인성이 뛰어납니다."
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
                let sequence = (0..<6).shuffled()
                let headers: [String] = sequence.map { self.headers[$0] }
                let dess: [String] = sequence.map { self.details[$0] }
                
                optionDescriptionCollection.refresh(by: (0..<6).map {
                    OptionDescriptionModel(index: $0, maxIndex: 6,
                                               title: headers[$0],
                                               optionDescription: dess[$0])
                })
                self.carImageView.setAsyncImage(url: detailImages[indexPath.row],
                                                size: carImageView.bounds.size)
                reviewView.refresh(by: tags[indexPath.row], with: names[indexPath.row])
            })
            .store(in: &bag)
        optionDescriptionCollection.didDisplayCellIndexPathSubject
            .sink(receiveValue: { [weak self] indexPath in
                guard let self else { return }
                self.carImageView.setAsyncImage(url: detailImages[indexPath.row], size: carImageView.bounds.size)
            })
            .store(in: &bag)
    }
    private func testPush() {
        footerView.tapNextButton.sink(receiveValue: {[weak self] _ in
            guard let self else { return }
            self.navigationController?.pushViewController(SelectGenuineOptionViewController(), animated: true)
        })
        .store(in: &bag)
    }
}

extension SelectOptionViewController: OptionSelectViewDataSource {
    func numberOfOption(_ optionSelectView: OptionSelectView) -> Int {
        5
    }
    func optionSelectModel(_ optionSelectView: OptionSelectView, at indexPath: IndexPath) -> OptionCardModel {
        data[indexPath.row]
    }
}
