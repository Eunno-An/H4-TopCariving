//
//  SummaryCell.swift
//  TopCariving
//
//  Created by cho seungki on 2023/08/15.
//

import UIKit

class SummaryCell: UICollectionViewCell {
    // MARK: - UI Property
    let titleLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.text = "펠리세이드 Le Blanc"
        return label
    }()
    let priceLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.text = "47,3400,000 원"
        return label
    }()
    
    // MARK: - Properties
    static let identifier = "SummaryCell"
    
    // MARK: - LifeCycle
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
    
    // MARK: - Helper
    private func setUI() {
        [titleLabel, priceLabel].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            titleLabel.centerYAnchor.constraint(equalTo: centerYAnchor),
            titleLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            titleLabel.heightAnchor.constraint(equalToConstant: 22),
            
            priceLabel.centerYAnchor.constraint(equalTo: centerYAnchor),
            priceLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -20),
            priceLabel.heightAnchor.constraint(equalToConstant: 22)
        ])
    }
    func setup(with title: String, price: String) {
        titleLabel.text = title
        priceLabel.text = price
    }
}
