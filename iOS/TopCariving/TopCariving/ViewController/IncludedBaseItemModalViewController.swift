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
        BaseOptionMainCategoryModel(
            title: "파워트레인/성능",
            subCategories: [
                BaseOptionSubCategoryModel(
                    imageURL: "https://topcariving.s3.ap-northeast-2.amazonaws.com/power/transmission.jpg",
                    title: "8단 자동변속기"
                ),
                BaseOptionSubCategoryModel(
                    imageURL: "https://topcariving.s3.ap-northeast-2.amazonaws.com/power/ISG.jpg",
                    title: "ISG 시스템"
                ),
                BaseOptionSubCategoryModel(
                    imageURL: "https://topcariving.s3.ap-northeast-2.amazonaws.com/power/driving_mode.jpg",
                    title: "통합주행모드"
                ),
                BaseOptionSubCategoryModel(
                    imageURL: "https://topcariving.s3.ap-northeast-2.amazonaws.com/power/power_steering.jpg",
                    title: "랙구동형 전동식 파워스티어링(R-MDPS)"),
                BaseOptionSubCategoryModel(
                    imageURL: "https://topcariving.s3.ap-northeast-2.amazonaws.com/power/shift_button.jpg",
                    title: "전자식 변속버튼"),
            ]
        ),
        BaseOptionMainCategoryModel(
            title: "지능형 안전기술",
            subCategories: [
                BaseOptionSubCategoryModel(
                    imageURL: "https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/hba.jpeg",
                    title: "하이빔 보조"),
                BaseOptionSubCategoryModel(
                    imageURL: "https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/vibsteeringwheel.jpeg",
                    title: "진동 경고 스티어링 휠"),
                BaseOptionSubCategoryModel(
                    imageURL: "https://topcariving.s3.ap-northeast-2.amazonaws.com/ai/scc.jpeg",
                    title: "스마트 크루즈 컨트롤(스탑앤고 기능 포함)")
            ]
        )
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
    }
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        setUI()
        setLayout()
        setCancelButtonAction()
    }
    
    // MARK: - Helpers
    private func setUI() {
        view.backgroundColor = .white
        setTableView()
        [modalTitle, separator, cancelButton, tableView].forEach {
            view.addSubview($0)
        }
    }
    private func setTableView() {
        tableView.translatesAutoresizingMaskIntoConstraints = false
        tableView.separatorStyle = .none
        tableView.sectionHeaderTopPadding = 12
        testTableViewData.enumerated().map { (index, _) in
            hiddenSections.insert(index)
        }
        tableView.register(
            BaseOptionSubCategoryCell.self,
            forCellReuseIdentifier: BaseOptionSubCategoryCell.identifier)
        tableView.register(
            BaseOptionMainCategoryView.self,
            forHeaderFooterViewReuseIdentifier: BaseOptionMainCategoryView.identifier
        )
        tableView.delegate = self
        tableView.dataSource = self
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
            
            tableView.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 20),
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
    @objc private func presentSubCategoryModal(with indexPath: IndexPath) {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            let modal = SubCategoryModalViewController(
                data: testTableViewData[indexPath.section].subCategories[indexPath.row]
            )
            modal.modalPresentationStyle = .custom
            modal.modalTransitionStyle = .crossDissolve
            modal.transitioningDelegate = self
            present(modal, animated: true)
        }
    }
    private func hideSection(sender: UIView) {
        let section = sender.tag
        let isContainSection = hiddenSections.contains(section)
        switch isContainSection {
        case true:
            hiddenSections.remove(section)
            tableView.insertRows(at: indexPathsForSection(with: section), with: .fade)
        case false:
            hiddenSections.insert(section)
            tableView.deleteRows(at: indexPathsForSection(with: section), with: .fade)
        }
    }
    private func indexPathsForSection(with tag: Int) -> [IndexPath] {
        var indexPaths = [IndexPath]()
        for row in 0..<self.testTableViewData[tag].subCategories.count {
            indexPaths.append(IndexPath(row: row,
                                        section: tag))
        }
        return indexPaths
    }
}

extension IncludedBaseItemModalViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if hiddenSections.contains(section) {
            return 0
        }
        return testTableViewData[section].subCategories.count
    }
    func numberOfSections(in tableView: UITableView) -> Int {
        return testTableViewData.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(
            withIdentifier: "BaseOptionSubCategoryCell",
            for: indexPath
        ) as? BaseOptionSubCategoryCell else {
            return UITableViewCell()
        }
        cell.setData(to: testTableViewData[indexPath.section].subCategories[indexPath.row])
        return cell
    }
}

extension IncludedBaseItemModalViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        presentSubCategoryModal(with: indexPath)
    }
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 58
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 79
    }
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        guard let sectionHeader = tableView.dequeueReusableHeaderFooterView(
            withIdentifier: "BaseOptionMainCategoryView"
        ) as? BaseOptionMainCategoryView else {
            return BaseOptionMainCategoryView(data: testTableViewData[section])
        }
        sectionHeader.setData(to: testTableViewData[section])
        sectionHeader.backgroundView = UIView(frame: self.view.bounds)
        guard let headerView = sectionHeader.backgroundView else {
            return UIView()
        }
        headerView.backgroundColor = .hyundaiLightSand
        headerView.layer.cornerRadius = 8
        
        sectionHeader.tag = section
    
        let mergedPublishers = Publishers.Merge(
            sectionHeader.tabPublisher.map { _ in () },
            sectionHeader.arrowTouchUpPublilsher
        )
        
        mergedPublishers.sink { [weak self, weak sectionHeader] _ in
            guard let self = self, let sectionHeader = sectionHeader else {
                print("NONO")
                return
            }
            hideSection(sender: sectionHeader)
            let exist = self.hiddenSections.contains(section)
            sectionHeader.isFolded = exist
        }.store(in: &sectionHeader.bag)
        return sectionHeader
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
