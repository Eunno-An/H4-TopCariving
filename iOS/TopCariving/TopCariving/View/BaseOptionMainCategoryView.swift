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
    private var subCategoryStackView = BaseOptionSubCategoryStackView()
    
    // MARK: - Properties
    private var bag: Set<AnyCancellable> = .init()
    private var isFolded: Bool = true
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setArrowButtonAction()
        setSubCategoryStackView()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setArrowButtonAction()
        setSubCategoryStackView()
    }
    
    // MARK: - Helpers
    func setUI() {
        backgroundColor = .hyundaiLightSand
        layer.cornerRadius = 8
        [title, arrow, subCategoryStackView].forEach {
            addSubview($0)
        }
    }
    
    func setLayout() {
        NSLayoutConstraint.activate([
            title.heightAnchor.constraint(equalToConstant: 22),
            title.widthAnchor.constraint(equalToConstant: 89),
            title.topAnchor.constraint(equalTo: topAnchor, constant: 12),
            title.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 12),
            
            arrow.heightAnchor.constraint(equalToConstant: 24),
            arrow.widthAnchor.constraint(equalToConstant: 24),
            arrow.topAnchor.constraint(equalTo: topAnchor, constant: 11),
            arrow.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -11),
            
            subCategoryStackView.widthAnchor.constraint(equalTo: widthAnchor, multiplier: 0.991),
            subCategoryStackView.centerXAnchor.constraint(equalTo: centerXAnchor),
            subCategoryStackView.heightAnchor.constraint(
                equalToConstant: CGFloat(12 + 71 * subCategoryStackView.arrangedSubviews.count)),
            subCategoryStackView.topAnchor.constraint(equalTo: bottomAnchor, constant: 12)
        ])
    }
    
    func setArrowButtonAction() {
        arrow.touchUpPublisher.sink { [weak self] _ in
            self?.toggleFold()
        }.store(in: &bag)
    }
    
    func setSubCategoryStackView() {
        subCategoryStackView.isHidden = true
    }
    
    func toggleFold() {
        isFolded.toggle()
        switch isFolded {
        case true:
            arrow.setImage(UIImage(named: "arrow_down"), for: .normal)
            subCategoryStackView.isHidden = true
        case false:
            arrow.setImage(UIImage(named: "arrow_up"), for: .normal)
            subCategoryStackView.isHidden = false
        }
    }
    
    func setTitle(to text: String) {
        title.text = text
    }
}
