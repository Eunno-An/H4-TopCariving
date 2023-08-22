//
//  TicketBackgroundView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/16.
//

import UIKit
struct TicketBackgroundModel {
    var imageName: String
}
class TicketBackgroundView: TicketView {
    // MARK: - UI properties
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
        imageView.contentMode = .scaleAspectFit
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
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
    init(ticketBackground: TicketBackgroundModel) {
        super.init(frame: .zero)
        setData(to: ticketBackground)
        setUI()
        setLayout()
    }
    override func layoutSubviews() {
        drawTicket(holeYPosition: 70)
        setUI()
        setLayout()
    }
    
    // MARK: - Helpers
    func setUI() {
        [title, leftDiscription, rightArrow, rightDescription, carImage].forEach { addSubview($0) }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            title.topAnchor.constraint(equalTo: topAnchor, constant: 23),
            title.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -153),
            title.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 45),
            title.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -45),
            
            leftDiscription.topAnchor.constraint(equalTo: title.bottomAnchor, constant: 4),
            leftDiscription.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -129),
            leftDiscription.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 47),
            leftDiscription.trailingAnchor.constraint(equalTo: rightArrow.leadingAnchor),
            
            rightArrow.topAnchor.constraint(equalTo: title.bottomAnchor, constant: 4),
            rightArrow.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -129),
            rightArrow.leadingAnchor.constraint(equalTo: leftDiscription.trailingAnchor),
            rightArrow.trailingAnchor.constraint(equalTo: rightDescription.leadingAnchor),
            
            rightDescription.topAnchor.constraint(equalTo: title.bottomAnchor, constant: 4),
            rightDescription.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -129),
            rightDescription.leadingAnchor.constraint(equalTo: rightArrow.trailingAnchor),
            rightDescription.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -30),
            
            carImage.topAnchor.constraint(equalTo: leftDiscription.topAnchor),
            carImage.bottomAnchor.constraint(equalTo: bottomAnchor, constant: 80),
            carImage.leadingAnchor.constraint(equalTo: leadingAnchor),
            carImage.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    func setCarImage(to name: String) {
        carImage.image = UIImage(named: "image 31")
        // 이미지서비스로 변경
    }
    func setData(to data: TicketBackgroundModel) {
        setCarImage(to: data.imageName)
    }
}
