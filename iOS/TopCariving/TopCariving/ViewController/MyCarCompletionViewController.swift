//
//  MyCarCompletionViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/16.
//

import UIKit

class MyCarCompletionViewController: BaseMyCarViewController {
    // MARK: - UI properties
    let ticketView = TicketBackgroundView()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
    }
    
    // MARK: - Helpers
    private func setUI() {
        view.backgroundColor = .white
        ticketView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(ticketView)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            ticketView.heightAnchor.constraint(equalToConstant: 204),
            ticketView.widthAnchor.constraint(equalToConstant: 343),
            ticketView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 13),
            ticketView.centerXAnchor.constraint(equalTo: view.centerXAnchor)
        ])
    }
}
