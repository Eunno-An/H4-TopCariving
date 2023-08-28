//
//  ArchivingDetailCell.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import UIKit

struct ArchivingDetailCellModel: Hashable {
    let image: String
    let index: UInt8
    let optionName: String
    let detailOptions: [String]
    let tags: [String]
}

class ArchivingDetailCell: UICollectionViewCell {
    // MARK: - UI properties
    private let optionImageView: UIImageView = {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.clipsToBounds = true
        imageView.layer.cornerRadius = 4
        imageView.contentMode = .scaleAspectFill
        return imageView
    }()
    private let progressIndexLabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._10))
        label.textColor = .hyundaiNeutral
        label.textAlignment = .center
        label.layer.cornerRadius = 22/2
        label.backgroundColor = .hyundaiPrimaryBlue
        label.clipsToBounds = true
        return label
    }()
    private let optionNameLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._20))
        label.textColor = .hyundaiBlackGray
        return label
    }()
    private let separator: UIView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .hyundaiLightGray
        return view
    }()
    private let detailLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._16))
        label.textColor = .hyundaiPrimaryBlue
        label.numberOfLines = 0
        return label
    }()
    private let tagsView = TagsView()

    // MARK: - Properties
    static let identifier = "ArchivingDetailCell"
    
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
        backgroundColor = .white
        
        [optionImageView, progressIndexLabel, optionNameLabel, separator, detailLabel, tagsView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            optionImageView.topAnchor.constraint(equalTo: topAnchor, constant: 21),
            optionImageView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 16),
            optionImageView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -16),
            optionImageView.heightAnchor.constraint(equalTo: widthAnchor, multiplier: 0.35),
            
            progressIndexLabel.topAnchor.constraint(equalTo: optionImageView.bottomAnchor, constant: 20),
            progressIndexLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 17),
            progressIndexLabel.widthAnchor.constraint(equalToConstant: 22),
            progressIndexLabel.heightAnchor.constraint(equalToConstant: 22),
            
            optionNameLabel.topAnchor.constraint(equalTo: optionImageView.bottomAnchor, constant: 17),
            optionNameLabel.leadingAnchor.constraint(equalTo: progressIndexLabel.trailingAnchor, constant: 3),
            optionNameLabel.heightAnchor.constraint(equalToConstant: 28),
            optionNameLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -16),
            
            separator.topAnchor.constraint(equalTo: optionNameLabel.bottomAnchor, constant: 9),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 16),
            separator.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -16),
            separator.heightAnchor.constraint(equalToConstant: 1),
            
            detailLabel.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 8),
            detailLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 16),
            detailLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -16),
            
            tagsView.topAnchor.constraint(equalTo: detailLabel.bottomAnchor, constant: 5),
            tagsView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 16),
            tagsView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -16),
            tagsView.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -10)
        ])
    }
    func setUp(with model: ArchivingDetailCellModel) {
        optionImageView.setAsyncImage(url: model.image, size: .init(width: CGRect.screenBounds.width - 32,
                                                                    height: (CGRect.screenBounds.width - 32)*0.35))
        progressIndexLabel.text = String(format: "%02d", model.index+1)
        optionNameLabel.text = model.optionName
        detailLabel.text = model.detailOptions.joined(separator: " | ")
        separator.isHidden = model.detailOptions.count == 0
        tagsView.refresh(by: model.tags)
    }
}
