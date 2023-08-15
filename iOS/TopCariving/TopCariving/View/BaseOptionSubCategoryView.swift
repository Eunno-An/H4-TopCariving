//
//  BaseOptionSubCategoryView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/12.
//

import Combine
import UIKit

class BaseOptionSubCategoryView: UIView {
    // MARK: - UI properties
    private let imageView: UIImageView = {
        let image: UIImage = UIImage(named: "baseOptionSample")!
        let imageView: UIImageView = UIImageView(image: image)
        imageView.layer.cornerRadius = 8
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    private let titleLabel: UILabel = {
        let label: UILabel = UILabel()
        label.text = "ISG 시스템"
        label.font = .designSystem(.init(name: .regular, size: ._14))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()
    
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
    func setUI() {
        backgroundColor = .hyundaiNeutral
        addSubview(imageView)
        addSubview(titleLabel)
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            imageView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 8),
            imageView.heightAnchor.constraint(equalToConstant: 55),
            imageView.widthAnchor.constraint(equalToConstant: 93),
            imageView.centerYAnchor.constraint(equalTo: centerYAnchor),
            
            titleLabel.leadingAnchor.constraint(equalTo: imageView.trailingAnchor, constant: 17),
            titleLabel.trailingAnchor.constraint(equalTo: trailingAnchor),
            titleLabel.heightAnchor.constraint(equalToConstant: 22),
            titleLabel.centerYAnchor.constraint(equalTo: centerYAnchor)
        ])
    }
}
