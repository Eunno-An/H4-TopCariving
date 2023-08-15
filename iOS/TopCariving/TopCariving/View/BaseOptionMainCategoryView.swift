//
//  BaseOptionMainCategoryView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/12.
//

import Combine
import UIKit

class BaseOptionMainCategoryView: UIView {
    // MARK: - UI properties
    private var titleView = BaseOptionMainCategoryTitleView()
    
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()

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
    convenience init(data: BaseOptionMainCategoryModel) {
        self.init(frame: .zero)
        titleView.setTitle(to: data.title)
    }
    
    // MARK: - Helpers
    func setUI() {
        titleView.translatesAutoresizingMaskIntoConstraints = false
        addSubview(titleView)
    }
    func setLayout() {
        heightAnchor.constraint(equalToConstant: 46).isActive = true
        NSLayoutConstraint.activate([
            titleView.topAnchor.constraint(equalTo: topAnchor),
            titleView.bottomAnchor.constraint(equalTo: bottomAnchor),
            titleView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 12),
            titleView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -12)
        ])
    }
}
