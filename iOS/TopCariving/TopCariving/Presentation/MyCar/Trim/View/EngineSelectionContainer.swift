//
//  EngineSelectionContainer.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/20.
//

import UIKit

struct EngineModel {
    let title: String
    let price: String
    let description: String
    let outputDescription: String
    let torqueDescription: String
}

class EngineSelectionContainer: UIView, Foldable {
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
    private let descriptionTextView = {
        let textView = UITextView()
        textView.translatesAutoresizingMaskIntoConstraints = false
        textView.font = .designSystem( .init(name: .regular, size: ._12))
        textView.textColor = .hyundaiPrimaryBlue
        textView.textContainerInset = .zero
        textView.backgroundColor = .clear
        textView.isSelectable = false
        return textView
    }()
    private let maximumOutputLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .bold, size: ._14))
        label.textColor = .hyundaiPrimaryBlue
        label.text = "최고출력"
        return label
    }()
    private let maximumTorqueLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .bold, size: ._14))
        label.textColor = .hyundaiPrimaryBlue
        label.text = "최대토크"
        return label
    }()
    private let outputDescriptionLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textColor = .hyundaiPrimaryBlue
        label.textAlignment = .right
        return label
    }()
    private let torqueDescriptionLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textColor = .hyundaiPrimaryBlue
        label.textAlignment = .right
        return label
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
    }

    // MARK: - Helpers
    private func setUI() {
        layer.cornerRadius = 8
        translatesAutoresizingMaskIntoConstraints = false
        [titleLabel, priceLabel, wonLabel, separator, descriptionTextView, maximumOutputLabel,
         maximumTorqueLabel, outputDescriptionLabel, torqueDescriptionLabel].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        heightConstraint = heightAnchor.constraint(equalToConstant: 71)
        heightConstraint?.isActive = true
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: topAnchor, constant: 22),
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
            
            descriptionTextView.topAnchor.constraint(equalTo: priceLabel.bottomAnchor, constant: 10),
            descriptionTextView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            descriptionTextView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -20),
            descriptionTextView.heightAnchor.constraint(equalToConstant: 39),
            
            separator.topAnchor.constraint(equalTo: descriptionTextView.bottomAnchor, constant: 12),
            separator.heightAnchor.constraint(equalToConstant: 1),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            separator.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -20),
            
            maximumOutputLabel.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 12),
            maximumOutputLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            maximumOutputLabel.widthAnchor.constraint(equalToConstant: 60),
            maximumOutputLabel.heightAnchor.constraint(equalToConstant: 18),
            
            outputDescriptionLabel.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 12),
            outputDescriptionLabel.leadingAnchor.constraint(equalTo: maximumOutputLabel.trailingAnchor),
            outputDescriptionLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -20),
            outputDescriptionLabel.heightAnchor.constraint(equalToConstant: 18),
            
            maximumTorqueLabel.topAnchor.constraint(equalTo: maximumOutputLabel.bottomAnchor, constant: 8),
            maximumTorqueLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 20),
            maximumTorqueLabel.widthAnchor.constraint(equalToConstant: 60),
            maximumTorqueLabel.heightAnchor.constraint(equalToConstant: 18),
            
            torqueDescriptionLabel.topAnchor.constraint(equalTo: outputDescriptionLabel.bottomAnchor, constant: 8),
            torqueDescriptionLabel.leadingAnchor.constraint(equalTo: maximumTorqueLabel.trailingAnchor),
            torqueDescriptionLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -20),
            torqueDescriptionLabel.heightAnchor.constraint(equalToConstant: 18)
        ])
    }
    func fold() {
        heightConstraint?.constant = 71
        [descriptionTextView, separator, maximumOutputLabel,
         maximumTorqueLabel, outputDescriptionLabel, torqueDescriptionLabel].forEach {
            $0.isHidden = true
        }
        layer.borderWidth = 0
        backgroundColor = .hyundaiLightSand
        [titleLabel, priceLabel, wonLabel].forEach {
            $0.textColor = .hyundaiBlackGray
        }
    }
    func unfold() {
        heightConstraint?.constant = 186
        [descriptionTextView, separator, maximumOutputLabel,
         maximumTorqueLabel, outputDescriptionLabel,
         torqueDescriptionLabel].forEach {
            $0.isHidden = false
        }
        layer.borderWidth = 2
        layer.borderColor = UIColor.hyundaiPrimaryBlue.cgColor
        backgroundColor = UIColor(red: 0, green: 0.173, blue: 0.373, alpha: 0.1)
        [titleLabel, priceLabel, wonLabel].forEach {
            $0.textColor = .hyundaiPrimaryBlue
        }
    }
    func setUp(with model: EngineModel) {
        titleLabel.text = model.title
        priceLabel.text = model.price
        descriptionTextView.text = model.description
        outputDescriptionLabel.text = model.outputDescription
        torqueDescriptionLabel.text = model.torqueDescription
    }
}
