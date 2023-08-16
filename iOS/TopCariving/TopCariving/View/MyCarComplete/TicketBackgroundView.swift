//
//  TicketBackgroundView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/16.
//

import UIKit

class TicketBackgroundView: UIView {
    // MARK: - UI properties
    // TODO: TicketView추가
    private var title: UILabel = {
        let label = UILabel()
        label.text = "나의 펠리세이드가 완성되었어요"
        label.font = .designSystem(.init(name: .medium, size: ._20))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private var leftDiscription: UILabel = {
        let label = UILabel()
        label.text = "완성된 차량은 마이카이빙"
        label.font = .designSystem(.init(name: .regular, size: ._10))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private var rightArrow: UIImageView = {
        let imageView = UIImageView()
        imageView.image = UIImage(named: "Keyboard_arrow_right")
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    private var rightDescription: UILabel = {
        let label = UILabel()
        label.text = "내가 만든 차량목록에서 볼 수 있어요"
        label.font = .designSystem(.init(name: .regular, size: ._10))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
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
