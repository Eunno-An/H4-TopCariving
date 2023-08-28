//
//  BaseMyCarViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/09.
//

import Combine
import UIKit

class BaseMyCarViewController: UIViewController {
    // MARK: - UI properties
    let progressView = ProgressView(frame: .zero)
    let footerView = MyCarFooterView()
    // MARK: - Properties
    var bag = Set<AnyCancellable>()
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setNavigationBar()
        setUI()
        setLayout()
        setSummaryTap()
    }
    
    // MARK: - Helpers
    private func setNavigationBar() {
        let appearance = UINavigationBarAppearance()
        appearance.backgroundColor = .hyundaiSand
        
        navigationController?.navigationBar.standardAppearance = appearance
        navigationController?.navigationBar.scrollEdgeAppearance = appearance
        navigationController?.navigationBar.topItem?.backButtonTitle = ""
        navigationController?.navigationBar.tintColor = .hyundaiBlackGray
        let topCarivingImage = UIImage(named: "TopArchivingButton")?.resized(to: 40)
        navigationController?.navigationBar.topItem?.rightBarButtonItem = UIBarButtonItem(image: topCarivingImage,
                                                                                   style: .done,
                                                                                   target: nil,
                                                                                   action: nil)
    }
    func setUI() {
        view.backgroundColor = .white
        setTitle(to: "펠리세이드")
        [progressView, footerView].forEach {
            view.addSubview($0)
        }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            progressView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            progressView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            progressView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            progressView.heightAnchor.constraint(equalTo: progressView.widthAnchor, multiplier: 0.154),
            
            footerView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            footerView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            footerView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            footerView.heightAnchor.constraint(equalTo: footerView.widthAnchor, multiplier: 0.365)])
    }
    func setProgress(to progess: ProgressView.Progress) {
        progressView.setImage(to: progess)
    }
    func setTitle(to title: String) {
        let titleLabel = UILabel()
        titleLabel.setFont(to: .init(name: .medium, size: ._14))
        titleLabel.text = "펠리세이드"
        navigationItem.titleView = titleLabel
    }
    private func setSummaryTap() {
        footerView.summaryLabel.tabPublisher
            .sink(receiveValue: { [weak self] _ in
                guard let self else { return }
                let summary = SummaryViewController()
                summary.modalPresentationStyle = .pageSheet
                self.present(summary, animated: true)
            })
            .store(in: &bag)
    }
}
