//
//  SearchCarSettingViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import Combine
import UIKit

class SearchCarSettingViewController: BaseSearchViewController {
    // MARK: - UI properties
    private lazy var mockImageView = {
        let image = UIImage(named: "ArchivingCarSetting")?
            .resized(to: CGRect.screenBounds.width)
        let imageView = UIImageView(image: image)
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.backgroundColor = .white
        return imageView
    }()
    private let acceptButton: CTAButton = {
       let button = CTAButton()
        button.setTitle(to: "선택 항목 적용하기")
        return button
    }()
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
        setEvent()
    }
    // MARK: - Helpers
    private func setUI() {
        view.backgroundColor = .white
        setTitle(to: "차량 설정")
        [mockImageView, acceptButton].forEach {
            view.addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            mockImageView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            mockImageView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
            mockImageView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor),
            mockImageView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            
            acceptButton.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -20),
            acceptButton.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 16),
            acceptButton.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -16),
            acceptButton.heightAnchor.constraint(equalToConstant: 56)
        ])
    }
    private func setEvent() {
        acceptButton.touchUpPublisher.sink(receiveValue: { [weak self] _ in
            guard let self else { return }
            navigationController?.popViewController(animated: true)
        })
        .store(in: &bag)
    }
}
