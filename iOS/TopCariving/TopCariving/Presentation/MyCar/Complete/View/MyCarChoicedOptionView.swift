//
//  MyCarChoicedOptionView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/19.
//

import UIKit

struct MyCarChoicedOptionModel {
    var myCarChoicedOptionItems: [MyCarChoicedOptionItemModel]
}

class MyCarChoicedOptionView: UIView {
    // MARK: - UI properties
    private var title: UILabel = {
        let label = UILabel()
        label.text = "선택옵션 0개"
        label.font = .designSystem(.init(name: .medium, size: ._18))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private let separator: UILabel = {
        let label = UILabel()
        label.layer.borderColor = UIColor.lightGray.cgColor
        label.layer.borderWidth = 1
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    var stackView: UIStackView = {
        let stackView = UIStackView()
        stackView.axis = .vertical
        stackView.translatesAutoresizingMaskIntoConstraints = false
        return stackView
    }()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setTitle()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setTitle()
    }
    init(myCarChoicedOptionModel: MyCarChoicedOptionModel) {
        super.init(frame: .zero)
        setStackView(to: myCarChoicedOptionModel.myCarChoicedOptionItems)
        setTitle()
        setUI()
        setLayout()
    }
    
    // MARK: - Helpers
    private func setUI() {
        addSubview(title)
        addSubview(separator)
        addSubview(stackView)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            title.heightAnchor.constraint(equalToConstant: 30),
            title.widthAnchor.constraint(equalToConstant: 100),
            title.topAnchor.constraint(equalTo: topAnchor),
            title.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            separator.heightAnchor.constraint(equalToConstant: 1),
            separator.widthAnchor.constraint(equalToConstant: CGRect.screenBounds.width - 16*2),
            separator.topAnchor.constraint(equalTo: title.bottomAnchor, constant: 16),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            stackView.topAnchor.constraint(equalTo: separator.bottomAnchor),
            stackView.leadingAnchor.constraint(equalTo: leadingAnchor)
        ])
    }
    func setStackView(to data: [MyCarChoicedOptionItemModel]) {
        let testData = testMakeMyCarChoicedoptionItemModel()
        for idx in 0..<testData.count {
            stackView.addArrangedSubview(MyCarChoicedOptionItemView(data: testData[idx]))
        }
        setTitle()
    }
    private func setTitle() {
        title.text = "선택옵션 " + String(stackView.arrangedSubviews.count) + "개"
    }
    private func testMakeMyCarChoicedoptionItemModel() -> [MyCarChoicedOptionItemModel] {
        var ret: [MyCarChoicedOptionItemModel] = []
        
        ret.append(.init(imageName: "comport2", optionName: "컴포트II", optionPrice: "1,090,000", optionDetail: "주요 주행 정보를 전면 윈드실드에 표시합니다."))
        ret.append(.init(
            imageName: "dualmufflerpackage",
                         optionName: "듀얼 머플러 패키지",
            optionPrice: "840,000",
            optionDetail: "[내비게이션 기반 스마트 크루즈 컨트롤(진출입로)]차량 후면부에 스포티함을 한층 강화시켜주는 듀얼 머플러와 디자인 리어 스키드 상품입니다."))
        ret.append(.init(
            imageName: "20_darkwheel",
                         optionName: "20인치 다크 스퍼터링 힐", optionPrice: "840,000", optionDetail: "스포티한 디자인 감성을 원하는 고객을 위한 N 퍼포먼스 파츠 휠입니다."))
        return ret
    }
}
