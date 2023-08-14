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
    private var subCategoryStackView = BaseOptionSubCategoryStackView()
    private var heightConstraint: NSLayoutConstraint?
    
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()
    private var isFolded: Bool = true
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setAction()
        setSubCategoryStackView()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setAction()
        setSubCategoryStackView()
    }
    
    // MARK: - Helpers
    private func setUI() {
        titleView.translatesAutoresizingMaskIntoConstraints = false
        subCategoryStackView.translatesAutoresizingMaskIntoConstraints = false
        addSubview(titleView)
        addSubview(subCategoryStackView)
    }
    private func setLayout() {
        heightConstraint = heightAnchor.constraint(equalToConstant: 46)
        heightConstraint?.isActive = true
        NSLayoutConstraint.activate([
            titleView.topAnchor.constraint(equalTo: topAnchor),
            titleView.heightAnchor.constraint(equalToConstant: 46),
            titleView.widthAnchor.constraint(equalToConstant: 343),
            titleView.centerXAnchor.constraint(equalTo: centerXAnchor),
            
            subCategoryStackView.topAnchor.constraint(equalTo: topAnchor, constant: 58),
            subCategoryStackView.widthAnchor.constraint(equalToConstant: 340),
            subCategoryStackView.heightAnchor.constraint(
                equalToConstant: CGFloat((71 + 8) * subCategoryStackView.arrangedSubviews.count)),
            subCategoryStackView.centerXAnchor.constraint(equalTo: centerXAnchor)
        ])
    }
    private func setAction() {
        titleView.tapPublisher().sink { [weak self] _ in
            self?.toggleFold()
        }.store(in: &bag)
        titleView.arrowTouchUpPublilsher?.sink { [weak self] _ in
            self?.toggleFold()
        }.store(in: &bag)
    }
    private func setSubCategoryStackView() {
        subCategoryStackView.isHidden = true
    }
    private func toggleFold() {
        isFolded.toggle()
        switch isFolded {
        case true:
            titleView.setArrowImage(to: "arrow_down")
            subCategoryStackView.isHidden = true
            heightConstraint?.constant = 46
        case false:
            titleView.setArrowImage(to: "arrow_up")
            subCategoryStackView.isHidden = false
            heightConstraint?.constant = CGFloat(46 + (71 + 8) * subCategoryStackView.arrangedSubviews.count + 12)
        }
    }
}
