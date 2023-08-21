//
//  DummyViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/01.
//

import Foundation
import UIKit
import Combine

class DummyViewController: UIViewController {
    // MARK: - UI properties
    private let myCarivingSummaryView = MyCarivingCarSummaryView()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
    }
    // MARK: - Helpers
    func setUI() {
        view.backgroundColor = .white
        myCarivingSummaryView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(myCarivingSummaryView)
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            myCarivingSummaryView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            myCarivingSummaryView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
            myCarivingSummaryView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
            myCarivingSummaryView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor)
        ])
    }
    private func bind(to viewModel: DummyViewModel) {
        let input = DummyViewModel.Input()
        let output = viewModel.transform(input: input)
    }
}

