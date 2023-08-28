//
//  BaseArchivingViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/22.
//

import UIKit

class BaseArchivingViewController: UIViewController {
    // MARK: - UI properties
    
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setNavigationBar()
        setTitle(to: "아카이빙")
    }
    // MARK: - Helpers
    private func setNavigationBar() {
        let appearance = UINavigationBarAppearance()
        appearance.backgroundColor = .hyundaiSand
        
        navigationController?.navigationBar.standardAppearance = appearance
        navigationController?.navigationBar.scrollEdgeAppearance = appearance
        navigationController?.navigationBar.topItem?.backButtonTitle = ""
        navigationController?.navigationBar.tintColor = .hyundaiBlackGray
        let topCarivingImage = UIImage(named: "MyCariving")?.resized(to: 78)
        navigationController?.navigationBar.topItem?.rightBarButtonItem = UIBarButtonItem(image: topCarivingImage,
                                                                                   style: .done,
                                                                                   target: nil,
                                                                                   action: nil)
    }
    func setTitle(to title: String) {
        let titleLabel = UILabel()
        titleLabel.setFont(to: .init(name: .medium, size: ._14))
        titleLabel.text = title
        titleLabel.frame = .init(x: 38, y: 0, width: 50, height: 21)
        let imageView = UIImageView(image: UIImage(named: "HyundaiTopLogo")?.resized(to: 36))
        imageView.frame = .init(x: 0, y: 0, width: 36, height: 22)
        imageView.contentMode = .scaleAspectFit
        
        let titleView = UIView(frame: .init(origin: .zero, size: .init(width: 83, height: 22)))
        titleView.addSubview(titleLabel)
        titleView.addSubview(imageView)
        navigationItem.titleView = titleView
    }
}
