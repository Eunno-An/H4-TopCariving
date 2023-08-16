//
//  BaseOptionMainCategoryView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/12.
//

import Combine
import UIKit

class BaseOptionMainCategoryView: UITableViewHeaderFooterView {
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
    var bag = Set<AnyCancellable>()
    var arrowTouchUpPublilsher: AnyPublisher<Void, Never>!
    static let identifier = "BaseOptionMainCategoryView"
    var isFolded: Bool = false {
        willSet {
            newValue ? setArrowImage(to: "arrow_down") : setArrowImage(to: "arrow_up")
        }
    }
    
    // MARK: - Lifecycles
    override init(reuseIdentifier: String?) {
        super.init(reuseIdentifier: reuseIdentifier)
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
    }
    override func layoutSubviews() {
        super.layoutSubviews()
        contentView.layer.cornerRadius = 8
        contentView.frame = contentView.frame.inset(by: UIEdgeInsets(top: 6, left: 16, bottom: 6, right: 16))
    }
    override func prepareForReuse() {
        super.prepareForReuse()
        bag = Set<AnyCancellable>()
    }
    
    // MARK: - Helpers
    private func setUI() {
        layer.cornerRadius = 8
        [title, arrow].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
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
    func setData(to data: BaseOptionMainCategoryModel) {
        setTitle(to: data.title)
    }
}
