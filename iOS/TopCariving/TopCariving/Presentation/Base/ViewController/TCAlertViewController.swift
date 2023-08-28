//
//  TCAlertViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/17.
//

import Combine
import UIKit

class TCAlertViewController: UIViewController {
    // MARK: - UI properties
    private let dimBackgroundView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .hyundaiDim
        return view
    }()
    private let alertView = {
        let view = UIView()
        view.layer.cornerRadius = 8
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .white
        return view
    }()
    private let descriptionLabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.numberOfLines = 0
        label.textAlignment = .center
        return label
    }()
    private let cancelButton: UIButton = {
        let button = UIButton()
        button.translatesAutoresizingMaskIntoConstraints = false
        button.layer.cornerRadius = 8
        button.titleLabel?.font = .designSystem(.init(name: .bold, size: ._18))
        button.setTitle("취소", for: .normal)
        button.backgroundColor = .hyundaiLightGray
        return button
    }()
    private let acceptButton: UIButton = {
        let button = UIButton()
        button.translatesAutoresizingMaskIntoConstraints = false
        button.layer.cornerRadius = 8
        button.titleLabel?.font = .designSystem(.init(name: .bold, size: ._18))
        button.backgroundColor = .hyundaiPrimaryBlue
        return button
    }()
    
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()
    private var acceptHandler: (() -> Void)?
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
        setEvent()
    }
    // MARK: - Helpers
    private func setUI() {
        [dimBackgroundView, alertView, descriptionLabel, cancelButton, acceptButton].forEach {
            view.addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            dimBackgroundView.topAnchor.constraint(equalTo: view.topAnchor),
            dimBackgroundView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            dimBackgroundView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            dimBackgroundView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            
            alertView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            alertView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            alertView.heightAnchor.constraint(equalToConstant: 186),
            alertView.widthAnchor.constraint(equalTo: view.widthAnchor, constant: -32),
            
            descriptionLabel.centerYAnchor.constraint(equalTo: alertView.centerYAnchor, constant: -35),
            descriptionLabel.leadingAnchor.constraint(equalTo: alertView.leadingAnchor, constant: 16),
            descriptionLabel.trailingAnchor.constraint(equalTo: alertView.trailingAnchor, constant: -16),
            
            cancelButton.leadingAnchor.constraint(equalTo: alertView.leadingAnchor, constant: 12),
            cancelButton.trailingAnchor.constraint(equalTo: alertView.centerXAnchor, constant: -3),
            cancelButton.bottomAnchor.constraint(equalTo: alertView.bottomAnchor, constant: -13),
            cancelButton.heightAnchor.constraint(equalToConstant: 56),
            
            acceptButton.leadingAnchor.constraint(equalTo: alertView.centerXAnchor, constant: 3),
            acceptButton.trailingAnchor.constraint(equalTo: alertView.trailingAnchor, constant: -13),
            acceptButton.bottomAnchor.constraint(equalTo: alertView.bottomAnchor, constant: -13),
            acceptButton.heightAnchor.constraint(equalToConstant: 56)
        ])
    }
    private func setEvent() {
        Publishers.Merge(dimBackgroundView.tabPublisher.map { _ in () },
                         cancelButton.touchUpPublisher)
        .sink(receiveValue: { [weak self] in
            guard let self else { return }
            self.dismiss(animated: false)
        })
        .store(in: &bag)
        
        acceptButton.touchUpPublisher
            .sink(receiveValue: { [weak self] in
                guard let self else { return }
                self.acceptHandler?()
                self.dismiss(animated: false)
            })
            .store(in: &bag)
    }
    func setUp(
        with attributedString: NSAttributedString,
        acceptTitle: String,
        acceptHandler: @escaping () -> Void
    ) {
        self.acceptHandler = acceptHandler
        descriptionLabel.attributedText = attributedString
        acceptButton.setTitle(acceptTitle, for: .normal)
    }
}
