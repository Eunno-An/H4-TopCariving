//
//  BaseOptionSubCategoryCell.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/12.
//

import Combine
import UIKit

class BaseOptionSubCategoryCell: UIView {
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
        setTapAction()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setTapAction()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
    }
    
    // MARK: - Helpers
    func setUI() {
        backgroundColor = .hyundaiNeutral
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
    
    func setTapAction() {
        tapPublisher().sink {
            NotificationCenter.default.post(name: Notification.Name("SubCategoryCellTapped"), object: nil)
        }.store(in: &bag)
    }
}
