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
        label.font = .designSystem(.init(name: .bold, size: ._14))
        return label
    }()
    private let ticketView = TicketView()
    private var carImage: UIImageView = {
        let imageView = UIImageView()
        imageView.image = UIImage(named: "image 31")
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    
    // MARK: - Helpers
}
