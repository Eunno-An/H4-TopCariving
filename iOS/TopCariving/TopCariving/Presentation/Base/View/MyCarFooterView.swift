//
//  MyCarFooterView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/08.
//

import Combine
import UIKit

class MyCarFooterView: UIView {
    // MARK: - UI properties
    let summaryLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._12))
        label.text = "견적요약보기"
        label.isUserInteractionEnabled = true
        return label
    }()
    
    private let summaryBottomLine: UIView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .hyundaiBlackGray
        return view
    }()
    
    private let priceLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .bold, size: ._18))
        label.text = "47,720,000"
        label.textAlignment = .right
        return label
    }()
    
    private let wonLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._12))
        label.text = "원"
        return label
    }()
    
    private let nextButton: CTAButton = {
        let button = CTAButton()
        button.setTitle(to: "다음 단계로")
        return button
    }()
    
    // MARK: - Properties
    var bag = Set<AnyCancellable>()
    var tapSummaryButton = PassthroughSubject<Void, Never>()
    var tapNextButton = PassthroughSubject<Void, Never>()

    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setEvent()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setEvent()
    }
    init(price: String) {
        super.init(frame: .zero)
        setUI()
        setLayout()
        setPrice(to: price)
    }
    
    // MARK: - Helpers
    func setUI() {
        backgroundColor = .hyundaiSand
        translatesAutoresizingMaskIntoConstraints = false
        
        [summaryLabel, summaryBottomLine, priceLabel, wonLabel, nextButton].forEach {
            addSubview($0)
        }
    }
    
    func setLayout() {
        NSLayoutConstraint.activate([
            summaryLabel.topAnchor.constraint(equalTo: topAnchor, constant: 14),
            summaryLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 16),
            summaryLabel.widthAnchor.constraint(equalToConstant: 65),
            summaryLabel.heightAnchor.constraint(equalToConstant: 20),
            
            summaryBottomLine.topAnchor.constraint(equalTo: summaryLabel.bottomAnchor),
            summaryBottomLine.widthAnchor.constraint(equalTo: summaryLabel.widthAnchor),
            summaryBottomLine.centerXAnchor.constraint(equalTo: summaryLabel.centerXAnchor),
            summaryBottomLine.heightAnchor.constraint(equalToConstant: 1.5),
            
            priceLabel.topAnchor.constraint(equalTo: topAnchor, constant: 12),
            priceLabel.trailingAnchor.constraint(equalTo: wonLabel.leadingAnchor, constant: -2),
            priceLabel.heightAnchor.constraint(equalToConstant: 24),
            
            wonLabel.bottomAnchor.constraint(equalTo: priceLabel.bottomAnchor),
            wonLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -16),
            wonLabel.heightAnchor.constraint(equalTo: priceLabel.heightAnchor),
            
            nextButton.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -32),
            nextButton.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 16),
            nextButton.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -16),
            nextButton.heightAnchor.constraint(equalTo: nextButton.widthAnchor, multiplier: 0.163)
        ])
    }
    func setEvent() {
        summaryLabel.tabPublisher
            .sink(receiveValue: { [weak self] _ in
                guard let self else { return }
                self.tapSummaryButton.send(())
            })
            .store(in: &bag)
        
        nextButton.touchUpPublisher
            .throttle(for: 1.0, scheduler: DispatchQueue.main, latest: false)
            .sink(receiveValue: { [weak self] in
                guard let self else { return }
                self.tapNextButton.send(())
            })
            .store(in: &bag)
    }
    func setPrice(to price: String) {
        priceLabel.text = price
    }
}
