//
//  CarImagePointView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/22.
//

import UIKit

class CarImagePointView: UIView {
    // MARK: - UI properties
    private let point: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "01"
        label.font = .designSystem(.init(name: .regular, size: ._12))
        label.textColor = .hyundaiPrimaryBlue
        label.layer.cornerRadius = 8
        label.layer.borderColor = UIColor.hyundaiPrimaryBlue.cgColor
        label.clipsToBounds = true
        label.backgroundColor = .white
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
    init(number: Int) {
        super.init(frame: .zero)
        setUI()
        setLayout()
        setPoint(to: number)
    }
    
    // MARK: - Helpers
    private func setUI() {
        addSubview(point)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            point.topAnchor.constraint(equalTo: topAnchor),
            point.bottomAnchor.constraint(equalTo: bottomAnchor),
            point.leadingAnchor.constraint(equalTo: leadingAnchor),
            point.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    func setPoint(to number: Int) {
        point.text = String(format: "%02d", number)
    }
}
