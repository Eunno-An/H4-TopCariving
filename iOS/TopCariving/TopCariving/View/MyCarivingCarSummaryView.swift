//
//  MyCarivingCarSummaryView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/21.
//

import UIKit

struct CarImagePoint {
    var point: (CGFloat, CGFloat)
    var isBookmarked: Bool
    var imageUrl: String
}
struct CarImagePoints {
    var numberPositions: [CarImagePoint]
}
struct MyCarivingCarSummaryModel {
    var carImage: String
    var trim: String
    var optionName: String
    var price: String
    var numberPositions: CarImagePoints
    var colorInfo: [(String, String)]
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
        imageView.image = UIImage(named: "image_001")
        return imageView
    }()
    private let featureContainerView = FeatureSummaryContainerView()
    
    // MARK: - Properties
    
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
}
