//
//  ChangeOptionButton.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/17.
//

import UIKit

class ChangeOptionButtonView: UIView {
    // MARK: - UI properties
    private let image: UIImageView = {
        let image = UIImageView(image: UIImage(named: "edit"))
        image.translatesAutoresizingMaskIntoConstraints = false
        return image
    }()
    private let label: UILabel = {
        let label = UILabel()
        label.text = "변경"
        label.font = .designSystem(.init(name: .regular, size: ._10))
        label.textColor = .hyundaiGold
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
    
    // MARK: - Helpers
    func setUI() {
        backgroundColor = .hyundaiSand
        layer.cornerRadius = 11
        [label, image].forEach {
            addSubview($0)
        }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            image.centerYAnchor.constraint(equalTo: centerYAnchor),
            image.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 5),
            image.heightAnchor.constraint(equalToConstant: 12),
            image.widthAnchor.constraint(equalToConstant: 12),
            
            label.topAnchor.constraint(equalTo: topAnchor),
            label.bottomAnchor.constraint(equalTo: bottomAnchor),
            label.leadingAnchor.constraint(equalTo: image.trailingAnchor),
            label.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
}
