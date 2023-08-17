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
        [label, image].forEach {
            addSubview($0)
        }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            image.topAnchor.constraint(equalTo: topAnchor),
            image.bottomAnchor.constraint(equalTo: bottomAnchor),
            image.leadingAnchor.constraint(equalTo: leadingAnchor),
            image.trailingAnchor.constraint(equalTo: label.leadingAnchor),
            
            label.topAnchor.constraint(equalTo: topAnchor),
            label.bottomAnchor.constraint(equalTo: bottomAnchor),
            label.leadingAnchor.constraint(equalTo: image.trailingAnchor),
            label.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
}
