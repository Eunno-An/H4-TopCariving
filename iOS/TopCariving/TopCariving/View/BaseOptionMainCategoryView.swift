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
    
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()
    var arrowTouchUpPublilsher: AnyPublisher<Void, Never>?
    private var isClicked = false
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setArrowButtonPublisher()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setArrowButtonPublisher()
    }
    convenience init(data: BaseOptionMainCategoryModel) {
        self.init(frame: .zero)
        setUI()
        setLayout()
        setArrowButtonPublisher()
        setTitle(to: data.title)
        setArrowImage(to: "arrow_up")
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
        heightAnchor.constraint(equalToConstant: 46).isActive = true
        NSLayoutConstraint.activate([
            title.centerYAnchor.constraint(equalTo: centerYAnchor),
            title.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 12),
            title.trailingAnchor.constraint(equalTo: arrow.leadingAnchor),
            title.heightAnchor.constraint(equalToConstant: 22),
            
            arrow.centerYAnchor.constraint(equalTo: centerYAnchor),
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
    func setArrowButtonPublisher() {
        arrowTouchUpPublilsher = arrow.touchUpPublisher
    }
    func toggleArrow() {
        isClicked ?
        arrow.setImage(UIImage(named: "arrow_up"), for: .normal):
        arrow.setImage(UIImage(named: "arrow_down"), for: .normal)
        isClicked.toggle()
    }
}
