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
    convenience init(arrangedSubviews views: [BaseOptionSubCategoryView]) {
        self.init(frame: .zero)
        views.forEach {
            addArrangedSubview($0)
        }
    }
    
    // MARK: - Helpers
    func setUpStackView() {
        axis = .vertical
        distribution = .fillEqually
        alignment = .fill
        spacing = 8
    }
    func test() {
        self.addArrangedSubview(BaseOptionSubCategoryView())
        self.addArrangedSubview(BaseOptionSubCategoryView())
    }
}
