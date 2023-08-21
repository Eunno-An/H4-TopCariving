//
//  MyCarCompletionViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/16.
//

import UIKit

struct MyCarCompletionModel {
    var featureSummaryModel: FeatureSummaryModel
    var myCarChoicedOptionModel: MyCarChoicedOptionModel
}

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
    private var myCarFeatureView = MyCarFeatureView()
    private var myCarChoicedOptionView = MyCarChoicedOptionView()
    private var anotherProcedureView = AnotherProcedureView()
    private let consultingView = ConsultingView()
    private let footerView = MyCarFooterView()
    
    // MARK: - Properties
    
    // MARK: - Lifecycles
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
    }
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        setUI()
        setLayout()
    }
    init(myCarCompletionModel: MyCarCompletionModel) {
        super.init(nibName: nil, bundle: nil)
        setMyCarFeatureView(to: myCarCompletionModel)
        setUI()
        setLayout()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
    }
    
    // MARK: - Helpers
    private func setUI() {
        view.backgroundColor = .white
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        containerView.translatesAutoresizingMaskIntoConstraints = false
        
        ticketView.translatesAutoresizingMaskIntoConstraints = false
        myCarFeatureView.translatesAutoresizingMaskIntoConstraints = false
        
        myCarChoicedOptionView.translatesAutoresizingMaskIntoConstraints = false
        anotherProcedureView.translatesAutoresizingMaskIntoConstraints = false
        consultingView.translatesAutoresizingMaskIntoConstraints = false
        footerView.translatesAutoresizingMaskIntoConstraints = false
        
        [scrollView, footerView].forEach {
            view.addSubview($0)
        }
        scrollView.addSubview(containerView)
        [
            ticketView,
            separator,
            myCarFeatureView,
            myCarChoicedOptionView,
            anotherProcedureView,
            consultingView
        ].forEach {
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
            containerView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            containerView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            
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
            myCarFeatureView.trailingAnchor.constraint(equalTo: containerView.trailingAnchor),
            
            myCarChoicedOptionView.topAnchor.constraint(equalTo: myCarFeatureView.bottomAnchor, constant: 28),
            myCarChoicedOptionView.stackView.bottomAnchor.constraint(equalTo: consultingView.topAnchor),
            myCarChoicedOptionView.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 18),
            myCarChoicedOptionView.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -15),
           
            consultingView.topAnchor.constraint(equalTo: myCarChoicedOptionView.bottomAnchor, constant: 20),
            consultingView.heightAnchor.constraint(equalToConstant: 86),
            consultingView.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16),
            consultingView.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16),
            
            anotherProcedureView.topAnchor.constraint(equalTo: consultingView.bottomAnchor, constant: 36),
            anotherProcedureView.bottomAnchor.constraint(equalTo: containerView.bottomAnchor, constant: -300),
            anotherProcedureView.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16),
            anotherProcedureView.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16),

            footerView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            footerView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            footerView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            footerView.heightAnchor.constraint(equalTo: view.widthAnchor, multiplier: 0.365)
        ])
    }
    func setMyCarFeatureView(to data: MyCarCompletionModel) {
        myCarFeatureView.setSummaryContainerView(to: data.featureSummaryModel)
        myCarChoicedOptionView.setStackView(to: data.myCarChoicedOptionModel.myCarChoicedOptionItems)

    }
}
