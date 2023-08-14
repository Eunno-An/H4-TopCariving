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
    private let testTableViewData = [
        ["1","2","3"],
        ["1","2","3","4","5"],
        ["1","2"],
        ["1","2","3","4"],
        ["1"],
    ]
    private let tableView = UITableView()
    
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()
    private var hiddenSections = Set<Int>()
    
    // MARK: - Lifecycles
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setCancelButtonAction()
        attribute()
    }
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        setUI()
        setLayout()
        setCancelButtonAction()
        attribute()
    }
    override func viewDidLoad() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(presentSubCategoryModal),
            name: Notification.Name("SubCategoryCellTapped"), object: nil)
    }
    
    // MARK: - Helpers
    private func setUI() {
        view.backgroundColor = .white
        tableView.translatesAutoresizingMaskIntoConstraints = false
        tableView.separatorStyle = .none
        [modalTitle, separator, cancelButton, tableView].forEach {
            view.addSubview($0)
        }
    }
    private func setLayout() {
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
            
            tableView.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 32),
            tableView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            tableView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            tableView.trailingAnchor.constraint(equalTo: view.trailingAnchor)
        ])
    }
    private func setCancelButtonAction() {
        cancelButton.touchUpPublisher.sink { [weak self] _ in
            self?.dismiss(animated: true)
        }.store(in: &bag)
    }
    @objc private func presentSubCategoryModal() {
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

extension IncludedBaseItemModalViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if hiddenSections.contains(section) {
            return 0
        }
        return testTableViewData[section].count
    }
    func numberOfSections(in tableView: UITableView) -> Int {
        return testTableViewData.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = BaseOptionSubCategoryCell()
        return cell
    }
    func attribute() {
        tableView.register(
            BaseOptionSubCategoryCell.self,
            forCellReuseIdentifier: BaseOptionSubCategoryCell.identifier)
        tableView.delegate = self
        tableView.dataSource = self
    }
}

extension IncludedBaseItemModalViewController: UITableViewDelegate {
    
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
