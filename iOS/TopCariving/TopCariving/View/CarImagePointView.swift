//
//  CarImagePointView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/22.
//

import UIKit

struct CarImagePoint {
    var number: UInt8
    var isBookmarked: Bool
}

class CarImagePointView: UIView {
    // MARK: - UI properties
    private let point: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "01"
        label.font = .designSystem(.init(name: .bold, size: ._12))
        label.textColor = .hyundaiPrimaryBlue
        label.textAlignment = .center
        label.layer.cornerRadius = 11
        label.layer.borderWidth = 2
        label.layer.borderColor = UIColor.hyundaiPrimaryBlue.cgColor
        label.clipsToBounds = true
        label.backgroundColor = .white
        return label
    }()
    
    // MARK: - Properties
    private var isBookmarked: Bool = false
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
    init(point: CarImagePoint) {
        super.init(frame: .zero)
        setUI()
        setLayout()
        setPoint(to: point)
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
    func setPoint(to point: CarImagePoint) {
        self.point.text = String(format: "%02d", point.number)
        isBookmarked = point.isBookmarked
        unCheck()
    }
    private func check() {
        point.textColor = .white
        point.layer.borderColor = UIColor.white.cgColor
        point.backgroundColor = .hyundaiPrimaryBlue
    }
    private func unCheck() {
        point.textColor = .hyundaiPrimaryBlue
        point.layer.borderColor = UIColor.hyundaiPrimaryBlue.cgColor
        point.backgroundColor = .white
    }
    func clickToggle() {
        (isBookmarked ? unCheck() : check())
        isBookmarked.toggle()
    }
}
