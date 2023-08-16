//
//  OptionDescriptionView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/14.
//

import UIKit

class OptionDescriptionView: UIView, Foldable {
    // MARK: - UI properties
    private let titleLabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._18))
        label.textColor = .hyundaiPrimaryBlue
        label.textAlignment = .left
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
        textView.textColor = .hyundaiPrimaryBlue
        textView.layer.cornerRadius = 13
        textView.backgroundColor = .clear
        textView.isSelectable = false
        textView.font = .designSystem(.init(name: .regular, size: ._14))
        textView.textContainerInset = .zero
        return textView
    }()
    
    // MARK: - Properties
    var heightConstraint: NSLayoutConstraint?
    
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
        translatesAutoresizingMaskIntoConstraints = false
        layer.borderWidth = 2
        layer.borderColor = UIColor.hyundaiPrimaryBlue.cgColor
        layer.cornerRadius = 8
        backgroundColor = .hyundaiPrimaryBlue.withAlphaComponent(0.1)
        [titleLabel, separator, descriptionTextView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        heightConstraint = heightAnchor.constraint(greaterThanOrEqualToConstant: 70)
        heightConstraint?.isActive = true
        
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: topAnchor, constant: 24),
            titleLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 32),
            titleLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -32),
            titleLabel.heightAnchor.constraint(greaterThanOrEqualToConstant: 24),
            
            separator.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 8),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 32),
            separator.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -32),
            separator.heightAnchor.constraint(equalToConstant: 1),
            
            descriptionTextView.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 8),
            descriptionTextView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 32),
            descriptionTextView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -32),
            descriptionTextView.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -10)
        ])
    }
    func setUp(with model: OptionDescriptionViewModel) {
        titleLabel.text = model.title
        descriptionTextView.text = model.optionDescription
        (model.optionDescription == "-" ? fold() : unfold())
    }
    func fold() {
        heightConstraint?.constant = 70
        [separator, descriptionTextView].forEach {
            $0.isHidden = true
        }
    }
    func unfold() {
        heightConstraint?.constant = descriptionTextView.contentSize.height + 110
        [separator, descriptionTextView].forEach {
            $0.isHidden = false
        }
    }
}
