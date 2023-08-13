//
//  BaseOptionMainCategoryStackView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/12.
//

import UIKit

class BaseOptionMainCategoryStackView: UIStackView {
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
    
    convenience init(arrangedSubviews views: [BaseOptionMainCategoryView]) {
        self.init(frame: .zero)
        views.forEach {
            addArrangedSubview($0)
        }
    }
    
    // MARK: - Helpers
    func test() {
        self.addArrangedSubview(BaseOptionMainCategoryView())
        self.addArrangedSubview(BaseOptionMainCategoryView())
        self.addArrangedSubview(BaseOptionMainCategoryView())
        self.addArrangedSubview(BaseOptionMainCategoryView())
    }
    
    func setUpStackView() {
        axis = .vertical
        distribution = .fill
        alignment = .fill
        spacing = 12
    }
}
