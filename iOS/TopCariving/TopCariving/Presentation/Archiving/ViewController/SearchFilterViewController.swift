//
//  SearchFilterViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/20.
//

import Combine
import UIKit

class SearchFilterViewController: BaseSearchViewController {
    // MARK: - UI properties
    private let carSettingLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._16))
        label.text = "차량 설정"
        label.textColor = .hyundaiBlackGray
        return label
    }()
    private let carSettingButon: ArchivingSettingButton = {
        let button = ArchivingSettingButton()
        button.setTitle(to: "펠리세이드",
                        font: .init(name: .medium, size: ._14),
                        color: .hyundaiBlackGray)
        return button
    }()
    private let separator: UIView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = UIColor(red: 242/255, green: 242/255, blue: 242/255, alpha: 1.0)
        return view
    }()
    private let optionSettingLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._16))
        label.text = "옵션 설정"
        label.textColor = .hyundaiBlackGray
        return label
    }()
    private let optionCountLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._12))
        label.text = "2 / 15"
        label.textColor = .hyundaiBlackGray
        label.textAlignment = .right
        return label
    }()
    private let optionSettingButon: ArchivingSettingButton = {
        let button = ArchivingSettingButton()
        button.setTitle(to: "옵션을 선택해 주세요.",
                        font: .init(name: .regular, size: ._14),
                        color: .hyundaiMediumGray)
        return button
    }()
    private let optionTagView = OptionTagView()
    
    // MARK: - Properties
    var bag = Set<AnyCancellable>()
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
        setEvent()
        testOptions()
    }
    private func setUI() {
        view.backgroundColor = .white
        setTitle(to: "차량 검색 조건 설정")
        setNavigationRightItem()
        [carSettingLabel, carSettingButon, separator, optionSettingLabel,
         optionCountLabel, optionSettingButon, optionTagView].forEach {
            view.addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            carSettingLabel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 32),
            carSettingLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            carSettingLabel.widthAnchor.constraint(equalToConstant: 60),
            carSettingLabel.heightAnchor.constraint(equalToConstant: 24),
            
            carSettingButon.topAnchor.constraint(equalTo: carSettingLabel.bottomAnchor, constant: 9),
            carSettingButon.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            carSettingButon.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            carSettingButon.heightAnchor.constraint(equalToConstant: 46),
            
            separator.topAnchor.constraint(equalTo: carSettingButon.bottomAnchor, constant: 24),
            separator.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            separator.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            separator.heightAnchor.constraint(equalToConstant: 1),
            
            optionSettingLabel.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 24),
            optionSettingLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            optionSettingLabel.widthAnchor.constraint(equalToConstant: 60),
            optionSettingLabel.heightAnchor.constraint(equalToConstant: 24),
            
            optionCountLabel.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 28),
            optionCountLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            optionCountLabel.heightAnchor.constraint(equalToConstant: 20),
            
            optionSettingButon.topAnchor.constraint(equalTo: optionSettingLabel.bottomAnchor, constant: 9),
            optionSettingButon.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            optionSettingButon.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            optionSettingButon.heightAnchor.constraint(equalToConstant: 46),
            
            optionTagView.topAnchor.constraint(equalTo: optionSettingButon.bottomAnchor, constant: 8),
            optionTagView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            optionTagView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16)
        ])
    }
    private func setEvent() {
        carSettingButon.tabPublisher.sink(receiveValue: { [weak self] _ in
            guard let self else { return }
            navigationController?.pushViewController(SearchCarSettingViewController(), animated: true)
        })
        .store(in: &bag)
        optionSettingButon.tabPublisher.sink(receiveValue: { [weak self] _ in
            guard let self else { return }
            navigationController?.pushViewController(SearchOptionSettingViewController(), animated: true)
        })
        .store(in: &bag)
    }
    var testOptionData = ["컴포트 || 패키지", "듀얼 와이드 선루프", "2열 통풍시트"]
    private func testOptions() {
        optionTagView.refresh(by: testOptionData)
        optionTagView.tapRemoveTagIndexPathSubject
            .sink(receiveValue: { [weak self] indexPath in
                guard let self else { return }
                self.testOptionData.remove(at: indexPath.row)
                optionTagView.refresh(by: testOptionData)
                optionCountLabel.text = "\(testOptionData.count) / 15"
            })
            .store(in: &bag)
    }
}
