//
//  OptionTagCell.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import UIKit

class OptionTagCell: UICollectionViewCell {
    // MARK: - UI properties
    private let nameLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textColor = .hyundaiLightSand
        return label
    }()
    private let xmarkImageView: UIImageView = {
        let image = UIImage(systemName: "xmark")?.withTintColor(.hyundaiLightSand.withAlphaComponent(0.6))
        let imageView = UIImageView(image: image?.resized(to: 12))
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.contentMode = .center
        imageView.isUserInteractionEnabled = true
        return imageView
    }()
    // MARK: - Properties
    static let identifier = "OptionTagCell"
    lazy var tapXmarkImageViewPublisher = xmarkImageView.tabPublisher
    
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
    private func setUI() {
        backgroundColor = .hyundaiNavy
        layer.cornerRadius = 4
        [nameLabel, xmarkImageView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            nameLabel.topAnchor.constraint(equalTo: topAnchor, constant: 4),
            nameLabel.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -4),
            nameLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 12),
            
            xmarkImageView.topAnchor.constraint(equalTo: topAnchor, constant: 9),
            xmarkImageView.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -9),
            xmarkImageView.leadingAnchor.constraint(equalTo: nameLabel.trailingAnchor, constant: 10),
            
            trailingAnchor.constraint(equalTo: xmarkImageView.trailingAnchor, constant: 12)
        ])
    }
    func setName(to text: String) {
        nameLabel.text = text
    }
}
