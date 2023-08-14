//
//  IncludedBaseItemModalViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import UIKit

class IncludedBaseItemModalViewController: UIViewController {
    // MARK: - UI properties
    private let modalTitle: UILabel = {
        let label: UILabel = UILabel()
        label.text = "기본 포함 품목"
        label.font = .designSystem(.init(name: .medium, size: ._16))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private let separator: UILabel = {
        let label: UILabel = UILabel()
        label.backgroundColor = UIColor(red: 242/255, green: 242/255, blue: 242/255, alpha: 1)
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private let cancelButton: UIButton = {
        let button: UIButton = UIButton()
        button.setImage(UIImage(named: "cancelButton"), for: .normal)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    private let scrollView: UIScrollView = {
        let scrollView = UIScrollView()
        let stackView = BaseOptionMainCategoryStackView()
        stackView.translatesAutoresizingMaskIntoConstraints = false
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        scrollView.addSubview(stackView)
        NSLayoutConstraint.activate([
            stackView.topAnchor.constraint(equalTo: scrollView.topAnchor),
            stackView.trailingAnchor.constraint(equalTo: scrollView.trailingAnchor),
            stackView.bottomAnchor.constraint(equalTo: scrollView.bottomAnchor),
            stackView.leadingAnchor.constraint(equalTo: scrollView.leadingAnchor),
            stackView.widthAnchor.constraint(equalTo: scrollView.widthAnchor)
        ])
        return scrollView
    }()
    
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()
    
    // MARK: - Lifecycles
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setCancelButtonAction()
    }
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        setUI()
        setLayout()
        setCancelButtonAction()
    }
    override func viewDidLoad() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(presentSubCategoryModal),
            name: Notification.Name("SubCategoryCellTapped"), object: nil)
    }
    
    // MARK: - Helpers
    func setUI() {
        view.backgroundColor = .white
        [modalTitle, separator, cancelButton, scrollView].forEach {
            view.addSubview($0)
        }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            modalTitle.topAnchor.constraint(equalTo: view.topAnchor, constant: 21),
            modalTitle.widthAnchor.constraint(equalTo: view.widthAnchor, multiplier: 0.2346),
            modalTitle.heightAnchor.constraint(equalTo: view.heightAnchor, multiplier: 0.0295),
            modalTitle.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            
            separator.topAnchor.constraint(equalTo: modalTitle.bottomAnchor, constant: 15),
            separator.heightAnchor.constraint(equalToConstant: 1),
            separator.widthAnchor.constraint(equalTo: view.widthAnchor),
            separator.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            
            cancelButton.topAnchor.constraint(equalTo: view.topAnchor, constant: 25.41),
            cancelButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -25.41),
            cancelButton.heightAnchor.constraint(equalToConstant: 13.18),
            cancelButton.widthAnchor.constraint(equalToConstant: 13.18),
            
            scrollView.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 32),
            scrollView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            scrollView.centerXAnchor.constraint(equalTo: view.centerXAnchor)
        ])
    }
    func setCancelButtonAction() {
        cancelButton.touchUpPublisher.sink { [weak self] _ in
            self?.dismiss(animated: true)
        }.store(in: &bag)
    }
    @objc func presentSubCategoryModal() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            let modal = SubCategoryModalViewController()
            modal.modalPresentationStyle = .custom
            modal.modalTransitionStyle = .crossDissolve
            modal.transitioningDelegate = self
            present(modal, animated: true)
        }
    }
}

extension IncludedBaseItemModalViewController: UIViewControllerTransitioningDelegate {
    func presentationController(
        forPresented presented: UIViewController,
        presenting: UIViewController?,
        source: UIViewController
    ) -> UIPresentationController? {
           return ModalPresentationController(presentedViewController: presented, presenting: presenting)
       }
}
