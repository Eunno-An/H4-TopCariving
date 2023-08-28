//
//  TrimReviewedView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/20.
//

import UIKit

class TrimReviewedView: UIView {
    // MARK: - UI properties
    private let titleLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = .designSystem(.init(name: .medium, size: ._20))
        label.textColor = .hyundaiBlackGray
        label.textAlignment = .left
        return label
    }()
    private let separator: UIView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .black
        return view
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
    
    // MARK: - Helpers
    func setUI() {
        translatesAutoresizingMaskIntoConstraints = false
        [titleLabel, separator].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: topAnchor),
            titleLabel.leadingAnchor.constraint(equalTo: leadingAnchor),
            titleLabel.heightAnchor.constraint(equalToConstant: 28),
            titleLabel.trailingAnchor.constraint(equalTo: trailingAnchor),
            
            separator.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 4),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor),
            separator.trailingAnchor.constraint(equalTo: trailingAnchor),
            separator.heightAnchor.constraint(equalToConstant: 4),
            separator.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
    }
    func setUp(with title: String) {
        titleLabel.attributedText = .makeBold(title + " 에 대해 시승자들은 이런 후기를 남겼어요",
                                              boldText: title,
                                              font: .init(name: .regular, size: ._14),
                                              boldFont: .init(name: .medium, size: ._20))
    }
}
