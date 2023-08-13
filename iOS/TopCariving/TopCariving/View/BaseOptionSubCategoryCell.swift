//
//  BaseOptionSubCategoryCell.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/12.
//

import UIKit

class BaseOptionSubCategoryCell: UIView {
    // MARK: - UI properties
    private let imageView: UIImageView = {
        let image: UIImage = UIImage(named: "baseOptionSample")!
        let imageView: UIImageView = UIImageView(image: image)
        imageView.layer.cornerRadius = 8
        return imageView
    }()
    private let titleLabel: UILabel = {
        let label: UILabel = UILabel()
        label.text = "ISG 시스템"
        label.font = .designSystem(.init(name: .regular, size: ._14))
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
    
    override func layoutSubviews() {
        super.layoutSubviews()
    }
    
    // MARK: - Helpers
    func setUI() {
        backgroundColor = .hyundaiNeutral
        imageView.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        addSubview(imageView)
        addSubview(titleLabel)
    }
    
    func setLayout() {
        translatesAutoresizingMaskIntoConstraints = false
        heightAnchor.constraint(equalToConstant: 71).isActive = true
        
        NSLayoutConstraint.activate([
            imageView.centerYAnchor.constraint(equalTo: centerYAnchor),
            imageView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 8),
            imageView.heightAnchor.constraint(equalToConstant: 55),
            imageView.widthAnchor.constraint(equalToConstant: 93),
            
            titleLabel.centerYAnchor.constraint(equalTo: centerYAnchor),
            titleLabel.leadingAnchor.constraint(equalTo: imageView.trailingAnchor, constant: 17),
            titleLabel.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
}
