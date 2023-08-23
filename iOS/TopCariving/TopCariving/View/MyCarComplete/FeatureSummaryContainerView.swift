//
//  FeatureSummaryContainerView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/17.
//

import UIKit

struct FeatureSummaryModel {
    var trim: String
    var option: String
    var price: String
    var colors: [(String, String)]
}

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
    private var editButton: UIButton = {
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
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
    }
    init(data: FeatureSummaryModel) {
        super.init(frame: .zero)
        setUI()
        setLayout()
        setFeatureSummaryView(to: data)
    }
    
    // MARK: - Helpers
    private func setUI() {
        outColorDescriptionView.translatesAutoresizingMaskIntoConstraints = false
        inColorDescriptionView.translatesAutoresizingMaskIntoConstraints = false
        layer.cornerRadius = 8
        [trim, option, editButton, price, separator, outColorDescriptionView, inColorDescriptionView].forEach {
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
            option.trailingAnchor.constraint(equalTo: editButton.leadingAnchor, constant: -7),
            
            editButton.bottomAnchor.constraint(equalTo: option.bottomAnchor),
            editButton.leadingAnchor.constraint(equalTo: option.trailingAnchor, constant: 7),
            editButton.heightAnchor.constraint(equalToConstant: 15),
            editButton.widthAnchor.constraint(equalToConstant: 42),
            
            price.topAnchor.constraint(equalTo: editButton.bottomAnchor, constant: 1),
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
    func setBackgroundColor(to color: UIColor) {
        backgroundColor = color
    }
    func setTrim(to trim: String) {
        self.trim.text = trim
    }
    func setOption(to option: String) {
        self.option.text = option
    }
    func setPrice(to price: String) {
        self.price.text = price
    }
    func setFeatureSummaryView(to data: FeatureSummaryModel) {
        setTrim(to: data.trim)
        setOption(to: data.option)
        setPrice(to: data.price)
        guard data.colors.count == 2 else {
            return
        }
        inColorDescriptionView.setData(to: .init(space: "외장",
                                                 colorImage: data.colors[0].0,
                                                 colorName: data.colors[0].1))
        outColorDescriptionView.setData(to: .init(space: "내장",
                                                  colorImage: data.colors[1].0,
                                                  colorName: data.colors[1].1))
    }
    func hideEditButton() {
        editButton.isHidden = true
    }
}
