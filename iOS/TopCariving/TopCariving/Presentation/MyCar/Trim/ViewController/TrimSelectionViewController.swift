//
//  TrimSelectionViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/20.
//

import UIKit

class TrimSelectionViewController: BaseMyCarViewController {
    // MARK: - UI properties
    private let scrollView: UIScrollView = UIScrollView()
    let trimImageView = UIImageView()
    let trimReviewedView = TrimReviewedView()
    let foldableStackView = FoldableStackView()
    
    // MARK: - Helpers
    override func setUI() {
        super.setUI()
        setProgress(to: .engine)
        view.backgroundColor = .white
        view.addSubview(scrollView)
        [trimImageView, trimReviewedView, foldableStackView].forEach {
            scrollView.addSubview($0)
        }
    }
    override func setLayout() {
        super.setLayout()
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        trimImageView.translatesAutoresizingMaskIntoConstraints = false

        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: progressView.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: footerView.topAnchor),
            
            trimImageView.topAnchor.constraint(equalTo: scrollView.contentLayoutGuide.topAnchor),
            trimImageView.leadingAnchor.constraint(equalTo: scrollView.leadingAnchor),
            trimImageView.trailingAnchor.constraint(equalTo: scrollView.trailingAnchor),
            trimImageView.widthAnchor.constraint(equalTo: scrollView.widthAnchor),
            trimImageView.heightAnchor.constraint(equalTo: view.widthAnchor, multiplier: 0.63),

            trimReviewedView.topAnchor.constraint(equalTo: trimImageView.bottomAnchor, constant: 10),
            trimReviewedView.leadingAnchor.constraint(equalTo: scrollView.leadingAnchor, constant: 16),
            trimReviewedView.trailingAnchor.constraint(equalTo: scrollView.trailingAnchor, constant: -16),
            trimReviewedView.heightAnchor.constraint(equalToConstant: 36),

            foldableStackView.topAnchor.constraint(equalTo: trimReviewedView.bottomAnchor, constant: 22),
            foldableStackView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            foldableStackView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            foldableStackView.bottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor, constant: -8)
        ])
    }
}
