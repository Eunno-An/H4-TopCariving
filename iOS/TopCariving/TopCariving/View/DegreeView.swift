//
//  DegreeView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/09.
//

import UIKit

class DegreeView: UIView {
    // MARK: - UI properties
    private var degreeLabel: UILabel = {
        let label: UILabel = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "360"
        label.font = .designSystem(.init(name: .medium, size: ._12))
        label.textColor = .hyundaiDarkGray
        return label
    }()
    private let gradient = CAGradientLayer()
    
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setRadialGradient()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setRadialGradient()
    }

    // MARK: - Helpers
    func setUI() {
        translatesAutoresizingMaskIntoConstraints = false
        degreeLabel.translatesAutoresizingMaskIntoConstraints = false
        addSubview(degreeLabel)
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            degreeLabel.widthAnchor.constraint(equalToConstant: 32),
            degreeLabel.heightAnchor.constraint(equalToConstant: 21),
            degreeLabel.centerXAnchor.constraint(equalTo: centerXAnchor),
            degreeLabel.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -18)
        ])
    }
    override func layoutSublayers(of layer: CALayer) {
        super.layoutSublayers(of: layer)
        setRadialGradient()
        layer.insertSublayer(gradient, at: 0)
    }
    func setRadialGradient() {
        gradient.type = .radial
        gradient.colors = [
            UIColor.white.cgColor, UIColor.white.withAlphaComponent(0.1).cgColor
        ]
        gradient.locations = [0, 1]
        gradient.startPoint = CGPoint(x: 0.5, y: 0.5)
        gradient.endPoint = CGPoint(x: 0, y: 0)
        gradient.frame = layer.bounds
        gradient.cornerRadius = 50
    }
}
