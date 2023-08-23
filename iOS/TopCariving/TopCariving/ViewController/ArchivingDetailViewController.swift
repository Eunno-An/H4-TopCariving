//
//  ArchivingDetailViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/22.
//

import UIKit

class ArchivingDetailViewController: BaseArchivingViewController {
    // MARK: - UI properties
    private let detailView = ArchivingDetailView()

    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
        setNavigationBar()
        test()
    }
    
    // MARK: - Helpers
    private func setUI() {
        view.addSubview(detailView)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            detailView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            detailView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            detailView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            detailView.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
    }
    private func setNavigationBar() {
        let appearance = UINavigationBarAppearance()
        appearance.backgroundColor = .hyundaiSand
        
        navigationController?.navigationBar.standardAppearance = appearance
        navigationController?.navigationBar.scrollEdgeAppearance = appearance
        navigationController?.navigationBar.topItem?.backButtonTitle = ""
        navigationController?.navigationBar.tintColor = .hyundaiBlackGray
        let topCarivingImage = UIImage(named: "MyCariving")?.resized(to: 78)
        navigationController?.navigationBar.topItem?.rightBarButtonItem = UIBarButtonItem(image: topCarivingImage,
                                                                                   style: .done,
                                                                                   target: nil,
                                                                                   action: nil)
    }
    private func test() {
        detailView.refresh(by: [
            .init(image: "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/kneewarmer.jpeg",
                  index: 0,
                  optionName: "적외선 무릎 워머",
                  detailOptions: [],
                  tags: ["어린이", "편리해요", "팬이에요"]),
            .init(image: "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/kneewarmer.jpeg",
                  index: 1,
                  optionName: "적외선 무릎 워머",
                  detailOptions: [],
                  tags: ["어린이", "편리해요", "팬이에요"]),
            .init(image: "https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/kneewarmer.jpeg",
                  index: 2,
                  optionName: "적외선 무릎 워머",
                  detailOptions: ["전방 충돌방지 보조", "내비게이션 기반 스마트 크루즈 컨트롤", "고속도로 주행보조 2"],
                  tags: ["차량을 깨끗하게 유지할 수 있어요:빗자루:"])
            
        ],
                           review: "승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다. 다른 주차 관련 옵션도 괜찮아요.",
                           summaryModel: testSummaryModel()
        )
    }
    private func testSummaryModel() -> MyCarivingCarSummaryModel {
        .init(featureSummary: testFeatureSummaryModel(),
                            carImage: "https://topcariving.s3.ap-northeast-2.amazonaws.com/360/abyss/image_001.png",
                            points: [.init(number: 1, isBookmarked: false)],
                            positions: [(0.46, 0.34)])
    }
    private func testFeatureSummaryModel() -> FeatureSummaryModel {
        .init(trim: "팰리세이드 Le Blanc",
              option: "디젤 2.2 / 4WD / 7인승",
              price: "47,340,000 원",
              colors: [("", "듀얼 듀얼 패키지"), ("", "퀄팅 퀄팅 퀄팅")])
    }
}
