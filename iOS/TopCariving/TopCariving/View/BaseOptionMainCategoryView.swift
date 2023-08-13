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
    private var heightConstant: NSLayoutConstraint?
    
    // MARK: - Properties
    private var bag: Set<AnyCancellable> = .init()
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
    func setUI() {
        titleView.translatesAutoresizingMaskIntoConstraints = false
        subCategoryStackView.translatesAutoresizingMaskIntoConstraints = false
        addSubview(titleView)
        addSubview(subCategoryStackView)
    }
    func setLayout() {
        heightConstant = heightAnchor.constraint(equalToConstant: 46)
        heightConstant?.isActive = true
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
    func setAction() {
        titleView.tapPublisher().sink { [weak self] _ in
            self!.toggleFold()
        }.store(in: &bag)
        titleView.arrow.touchUpPublisher.sink { [weak self] _ in
            self!.toggleFold()
        }.store(in: &bag)
    }
    func setSubCategoryStackView() {
        subCategoryStackView.isHidden = true
    }
    func toggleFold() {
        isFolded.toggle()
        switch isFolded {
        case true:
            titleView.setArrowImage(to: "arrow_down")
            subCategoryStackView.isHidden = true
            heightConstant?.constant = 46
        case false:
            titleView.setArrowImage(to: "arrow_up")
            subCategoryStackView.isHidden = false
            heightConstant?.constant = CGFloat(46 + (71+8) * subCategoryStackView.arrangedSubviews.count + 12)
        }
    }
}
