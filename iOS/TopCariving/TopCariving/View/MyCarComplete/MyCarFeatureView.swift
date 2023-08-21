//
//  MyCarFeatureView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/16.
//

import UIKit

class MyCarFeatureView: UIView {
    // MARK: - UI properties
    private var title: UILabel = {
        let label = UILabel()
        label.text = "나의 펠리세이드는 이런 기능을 가지고 있어요"
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = .designSystem(.init(name: .medium, size: ._18))
        return label
    }()
    private var summaryContainerView = FeatureSummaryContainerView()

    // MARK: - Properties
    
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
    init(data: FeatureSummaryModel) {
        super.init(frame: .zero)
        setUI()
        setLayout()
        setSummaryContainerView(to: data)
    }
    
    // MARK: - Helpers
    func setUI() {
        summaryContainerView.translatesAutoresizingMaskIntoConstraints = false
        [title, summaryContainerView].forEach {
            addSubview($0)
        }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            title.topAnchor.constraint(equalTo: topAnchor),
            title.heightAnchor.constraint(equalToConstant: 24),
            title.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 16),
            title.trailingAnchor.constraint(equalTo: trailingAnchor),
            
            summaryContainerView.topAnchor.constraint(equalTo: title.bottomAnchor, constant: 10),
            summaryContainerView.bottomAnchor.constraint(equalTo: bottomAnchor),
            summaryContainerView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 15),
            summaryContainerView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -15)
        ])
    }
    func setSummaryContainerView(to data: FeatureSummaryModel) {
        summaryContainerView = FeatureSummaryContainerView(data: data)
    }
}
