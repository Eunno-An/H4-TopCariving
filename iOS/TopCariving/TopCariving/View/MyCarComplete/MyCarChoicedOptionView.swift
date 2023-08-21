//
//  MyCarChoicedOptionView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/19.
//

import UIKit

struct MyCarChoicedOptionModel {
    var myCarChoicedOptionItems: [MyCarChoicedOptionItemModel]
}

class MyCarChoicedOptionView: UIView {
    // MARK: - UI properties
    private var title: UILabel = {
        let label = UILabel()
        label.text = "선택옵션 0개"
        label.font = .designSystem(.init(name: .medium, size: ._18))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private let separator: UILabel = {
        let label = UILabel()
        label.layer.borderColor = UIColor.lightGray.cgColor
        label.layer.borderWidth = 1
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    var stackView: UIStackView = {
        let stackView = UIStackView()
        stackView.axis = .vertical
        stackView.translatesAutoresizingMaskIntoConstraints = false
        return stackView
    }()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setTitle()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setTitle()
    }
    init(myCarChoicedOptionModel: MyCarChoicedOptionModel) {
        super.init(frame: .zero)
        setStackView(to: myCarChoicedOptionModel.myCarChoicedOptionItems)
        setTitle()
        setUI()
        setLayout()
    }
    
    // MARK: - Helpers
    private func setUI() {
        addSubview(title)
        addSubview(separator)
        addSubview(stackView)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            title.heightAnchor.constraint(equalToConstant: 30),
            title.widthAnchor.constraint(equalToConstant: 100),
            title.topAnchor.constraint(equalTo: topAnchor),
            title.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            separator.heightAnchor.constraint(equalToConstant: 1),
            separator.widthAnchor.constraint(equalToConstant: CGRect.screenBounds.width - 16*2),
            separator.topAnchor.constraint(equalTo: title.bottomAnchor, constant: 16),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            stackView.topAnchor.constraint(equalTo: separator.bottomAnchor),
            stackView.leadingAnchor.constraint(equalTo: leadingAnchor)
        ])
    }
    func setStackView(to data: [MyCarChoicedOptionItemModel]) {
        for idx in 0..<data.count {
            stackView.addArrangedSubview(MyCarChoicedOptionItemView(data: data[idx]))
        }
        setTitle()
    }
    private func setTitle() {
        title.text = "선택옵션 " + String(stackView.arrangedSubviews.count) + "개"
    }
}
