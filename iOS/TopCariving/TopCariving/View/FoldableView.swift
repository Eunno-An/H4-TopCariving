//
//  FoldableView.swift
//  TopCariving
//
//  Created by cho seungki on 2023/08/05.
//

import Combine
import UIKit

protocol Foldable {
    var heightConstraint: NSLayoutConstraint? { get set }
    
    func fold()
    func unfold()
}

class FoldableView: TappableView, Foldable {
    // MARK: - UI properties
    private let titleLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = .designSystem(.init(name: .bold, size: ._18))
        label.textColor = .hyundaiPrimaryBlue
        return label
    }()
    
    private let priceLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = .designSystem(.init(name: .bold, size: ._18))
        label.textColor = .hyundaiPrimaryBlue
        label.textAlignment = .right
        return label
    }()
    
    private let wonLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = .designSystem(.init(name: .regular, size: ._12))
        label.text = "원"
        label.textColor = .hyundaiPrimaryBlue
        return label
    }()
    
    let separator: UIView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.layer.borderColor = UIColor(red: 0, green: 0.173, blue: 0.373, alpha: 0.6).cgColor
        view.layer.borderWidth = 1
        return view
    }()
    
    // MARK: - Properties
    var heightConstraint: NSLayoutConstraint?
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        unfold()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        unfold()
    }
    
    // MARK: - Helpers
    func setUI() {
        layer.cornerRadius = 8
        translatesAutoresizingMaskIntoConstraints = false
        [titleLabel, priceLabel, wonLabel, separator].forEach {
            addSubview($0)
        }
    }
    
    func setLayout() {
        separator.translatesAutoresizingMaskIntoConstraints = false
        heightConstraint = heightAnchor.constraint(equalToConstant: 71)
        heightConstraint?.isActive = true
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: topAnchor, constant: 25),
            titleLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            titleLabel.widthAnchor.constraint(equalTo: widthAnchor, multiplier: 0.5),
            titleLabel.heightAnchor.constraint(equalToConstant: 27),
            
            wonLabel.topAnchor.constraint(equalTo: topAnchor, constant: 28),
            wonLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -20),
            wonLabel.heightAnchor.constraint(equalToConstant: 22),
            wonLabel.widthAnchor.constraint(equalToConstant: 15),
            
            priceLabel.topAnchor.constraint(equalTo: topAnchor, constant: 23),
            priceLabel.leadingAnchor.constraint(equalTo: titleLabel.trailingAnchor),
            priceLabel.trailingAnchor.constraint(equalTo: wonLabel.leadingAnchor, constant: -4),
            priceLabel.heightAnchor.constraint(equalToConstant: 31),
            
            separator.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 12),
            separator.heightAnchor.constraint(equalToConstant: 1),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            separator.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -20)
        ])
    }
    
    func fold() {
        separator.isHidden = true
        layer.borderWidth = 0
        backgroundColor = UIColor.hyundaiLightSand
    }
    
    func unfold() {
        separator.isHidden = false
        layer.borderWidth = 1
        layer.borderColor = UIColor(red: 0, green: 0.173, blue: 0.373, alpha: 0.6).cgColor
        backgroundColor = UIColor(red: 0, green: 0.173, blue: 0.373, alpha: 0.1)
    }
    
    func setTitle(to title: String) {
        titleLabel.text = title
    }
    
    func setPrice(to price: String) {
        priceLabel.text = price
    }
}
