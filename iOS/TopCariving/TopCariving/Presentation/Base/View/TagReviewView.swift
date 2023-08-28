//
//  MyCarTagReviewView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/12.
//

import UIKit

class TagReviewView: UIView {
    // MARK: - UI properties
    private let optionNameLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._20))
        return label
    }()
    private let reviewLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.text = "에 대해 시승자들은 이런 후기를 남겼어요"
        return label
    }()
    private let tagsView = TagsView()

    // MARK: - Properties
    lazy var tagsViewBottomAnchor = tagsView.collectionViewBottomAnchor
    
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
        backgroundColor = .white
        layer.cornerRadius = 8
        [optionNameLabel, reviewLabel, tagsView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            optionNameLabel.topAnchor.constraint(equalTo: topAnchor),
            optionNameLabel.leadingAnchor.constraint(equalTo: leadingAnchor),
            optionNameLabel.trailingAnchor.constraint(equalTo: trailingAnchor),
            optionNameLabel.heightAnchor.constraint(equalToConstant: 28),
            
            reviewLabel.topAnchor.constraint(equalTo: optionNameLabel.bottomAnchor, constant: 5),
            reviewLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 2),
            reviewLabel.trailingAnchor.constraint(equalTo: trailingAnchor),
            reviewLabel.heightAnchor.constraint(equalToConstant: 22),
            
            tagsView.topAnchor.constraint(equalTo: reviewLabel.bottomAnchor, constant: 12),
            tagsView.leadingAnchor.constraint(equalTo: leadingAnchor),
            tagsView.trailingAnchor.constraint(equalTo: trailingAnchor),
            bottomAnchor.constraint(equalTo: tagsView.bottomAnchor, constant: +10)
        ])
    }
    func refresh(by tags: [String], with name: String) {
        tagsView.refresh(by: tags)
        optionNameLabel.text = name
    }
}
