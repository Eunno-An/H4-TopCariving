//
//  SummaryHeaderView.swift
//  TopCariving
//
//  Created by cho seungki on 2023/08/15.
//

import UIKit

class SummaryHeaderView: UICollectionReusableView {
    // MARK: - UI Property
    private let titleLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._14))
        label.text = "총견적금액"
        return label
    }()
    private let priceLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .bold, size: ._18))
        label.text = "47,720,000"
        label.isHidden = true
        return label
    }()
    private let wonLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._12))
        label.text = "원"
        label.isHidden = true
        return label
    }()
    // MARK: - Property
    static let identifier = "SummaryHeaderView"
    
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
        backgroundColor = .hyundaiLightSand
        [titleLabel, priceLabel, wonLabel].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            titleLabel.centerYAnchor.constraint(equalTo: centerYAnchor),
            titleLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            titleLabel.heightAnchor.constraint(equalToConstant: 20),
            
            priceLabel.centerYAnchor.constraint(equalTo: centerYAnchor),
            priceLabel.trailingAnchor.constraint(equalTo: wonLabel.leadingAnchor, constant: -2),
            priceLabel.heightAnchor.constraint(equalToConstant: 24),
            
            wonLabel.centerYAnchor.constraint(equalTo: centerYAnchor, constant: 1),
            wonLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -20),
            wonLabel.heightAnchor.constraint(equalToConstant: 12)
        ])
    }
    func setup(with title: String, price: String? = nil) {
        titleLabel.text = title
        guard let price = price else {
            wonLabel.isHidden = true
            priceLabel.isHidden = true
            return
        }
        wonLabel.isHidden = false
        priceLabel.isHidden = false
        priceLabel.text = price
    }
}
