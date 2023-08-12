//
//  BaseOptionSubCategoryStackView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/12.
//

import UIKit

class BaseOptionSubCategoryStackView: UIStackView {
    // MARK: - UI properties
    
    // MARK: - Properties
    
    // MARK: - Lifecycles
    required init(coder: NSCoder) {
        super.init(coder: coder)
        setUpStackView()
        test()
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUpStackView()
        test()
    }
    
    convenience init(arrangedSubviews views: [BaseOptionSubCategoryCell]) {
        self.init(frame: .zero)
        views.forEach { addArrangedSubview($0) }
    }
    
    // MARK: - Helpers
    func test() {
        self.addArrangedSubview(BaseOptionSubCategoryCell())
        self.addArrangedSubview(BaseOptionSubCategoryCell())
    }
    
    func setUpStackView() {
        axis = .vertical
        distribution = .fillEqually
        alignment = .fill
        spacing = 8
        translatesAutoresizingMaskIntoConstraints = false
    }
}
