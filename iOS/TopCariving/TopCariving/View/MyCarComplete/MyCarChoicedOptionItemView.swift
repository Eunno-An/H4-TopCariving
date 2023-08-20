//
//  MyCarChoicedOptionItemView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/16.
//

import UIKit

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
    private var button: UIButton = {
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
    private var intrinsicSize: CGSize = .zero
    
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
    init(intrinsicSize: CGSize) {
        self.intrinsicSize = intrinsicSize
        super.init(frame: .zero)
        setUI()
        setLayout()
    }
    override var intrinsicContentSize: CGSize {
        intrinsicSize
    }
    
    // MARK: - Helpers
    private func setUI() {
        [imageView, optionName, optionSeparator, optionPrice, button, optionDetail].forEach {
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
            
            button.heightAnchor.constraint(equalToConstant: 19),
            button.widthAnchor.constraint(equalToConstant: 46),
            button.topAnchor.constraint(equalTo: optionPrice.topAnchor),
            button.trailingAnchor.constraint(equalTo: trailingAnchor),
            
            optionDetail.heightAnchor.constraint(equalToConstant: 40),
            optionDetail.widthAnchor.constraint(equalToConstant: 260),
            optionDetail.topAnchor.constraint(equalTo: optionPrice.bottomAnchor, constant: 10),
            optionDetail.leadingAnchor.constraint(equalTo: imageView.trailingAnchor, constant: 12.26)
        ])
    }
}
