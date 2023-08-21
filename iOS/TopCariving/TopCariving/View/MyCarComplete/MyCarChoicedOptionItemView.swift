//
//  MyCarChoicedOptionItemView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/16.
//

import UIKit

struct MyCarChoicedOptionItemModel {
    var imageName: String
    var optionName: String
    var optionPrice: String
    var optionDetail: String
}

class MyCarChoicedOptionItemView: UIView {
    // MARK: - UI properties
    private var imageView: UIImageView = {
        let imageView = UIImageView(image: UIImage(named: "ChoicedOptionItem"))
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    private var optionName: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "컴포트 ||"
        label.font = .designSystem(.init(name: .medium, size: ._16))
        return label
    }()
    private var optionSeparator: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.layer.borderWidth = 1
        label.layer.borderColor = UIColor.hyundaiMediumGray.cgColor
        return label
    }()
    private var optionPrice: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "1,090,000원"
        label.font = .designSystem(.init(name: .medium, size: ._16))
        return label
    }()
    private var editButton: UIButton = {
        let button = UIButton()
        let buttonView = ChangeOptionButtonView()
        buttonView.setButtonImage(to: UIImage(named: "edit_gray") ?? UIImage())
        buttonView.setButtonLabelColor(to: .lightGray)
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
    private var optionDetail: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.numberOfLines = 0
        label.lineBreakMode = .byWordWrapping
        label.font = .designSystem(.init(name: .regular, size: ._12))
        label.text = "후석 승객 알림 / 메탈 리어범퍼스텝 / 메탈 도어스커프 / 3열 파워폴딩시트 / 3열 열선시트 / 헤드업 디스틀레이"
        return label
    }()
    
    // MARK: - Properties
    private var intrinsicSize: CGSize = CGSize(width: CGRect.screenBounds.width - 16 * 2, height: 108)
    
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
    init(data: MyCarChoicedOptionItemModel) {
        super.init(frame: .zero)
        setUI()
        setLayout()
        setData(to: data)
    }
    override var intrinsicContentSize: CGSize {
        intrinsicSize
    }
    
    // MARK: - Helpers
    private func setUI() {
        [imageView, optionName, optionSeparator, optionPrice, editButton, optionDetail].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            imageView.heightAnchor.constraint(equalToConstant: 82),
            imageView.widthAnchor.constraint(equalToConstant: 67.74),
            imageView.topAnchor.constraint(equalTo: topAnchor, constant: 10),
            imageView.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            optionName.heightAnchor.constraint(equalToConstant: 28),
            optionName.widthAnchor.constraint(equalToConstant: 65),
            optionName.topAnchor.constraint(equalTo: topAnchor, constant: 13),
            optionName.leadingAnchor.constraint(equalTo: imageView.trailingAnchor, constant: 11.26),
            
            optionSeparator.heightAnchor.constraint(equalToConstant: 13),
            optionSeparator.widthAnchor.constraint(equalToConstant: 1),
            optionSeparator.topAnchor.constraint(equalTo: topAnchor, constant: 19),
            optionSeparator.leadingAnchor.constraint(equalTo: optionName.leadingAnchor, constant: 62),
            
            optionPrice.heightAnchor.constraint(equalToConstant: 28),
            optionPrice.widthAnchor.constraint(equalToConstant: 100),
            optionPrice.topAnchor.constraint(equalTo: topAnchor, constant: 13),
            optionPrice.leadingAnchor.constraint(equalTo: optionSeparator.trailingAnchor, constant: 5),
            
            editButton.heightAnchor.constraint(equalToConstant: 19),
            editButton.widthAnchor.constraint(equalToConstant: 46),
            editButton.topAnchor.constraint(equalTo: optionPrice.topAnchor),
            editButton.trailingAnchor.constraint(equalTo: trailingAnchor),
            
            optionDetail.heightAnchor.constraint(equalToConstant: 40),
            optionDetail.widthAnchor.constraint(equalToConstant: 260),
            optionDetail.topAnchor.constraint(equalTo: optionPrice.bottomAnchor, constant: 10),
            optionDetail.leadingAnchor.constraint(equalTo: imageView.trailingAnchor, constant: 12.26)
        ])
    }
    func setImage(to name: String) {
        imageView.image = UIImage(named: name)
    }
    func setOptionName(to name: String) {
        optionName.text = name
    }
    func setOptionPrice(to price: String) {
        optionPrice.text = price
    }
    func setOptionDetail(to detail: String) {
        optionDetail.text = detail
    }
    func setData(to data: MyCarChoicedOptionItemModel) {
        setImage(to: data.imageName)
        setOptionName(to: data.optionName)
        setOptionPrice(to: data.optionPrice)
        setOptionDetail(to: data.optionDetail)
    }
}
