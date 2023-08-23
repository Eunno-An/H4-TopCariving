//
//  ArchivingSettingButton.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/20.
//

import UIKit

class ArchivingSettingButton: UIView {
    // MARK: - UI properties
    private let titleLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private let chevronImageView: UIImageView = {
        let imageView = UIImageView(image: UIImage(systemName: "chevron.right")?.resized(to: 10))
        imageView.contentMode = .center
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.tintColor = .hyundaiDarkGray
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
        layer.cornerRadius = 8
        translatesAutoresizingMaskIntoConstraints = false
        backgroundColor = .hyundaiLightSand
        [titleLabel, chevronImageView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            titleLabel.centerYAnchor.constraint(equalTo: centerYAnchor),
            titleLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 12),
            titleLabel.heightAnchor.constraint(equalToConstant: 22),
            
            chevronImageView.centerYAnchor.constraint(equalTo: centerYAnchor),
            chevronImageView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -12),
            chevronImageView.widthAnchor.constraint(equalToConstant: 24),
            chevronImageView.heightAnchor.constraint(equalToConstant: 24)
        ])
    }
    func setTitle(to title: String, font: HyundaiFont.Custom, color: UIColor) {
        titleLabel.text = title
        titleLabel.font = .designSystem(font)
        titleLabel.textColor = color
    }
}
