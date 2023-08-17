//
//  ColorDescriptionView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/17.
//

import UIKit

struct ColorDescriptionModel {
    var space: String
    var color: UIColor
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
    private var spaceColor: UILabel = {
        let label = UILabel()
        label.backgroundColor = .hyundaiBlackGray
        label.layer.cornerRadius = label.frame.width / 2
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
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
        setSpace(to: data.space)
        setColor(to: data.color)
        setColorName(to: data.colorName)
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
            space.trailingAnchor.constraint(equalTo: spaceColor.leadingAnchor, constant: 12),
            
            spaceColor.topAnchor.constraint(equalTo: topAnchor),
            spaceColor.bottomAnchor.constraint(equalTo: bottomAnchor),
            spaceColor.leadingAnchor.constraint(equalTo: space.trailingAnchor, constant: 12),
            spaceColor.trailingAnchor.constraint(equalTo: colorName.leadingAnchor, constant: 8),
            
            colorName.topAnchor.constraint(equalTo: topAnchor),
            colorName.bottomAnchor.constraint(equalTo: bottomAnchor),
            colorName.leadingAnchor.constraint(equalTo: spaceColor.trailingAnchor, constant: 8),
            colorName.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    func setSpace(to text: String) {
        space.text = text
    }
    func setColor(to color: UIColor) {
        spaceColor.backgroundColor = color
    }
    func setColorName(to name: String) {
        colorName.text = name
    }
}
