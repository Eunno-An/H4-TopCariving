//
//  ArchivingSearchButton.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/20.
//

import UIKit

class ArchivingSearchButton: UIView {
    // MARK: - UI properties
    private let carNameLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._18))
        label.text = "펠리세이드"
        label.textColor = .hyundaiBlackGray
        return label
    }()
    private let sliderImageView: UIImageView = {
        let image = UIImage(systemName: "slider.horizontal.3")
        let imageView = UIImageView(image: image)
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.tintColor = .hyundaiBlackGray
        return imageView
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
        translatesAutoresizingMaskIntoConstraints = false
        backgroundColor = .hyundaiLightSand
        layer.cornerRadius = 8
        [carNameLabel, sliderImageView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            carNameLabel.centerYAnchor.constraint(equalTo: centerYAnchor),
            carNameLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 16),
            carNameLabel.heightAnchor.constraint(equalToConstant: 24),
            
            sliderImageView.centerYAnchor.constraint(equalTo: centerYAnchor),
            sliderImageView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -7),
            sliderImageView.widthAnchor.constraint(equalToConstant: 28),
            sliderImageView.heightAnchor.constraint(equalToConstant: 28)
        ])
    }
}
