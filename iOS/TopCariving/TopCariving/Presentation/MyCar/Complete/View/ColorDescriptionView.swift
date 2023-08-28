//
//  ColorDescriptionView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/17.
//

import UIKit

struct ColorDescriptionModel {
    var space: String
    var colorImage: String
    var colorName: String
}

class ColorDescriptionView: UIView {
    // MARK: - UI properties
    private var space: UILabel = {
        let label = UILabel()
        label.text = "외장"
        label.font = .designSystem(.init(name: .medium, size: ._14))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private var spaceColor: UIImageView = {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.layer.cornerRadius = 8
        imageView.layer.borderColor = UIColor.hyundaiActiveBlue.cgColor
        imageView.layer.masksToBounds = true
        imageView.backgroundColor = .black
        return imageView
    }()
    private var colorName: UILabel = {
        let label = UILabel()
        label.text = "문라이트 블루펄"
        label.font = .designSystem(.init(name: .regular, size: ._14))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
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
    init(data: ColorDescriptionModel) {
        super.init(frame: .zero)
        setUI()
        setLayout()
        setData(to: data)
    }
    
    // MARK: - Helpers
    func setUI() {
        [space, spaceColor, colorName].forEach {
            addSubview($0)
        }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            space.topAnchor.constraint(equalTo: topAnchor),
            space.bottomAnchor.constraint(equalTo: bottomAnchor),
            space.leadingAnchor.constraint(equalTo: leadingAnchor),
            space.widthAnchor.constraint(equalToConstant: 25),
            
            spaceColor.leadingAnchor.constraint(equalTo: space.trailingAnchor, constant: 12),
            spaceColor.widthAnchor.constraint(equalToConstant: 16),
            spaceColor.heightAnchor.constraint(equalToConstant: 16),
            spaceColor.centerYAnchor.constraint(equalTo: centerYAnchor),
            
            colorName.topAnchor.constraint(equalTo: topAnchor),
            colorName.bottomAnchor.constraint(equalTo: bottomAnchor),
            colorName.leadingAnchor.constraint(equalTo: spaceColor.trailingAnchor, constant: 8),
            colorName.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    func setSpace(to text: String) {
        space.text = text
    }
    func setColor(to colorImage: String) {
        spaceColor.backgroundColor = .black
        // 이미지 서비스로 변경
    }
    func setColorName(to name: String) {
        colorName.text = name
    }
    func setData(to data: ColorDescriptionModel) {
        setSpace(to: data.space)
        setColor(to: data.colorImage)
        setColorName(to: data.colorName)
    }
}
