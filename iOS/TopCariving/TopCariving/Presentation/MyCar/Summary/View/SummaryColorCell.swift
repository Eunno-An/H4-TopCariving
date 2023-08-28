//
//  SummaryColorCell.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/17.
//

import UIKit

class SummaryColorCell: UICollectionViewCell {
    // MARK: - UI properties
    private let colorImageLabel = ColorImageLabel()
    
    // MARK: - Properties
    static let identifier = "SummaryColorCell"
    
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
        addSubview(colorImageLabel)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            colorImageLabel.topAnchor.constraint(equalTo: topAnchor),
            colorImageLabel.bottomAnchor.constraint(equalTo: bottomAnchor),
            colorImageLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            colorImageLabel.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    func setUp(with category: String, image: String, name: String) {
        colorImageLabel.setup(with: category, image: image, name: name)
    }
}
