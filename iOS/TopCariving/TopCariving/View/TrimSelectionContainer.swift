//
//  TrimSelectionContainer.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/20.
//

import UIKit

struct TrimSelectionModel {
    let title: String
    let price: String
    let description: String
}

class TrimSelectionContainer: FoldableView {
    // MARK: - UI properties
    private let descriptionTextView: UITextView = {
        let textView = UITextView()
        textView.translatesAutoresizingMaskIntoConstraints = false
        textView.font = .designSystem(.init(name: .regular, size: ._12))
        textView.textColor = .hyundaiPrimaryBlue
        textView.isSelectable = false
        textView.backgroundColor = .clear
        textView.textContainerInset = .zero 
        return textView
    }()

    // MARK: - Helpers
    override func setUI() {
        super.setUI()
        addSubview(descriptionTextView)
    }
    override func setLayout() {
        super.setLayout()
        NSLayoutConstraint.activate([
            descriptionTextView.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 13),
            descriptionTextView.centerXAnchor.constraint(equalTo: centerXAnchor),
            descriptionTextView.widthAnchor.constraint(equalTo: widthAnchor, multiplier: 1, constant: -40),
            descriptionTextView.heightAnchor.constraint(equalToConstant: 39)
        ])
    }
    override func fold() {
        super.fold()
        descriptionTextView.isHidden = true
        heightConstraint?.constant = 71
    }
    override func unfold() {
        super.unfold()
        descriptionTextView.isHidden = false
        heightConstraint?.constant = 135
    }
    func setUp(with model: TrimSelectionModel) {
        setTitle(to: model.title)
        setPrice(to: model.price)
        descriptionTextView.text = model.description
    }
}
