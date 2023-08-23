//
//  BaseSearchViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/22.
//

import UIKit

class BaseSearchViewController: UIViewController {
    // MARK: - UI properties
    
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setNavigationBar()
    }
    // MARK: - Helpers
    private func setNavigationBar() {
        navigationController?.navigationBar.topItem?.backButtonTitle = ""
        navigationController?.navigationBar.tintColor = .hyundaiBlackGray
    }
    func setNavigationRightItem() {
        let xmarkImage = UIImage(systemName: "xmark")?.resized(to: 20)
        navigationController?.navigationBar.topItem?.rightBarButtonItem = UIBarButtonItem(
            image: xmarkImage,
            style: .done,
            target: self,
            action: #selector(tapNavigationRightItem(_:))
        )
    }
    @objc private func tapNavigationRightItem(_ sender: Any) {
        self.dismiss(animated: true)
    }
    func setTitle(to title: String) {
        let titleLabel = UILabel()
        titleLabel.setFont(to: .init(name: .medium, size: ._16))
        titleLabel.text = title
        navigationItem.titleView = titleLabel
    }
}
