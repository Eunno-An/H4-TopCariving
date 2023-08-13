//
//  BaseOptionMainCategoryTitleView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/12.
//

import UIKit

class BaseOptionMainCategoryTitleView: UIView {
    // MARK: - UI properties
    private var title: UILabel = {
        let label: UILabel = UILabel()
        label.text = "파워트레인/성능"
        label.font = .designSystem(.init(name: .medium, size: ._14))
        label.textAlignment = .left
        label.baselineAdjustment = .alignCenters
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    var arrow: UIButton = {
        let button: UIButton = UIButton()
        button.setImage(UIImage(named: "arrow_down"), for: .normal)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
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
        backgroundColor = .hyundaiLightSand
        layer.cornerRadius = 8
        [title, arrow].forEach {
            addSubview($0)
        }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            title.topAnchor.constraint(equalTo: topAnchor, constant: 12),
            title.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 12),
            title.trailingAnchor.constraint(equalTo: arrow.leadingAnchor),
            title.heightAnchor.constraint(equalToConstant: 22),
            
            arrow.topAnchor.constraint(equalTo: topAnchor, constant: 11),
            arrow.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -11),
            arrow.heightAnchor.constraint(equalToConstant: 24),
            arrow.widthAnchor.constraint(equalToConstant: 24)
        ])
    }
    func setArrowImage(to name: String) {
        arrow.setImage(UIImage(named: name), for: .normal)
    }
    func setTitle(to text: String) {
        title.text = text
    }
}
