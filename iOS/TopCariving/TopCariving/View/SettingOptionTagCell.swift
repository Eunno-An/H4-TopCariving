//
//  SettingOptionTagCell.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import UIKit

struct SettingOptionTagCellModel: Hashable {
    let name: String
    let isSelected: Bool
}

class SettingOptionTagCell: UICollectionViewCell {
    // MARK: - UI properties
    private let nameLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textColor = .hyundaiDarkGray
        return label
    }()

    // MARK: - Properties
    static let identifier = "SettingOptionTagCell"
    override var isSelected: Bool {
        willSet {
            (newValue ? select() : deselect())
        }
    }
    
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
        backgroundColor = .hyundaiNeutral
        layer.cornerRadius = 4
        layer.borderColor = UIColor.hyundaiLightGray.cgColor
        [nameLabel].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            nameLabel.topAnchor.constraint(equalTo: topAnchor, constant: 4),
            nameLabel.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -4),
            nameLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 12),
            nameLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -12)
        ])
    }
    func setUp(with model: SettingOptionTagCellModel) {
        nameLabel.text = model.name
    }
    private func select() {
        backgroundColor = .hyundaiNavy
        nameLabel.textColor = .hyundaiLightGray
        layer.borderWidth = 0
    }
    private func deselect() {
        backgroundColor = .hyundaiNeutral
        nameLabel.textColor = .hyundaiDarkGray
        layer.borderWidth = 0.5
    }
}
