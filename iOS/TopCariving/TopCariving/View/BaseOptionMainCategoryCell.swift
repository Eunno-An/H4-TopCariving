//
//  BaseOptionMainCategoryCell.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/14.
//

import UIKit

class BaseOptionMainCategoryCell: UITableViewCell {
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
    private var arrow: UIButton = {
        let button: UIButton = UIButton()
        button.setImage(UIImage(named: "arrow_down"), for: .normal)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    
    // MARK: - Properties
    
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
    override func layoutSubviews() {
        super.layoutSubviews()
        contentView.backgroundColor = .hyundaiLightSand
        contentView.layer.cornerRadius = 8
        contentView.frame = contentView.frame.inset(by: UIEdgeInsets(top: 6, left: 16, bottom: 6, right: 16))
    }
    // MARK: - Helpers
    func setUI() {
        contentView.addSubview(title)
        contentView.addSubview(arrow)
        selectionStyle = .none
    }
    func setLayout() {
        contentView.heightAnchor.constraint(equalToConstant: 70).isActive = true
        NSLayoutConstraint.activate([
            title.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 12),
            title.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 12),
            title.trailingAnchor.constraint(equalTo: arrow.leadingAnchor),
            title.heightAnchor.constraint(equalToConstant: 22),
            
            arrow.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 11),
            arrow.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: -11),
            arrow.heightAnchor.constraint(equalToConstant: 24),
            arrow.widthAnchor.constraint(equalToConstant: 24)
        ])
    }
}
