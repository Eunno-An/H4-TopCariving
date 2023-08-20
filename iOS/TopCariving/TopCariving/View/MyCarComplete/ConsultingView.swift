//
//  ConsultingView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/20.
//

import UIKit

class ConsultingView: UIView {
    // MARK: - UI properties
    private let title: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = .designSystem(.init(name: .medium, size: ._16))
        label.text = "딜러에게 가까운 출고일자 상담을 받아볼까요?"
        return label
    }()
    private let consulting: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = .designSystem(.init(name: .medium, size: ._14))
        label.text = "     출고일자 상담신청 바로가기"
        label.textAlignment = .left
        label.layer.cornerRadius = 10
        label.clipsToBounds = true
        label.backgroundColor = .hyundaiLightSand
        return label
    }()
    private let arrow: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "▶"
        label.font = .designSystem(.init(name: .regular, size: ._10))
        label.backgroundColor = .hyundaiLightSand
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
    
    // MARK: - Helpers
    private func setUI() {
        [title, consulting, arrow].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            title.heightAnchor.constraint(equalToConstant: 24),
            title.widthAnchor.constraint(equalToConstant: 300),
            title.topAnchor.constraint(equalTo: topAnchor),
            title.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            consulting.heightAnchor.constraint(equalToConstant: 52),
            consulting.widthAnchor.constraint(equalToConstant: 343),
            consulting.topAnchor.constraint(equalTo: title.bottomAnchor, constant: 12),
            consulting.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            arrow.heightAnchor.constraint(equalToConstant: 12),
            arrow.widthAnchor.constraint(equalToConstant: 10),
            arrow.topAnchor.constraint(equalTo: consulting.topAnchor, constant: 20),
            arrow.trailingAnchor.constraint(equalTo: consulting.trailingAnchor, constant: -20)
        ])
    }
}
