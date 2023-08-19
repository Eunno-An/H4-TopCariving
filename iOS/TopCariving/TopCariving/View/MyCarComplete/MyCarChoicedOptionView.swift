//
//  MyCarChoicedOptionView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/19.
//

import UIKit

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
    private let stackView: UIStackView = {
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
        test_injectMock()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        test_injectMock()
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
            separator.widthAnchor.constraint(equalToConstant: 342),
            separator.topAnchor.constraint(equalTo: topAnchor),
            separator.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            stackView.topAnchor.constraint(equalTo: separator.bottomAnchor),
            stackView.leadingAnchor.constraint(equalTo: leadingAnchor)
        ])
    }
    private func test_injectMock() {
        for _ in 0..<5 {
            stackView.addArrangedSubview(MyCarChoicedOptionView())
            stackView.addArrangedSubview(separator)
        }
    }
    func setTitle(to number: Int) {
        title.text = "선택옵션 " + String(number) + "개"
    }
}
