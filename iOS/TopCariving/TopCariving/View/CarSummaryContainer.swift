//
//  CarSummaryContainerCell.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/03.
//

import Combine
import UIKit

class CarSummaryContainer: FoldableView {
    // MARK: - UI properties
    private let iconStackView = UIStackView()
    
    // MARK: - Helpers
    override func setUI() {
        super.setUI()
        addSubview(iconStackView)
    }
    
    override func setLayout() {
        super.setLayout()
        iconStackView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            iconStackView.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 23),
            iconStackView.centerXAnchor.constraint(equalTo: centerXAnchor),
            iconStackView.widthAnchor.constraint(equalTo: widthAnchor, multiplier: 1, constant: -40)
        ])
    }
    
    override func fold() {
        super.fold()
        iconStackView.isHidden = true
        heightConstraint?.constant = 71
    }
    
    override func unfold() {
        super.unfold()
        iconStackView.isHidden = false
        heightConstraint?.constant = 215
    }
    
    func setInfo(to trimName: String, price: String, icons: [(image: String, text: String)]) {
        setTitle(to: trimName)
        setPrice(to: price)
        icons.forEach { (image, text) in
            let stackView = UIStackView(arrangedSubviews: [makeIconImageView(by: image), makeIconLabel(by: text)])
            stackView.axis = .vertical
            iconStackView.addArrangedSubview(stackView)
        }
    }
    
    private func makeIconImageView(by url: String) -> UIImageView {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.setAsyncImage(url: url, size: .init(width: 40, height: 40))
        
        imageView.contentMode = .scaleAspectFit
        return imageView
    }
    
    private func makeIconLabel(by text: String) -> UILabel {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = .designSystem(.init(name: .regular, size: ._14))
        label.textColor = .hyundaiPrimaryBlue
        label.numberOfLines = 0
        label.lineBreakMode = .byWordWrapping
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.alignment = .center
        paragraphStyle.lineHeightMultiple = 1.48
        label.attributedText = NSMutableAttributedString(
            string: text,
            attributes: [NSAttributedString.Key.kern: -0.28, NSAttributedString.Key.paragraphStyle: paragraphStyle]
        )
        return label
    }
}
