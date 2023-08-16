//
//  OptionDescriptionCell.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/13.
//

import UIKit

class OptionDescriptionCell: UICollectionViewCell {
    // MARK: - UI properties
    private let indexLabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = UIFont(name: "Futura-CondensedExtraBold", size: 12)
        label.textColor = .hyundaiNeutral
        label.textAlignment = .center
        label.backgroundColor = .hyundaiPrimaryBlue
        label.clipsToBounds = true
        label.layer.cornerRadius = 11
        return label
    }()
    private let titleLabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._18))
        label.textColor = .hyundaiPrimaryBlue
        label.textAlignment = .left
        return label
    }()
    private let progressIndexLabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._10))
        label.textColor = .hyundaiNeutral
        label.textAlignment = .center
        label.layer.cornerRadius = 17/2
        label.backgroundColor = .hyundaiDarkGray
        label.clipsToBounds = true
        return label
    }()
    private let separator = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .hyundaiPrimaryBlue
        return view
    }()
    private let descriptionTextView = {
        let textView = UITextView()
        textView.translatesAutoresizingMaskIntoConstraints = false
        textView.font = .designSystem( .init(name: .regular, size: ._14))
        textView.textColor = .hyundaiPrimaryBlue
        textView.layer.cornerRadius = 13
        textView.textContainerInset = .zero
        textView.backgroundColor = .clear
        return textView
    }()
    
    // MARK: - Properties
    static let identifier = "OptionDescriptionCell"
    
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
        layer.borderWidth = 2
        layer.borderColor = UIColor.hyundaiPrimaryBlue.cgColor
        layer.cornerRadius = 8
        backgroundColor = .hyundaiPrimaryBlue.withAlphaComponent(0.1)
        [indexLabel, titleLabel, progressIndexLabel, separator, descriptionTextView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            indexLabel.topAnchor.constraint(equalTo: topAnchor, constant: 24),
            indexLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 32),
            indexLabel.widthAnchor.constraint(equalToConstant: 22),
            indexLabel.heightAnchor.constraint(equalToConstant: 22),
            
            titleLabel.topAnchor.constraint(equalTo: topAnchor, constant: 24),
            titleLabel.leadingAnchor.constraint(equalTo: indexLabel.trailingAnchor, constant: 8),
            titleLabel.trailingAnchor.constraint(equalTo: progressIndexLabel.leadingAnchor),
            titleLabel.heightAnchor.constraint(equalToConstant: 24),
            
            progressIndexLabel.topAnchor.constraint(equalTo: topAnchor, constant: 23),
            progressIndexLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -19),
            progressIndexLabel.widthAnchor.constraint(equalToConstant: 33),
            progressIndexLabel.heightAnchor.constraint(equalToConstant: 17),
            
            separator.topAnchor.constraint(equalTo: indexLabel.bottomAnchor, constant: 8),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 26),
            separator.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -26),
            separator.heightAnchor.constraint(equalToConstant: 1),
            
            descriptionTextView.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 10),
            descriptionTextView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 40),
            descriptionTextView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -40),
            descriptionTextView.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -10)
        ])
    }
    func setUp(with model: OptionDescriptionViewModel) {
        indexLabel.text = String(format: "%02d", model.index+1)
        titleLabel.text = model.title
        progressIndexLabel.text = "\(model.index+1)/\(model.maxIndex)"
        descriptionTextView.text = model.optionDescription
    }
}
