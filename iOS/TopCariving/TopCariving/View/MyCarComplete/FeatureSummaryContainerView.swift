//
//  FeatureSummaryContainerView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/17.
//

import UIKit

class FeatureSummaryContainerView: UIView {
    // MARK: - UI properties
    private var trim: UILabel = {
        let label = UILabel()
        label.text = "펠리세이드 Le Blanc(르블랑)"
        label.setFont(to: .init(name: .medium, size: ._18))
        label.textAlignment = .left
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private var option: UILabel = {
        let label = UILabel()
        label.text = "디젤 2.2 / 4WD / 7인승"
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textAlignment = .left
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private var button: UIButton = {
        let button = UIButton()
        let buttonView = ChangeOptionButtonView()
        buttonView.setButtonImage(to: UIImage(named: "edit_brown") ?? UIImage())
        buttonView.setButtonLabelColor(to: .hyundaiGold)
        buttonView.translatesAutoresizingMaskIntoConstraints = false
        button.addSubview(buttonView)
        NSLayoutConstraint.activate([
            buttonView.topAnchor.constraint(equalTo: button.topAnchor),
            buttonView.bottomAnchor.constraint(equalTo: button.bottomAnchor),
            buttonView.leadingAnchor.constraint(equalTo: button.leadingAnchor),
            buttonView.widthAnchor.constraint(equalToConstant: 42)
        ])
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    private var price: UILabel = {
        let label = UILabel()
        label.text = "47,340,000원"
        label.setFont(to: .init(name: .regular, size: ._14))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private var separator: UILabel = {
        let label = UILabel()
        label.layer.borderWidth = 1
        label.layer.borderColor = UIColor.hyundaiSand.cgColor
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private var outColorDescriptionView: ColorDescriptionView = ColorDescriptionView()
    private var inColorDescriptionView: ColorDescriptionView = ColorDescriptionView()
    
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        test_data()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        test_data()
    }
    init() {
        super.init(frame: .zero)
        setUI()
        setLayout()
        test_data()
    }
    
    // MARK: - Helpers
    private func setUI() {
        outColorDescriptionView.translatesAutoresizingMaskIntoConstraints = false
        inColorDescriptionView.translatesAutoresizingMaskIntoConstraints = false
        backgroundColor = .hyundaiLightSand
        layer.cornerRadius = 8
        [trim, option, button, price, separator, outColorDescriptionView, inColorDescriptionView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            trim.topAnchor.constraint(equalTo: topAnchor, constant: 28),    
            trim.heightAnchor.constraint(equalToConstant: 24),
            trim.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 33),
            trim.trailingAnchor.constraint(equalTo: trailingAnchor),
            
            option.topAnchor.constraint(equalTo: trim.bottomAnchor),
            option.heightAnchor.constraint(equalToConstant: 22),
            option.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 33),
            option.trailingAnchor.constraint(equalTo: button.leadingAnchor, constant: -7),
            
            button.bottomAnchor.constraint(equalTo: option.bottomAnchor),
            button.leadingAnchor.constraint(equalTo: option.trailingAnchor, constant: 7),
            button.heightAnchor.constraint(equalToConstant: 15),
            button.widthAnchor.constraint(equalToConstant: 42),
            
            price.topAnchor.constraint(equalTo: button.bottomAnchor, constant: 1),
            price.heightAnchor.constraint(equalToConstant: 22),
            price.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 219),
            price.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -33),
            
            separator.topAnchor.constraint(equalTo: price.bottomAnchor, constant: 10),
            separator.heightAnchor.constraint(equalToConstant: 1),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 33),
            separator.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -33),
            
            outColorDescriptionView.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 17),
            outColorDescriptionView.heightAnchor.constraint(equalToConstant: 23),
            outColorDescriptionView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 33),
            outColorDescriptionView.trailingAnchor.constraint(equalTo: trailingAnchor),
            
            inColorDescriptionView.topAnchor.constraint(equalTo: outColorDescriptionView.bottomAnchor, constant: 7),
            inColorDescriptionView.heightAnchor.constraint(equalToConstant: 23),
            inColorDescriptionView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 33),
            inColorDescriptionView.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    private func test_data() {
        outColorDescriptionView = ColorDescriptionView(
            data: ColorDescriptionModel(space: "외장", color: .hyundaiBlackGray, colorName: "문라이트 블루펄")
        )
        inColorDescriptionView = ColorDescriptionView(
            data: ColorDescriptionModel(space: "내장", color: .hyundaiBlackGray, colorName: "퀄팅 천연(블랙)")
        )
    }
}
