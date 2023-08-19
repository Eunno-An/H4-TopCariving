//
//  MyCarCompletionViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/16.
//

import UIKit

class MyCarCompletionViewController: BaseMyCarViewController {
    // MARK: - UI properties
    private let scrollView = UIScrollView()
    private let containerView = UIView()
    private let ticketView = TicketBackgroundView()
    private let separator: UILabel = {
        let label = UILabel()
        label.backgroundColor = .hyundaiLightSand
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private let myCarFeatureView = MyCarFeatureView()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
    }
    override func viewDidLayoutSubviews() {
        ticketView.drawTicket(holeYPosition: 70)
        ticketView.setUI()
        ticketView.setLayout()
    }
    
    // MARK: - Helpers
    private func setUI() {
        view.backgroundColor = .white
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        containerView.translatesAutoresizingMaskIntoConstraints = false
        
        ticketView.translatesAutoresizingMaskIntoConstraints = false
        myCarFeatureView.translatesAutoresizingMaskIntoConstraints = false
        
        view.addSubview(scrollView)
        scrollView.addSubview(containerView)
        
        [ticketView, separator, myCarFeatureView].forEach {
            containerView.addSubview($0)
        }
    }
    private func setLayout() {
        let contentViewHeight = containerView.heightAnchor.constraint(greaterThanOrEqualTo: view.heightAnchor)
        contentViewHeight.priority = .defaultLow
        contentViewHeight.isActive = true
        containerView.widthAnchor.constraint(equalTo: scrollView.widthAnchor).isActive = true
        
        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            scrollView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor),
            
            containerView.topAnchor.constraint(equalTo: scrollView.contentLayoutGuide.topAnchor),
            containerView.bottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor),
            containerView.leadingAnchor.constraint(equalTo: scrollView.contentLayoutGuide.leadingAnchor),
            containerView.trailingAnchor.constraint(equalTo: scrollView.contentLayoutGuide.trailingAnchor),
            
            ticketView.heightAnchor.constraint(equalToConstant: 204),
            ticketView.widthAnchor.constraint(equalToConstant: 343),
            ticketView.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 13),
            ticketView.centerXAnchor.constraint(equalTo: containerView.centerXAnchor),
            
            separator.topAnchor.constraint(equalTo: ticketView.bottomAnchor, constant: 65),
            separator.heightAnchor.constraint(equalToConstant: 4),
            separator.leadingAnchor.constraint(equalTo: containerView.leadingAnchor),
            separator.trailingAnchor.constraint(equalTo: containerView.trailingAnchor),
            
            myCarFeatureView.topAnchor.constraint(equalTo: separator.bottomAnchor, constant: 20),
            myCarFeatureView.heightAnchor.constraint(equalToConstant: 258),
            myCarFeatureView.leadingAnchor.constraint(equalTo: containerView.leadingAnchor),
            myCarFeatureView.trailingAnchor.constraint(equalTo: containerView.trailingAnchor)
        ])
    }
}
