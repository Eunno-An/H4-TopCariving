//
//  MyCarivingCarSummaryView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/21.
//

import Combine
import UIKit

typealias Position = (CGFloat, CGFloat)

struct MyCarivingCarSummaryModel {
    var featureSummary: FeatureSummaryModel
    var carImage: String
    var points: [CarImagePoint]
    var positions: [Position]
}

class MyCarivingCarSummaryView: UIView {
    // MARK: - UI properties
    private let title: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "차량 정보 요약"
        label.font = .designSystem(.init(name: .medium, size: ._14))
        return label
    }()
    private let ticketView = TicketView()
    private var carImage: UIImageView = {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.contentMode = .scaleAspectFit
        return imageView
    }()
    private let featureContainerView = FeatureSummaryContainerView()
    
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
    }
    init(data: MyCarivingCarSummaryModel) {
        super.init(frame: .zero)
        setUI()
        setLayout()
        setMyCarivingView(to: data)
    }
    override func layoutSubviews() {
        super.layoutSubviews()
        ticketView.drawTicket(holeYPosition: 295)
    }
    
    // MARK: - Helpers
    private func setUI() {
        ticketView.translatesAutoresizingMaskIntoConstraints = false
        featureContainerView.translatesAutoresizingMaskIntoConstraints = false
        featureContainerView.hideEditButton()
        [title, ticketView, carImage, featureContainerView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            title.heightAnchor.constraint(equalToConstant: 20),
            title.widthAnchor.constraint(equalToConstant: 114),
            title.topAnchor.constraint(equalTo: topAnchor),
            title.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            ticketView.heightAnchor.constraint(equalToConstant: 328),
            ticketView.widthAnchor.constraint(equalToConstant: CGRect.screenBounds.width - 16 * 2),
            ticketView.topAnchor.constraint(equalTo: topAnchor, constant: 46),
            ticketView.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            carImage.heightAnchor.constraint(equalToConstant: 190),
            carImage.widthAnchor.constraint(equalToConstant: CGRect.screenBounds.width - 16 * 2),
            carImage.topAnchor.constraint(equalTo: topAnchor),
            carImage.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            featureContainerView.heightAnchor.constraint(equalToConstant: 184),
            featureContainerView.widthAnchor.constraint(equalToConstant: CGRect.screenBounds.width - 16*2),
            featureContainerView.topAnchor.constraint(equalTo: carImage.bottomAnchor, constant: -30),
            featureContainerView.leadingAnchor.constraint(equalTo: leadingAnchor)
        ])
    }
    func setFeatureContainerView(to data: FeatureSummaryModel) {
        featureContainerView.setFeatureSummaryView(to: data)
    }
    func setCarImagePointViews(points: [CarImagePoint], positions: [Position]) {
        for idx in 0..<points.count {
            let point = points[idx]
            let position = positions[idx]
            
            let posX = (position.0 * (CGRect.screenBounds.width - 16 * 2))
            let posY = (position.1 * 190)
            let carImagePointView = CarImagePointView(point: point)
            
            carImagePointView.tabPublisher.sink { _ in
                carImagePointView.clickToggle()
            }.store(in: &bag)
            carImagePointView.translatesAutoresizingMaskIntoConstraints = false
            
            addSubview(carImagePointView)
            
            NSLayoutConstraint.activate([
                carImagePointView.heightAnchor.constraint(equalToConstant: 22),
                carImagePointView.widthAnchor.constraint(equalToConstant: 22),
                carImagePointView.topAnchor.constraint(equalTo: carImage.topAnchor, constant: posY),
                carImagePointView.leadingAnchor.constraint(equalTo: carImage.leadingAnchor, constant: posX)
            ])
        }
    }
    func setMyCarivingView(to data: MyCarivingCarSummaryModel) {
        setFeatureContainerView(to: data.featureSummary)
        setCarImagePointViews(points: data.points, positions: data.positions)
        carImage.setAsyncImage(url: data.carImage)
    }
}
