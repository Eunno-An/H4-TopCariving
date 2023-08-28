//
//  ChangeOptionButton.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/17.
//

import UIKit

class ChangeOptionButtonView: UIView {
    // MARK: - UI properties
    private var imageView: UIImageView = {
        let image = UIImageView(image: UIImage(named: "edit_gray"))
        image.translatesAutoresizingMaskIntoConstraints = false
        return image
    }()
    private var label: UILabel = {
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
        [label, imageView].forEach {
            addSubview($0)
        }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            imageView.centerYAnchor.constraint(equalTo: centerYAnchor),
            imageView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 5),
            imageView.heightAnchor.constraint(equalToConstant: 12),
            imageView.widthAnchor.constraint(equalToConstant: 12),
            
            label.topAnchor.constraint(equalTo: topAnchor),
            label.bottomAnchor.constraint(equalTo: bottomAnchor),
            label.leadingAnchor.constraint(equalTo: imageView.trailingAnchor),
            label.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    func setButtonImage(to image: UIImage) {
        imageView.image = image
    }
    func setButtonLabelColor(to color: UIColor) {
        self.label.textColor = color
    }
}
