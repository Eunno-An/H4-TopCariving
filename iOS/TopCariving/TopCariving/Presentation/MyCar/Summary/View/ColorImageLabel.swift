//
//  ColorImageLabel.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/16.
//

import UIKit

class ColorImageLabel: UIView {
    // MARK: - UI properties
    private let detailLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._14))
        label.textColor = .hyundaiBlackGray
        return label
    }()
    private let colorImage: UIImageView = {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.layer.cornerRadius = 8
        imageView.clipsToBounds = true
        return imageView
    }()
    private let nameLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textColor = .hyundaiBlackGray
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
    private func setUI() {
        [detailLabel, colorImage, nameLabel].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            detailLabel.topAnchor.constraint(equalTo: topAnchor),
            detailLabel.bottomAnchor.constraint(equalTo: bottomAnchor),
            detailLabel.leadingAnchor.constraint(equalTo: leadingAnchor),
            detailLabel.widthAnchor.constraint(equalToConstant: 25),
            
            colorImage.widthAnchor.constraint(equalToConstant: 16),
            colorImage.heightAnchor.constraint(equalToConstant: 16),
            colorImage.centerYAnchor.constraint(equalTo: centerYAnchor),
            colorImage.leadingAnchor.constraint(equalTo: detailLabel.trailingAnchor, constant: 12),
            
            nameLabel.topAnchor.constraint(equalTo: topAnchor),
            nameLabel.bottomAnchor.constraint(equalTo: bottomAnchor),
            nameLabel.leadingAnchor.constraint(equalTo: colorImage.trailingAnchor, constant: 12)
        ])
    }
    func setup(with detail: String, image: String, name: String) {
        detailLabel.text = detail
        colorImage.image = UIImage(named: "gray.png")
        nameLabel.text = name
        colorImage.setAsyncImage(url: "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/gray.png", size: .init(width: 16, height: 16))
    }
}
