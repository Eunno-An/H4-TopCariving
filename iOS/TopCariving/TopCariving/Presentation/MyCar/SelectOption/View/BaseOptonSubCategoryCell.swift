//
//  BaseOptionSubCategoryCell.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/14.
//

import UIKit

class BaseOptionSubCategoryCell: UITableViewCell {
    // MARK: - UI properties
    private var titleImage: UIImageView = {
        let image: UIImage = UIImage(named: "baseOptionSample")!
        let imageView: UIImageView = UIImageView(image: image)
        imageView.layer.cornerRadius = 8
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    private var titleLabel: UILabel = {
        let label: UILabel = UILabel()
        label.text = "ISG 시스템"
        label.font = .designSystem(.init(name: .regular, size: ._14))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    // MARK: - Properties
    static let identifier = "BaseOptionSubCategoryCell"
    
    // MARK: - Lifecycles
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
    }
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        setUI()
        setLayout()
    }
    init(data: BaseOptionSubCategoryModel) {
        self.init(frame: .zero)
        titleLabel.text = data.title
        titleImage.image = UIImage(named: data.imageURL)
    }
    override func layoutSubviews() {
        super.layoutSubviews()
        contentView.backgroundColor = .hyundaiNeutral
        contentView.layer.cornerRadius = 8
        contentView.frame = contentView.frame.inset(by: UIEdgeInsets(top: 4, left: 16, bottom: 4, right: 16))
    }
    
    // MARK: - Helpers
    private func setUI() {
        contentView.addSubview(titleImage)
        contentView.addSubview(titleLabel)
        selectionStyle = .none
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            titleImage.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 8),
            titleImage.heightAnchor.constraint(equalToConstant: 55),
            titleImage.widthAnchor.constraint(equalToConstant: 93),
            titleImage.centerYAnchor.constraint(equalTo: contentView.centerYAnchor),
            
            titleLabel.leadingAnchor.constraint(equalTo: titleImage.trailingAnchor, constant: 17),
            titleLabel.trailingAnchor.constraint(equalTo: contentView.trailingAnchor),
            titleLabel.heightAnchor.constraint(equalToConstant: 22),
            titleLabel.centerYAnchor.constraint(equalTo: contentView.centerYAnchor)
        ])
    }
    func setData(to data: BaseOptionSubCategoryModel) {
        titleImage.setAsyncImage(url: data.imageURL)
        titleLabel.text = data.title
    }
}
