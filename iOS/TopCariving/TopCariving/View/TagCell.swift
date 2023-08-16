//
//  TagCell.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/12.
//

import UIKit

class TagCell: UICollectionViewCell {
    // MARK: - UI properties
    private let tagLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textAlignment = .center
        label.setFont(to: .init(name: .regular, size: ._14))
        return label
    }()
    
    // MARK: - Properties
    static let identifier = "TagCell"
    
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
        backgroundColor = .hyundaiLightSand
        layer.cornerRadius = 8
        addSubview(tagLabel)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            tagLabel.topAnchor.constraint(equalTo: topAnchor),
            tagLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 5),
            tagLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -5),
            tagLabel.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
    }
    func setTag(to text: String) {
        tagLabel.text = text
    }
}
