//
//  BaseOptionSubCategoryCell.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/12.
//

import UIKit

class BaseOptionSubCategoryCell: UIView {
    // MARK: - UI properties
    private let temp: UIView = {
        let view: UIView = UIView()
        view.backgroundColor = .hyundaiNeutral
        view.layer.cornerRadius = 8
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
    
    override func layoutSubviews() {
        super.layoutSubviews()
        temp.frame = bounds
    }
    
    // MARK: - Helpers
    func setUI() {
        addSubview(temp)
    }
    
    func setLayout() {
        translatesAutoresizingMaskIntoConstraints = false
        temp.translatesAutoresizingMaskIntoConstraints = false
        heightAnchor.constraint(equalToConstant: 71).isActive = true
    }
}
