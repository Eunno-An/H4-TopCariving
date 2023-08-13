//
//  SubCategoryModalViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/13.
//

import UIKit

class SubCategoryModalViewController: UIViewController {
    // MARK: - UI properties
    private var titleLabel: UILabel = {
        let label = UILabel()
        label.text = "ISG 시스템"
        label.font = .designSystem(.init(name: .medium, size: ._18))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    private var cancelButton: UIButton = {
        let button = UIButton()
        button.setImage(UIImage(named: "cancelButton"), for: .normal)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()
    private var titleImage: UIImageView = {
        var image = UIImage()
        var imageView = UIImageView(image: image)
        imageView.translatesAutoresizingMaskIntoConstraints = false
        return imageView
    }()
    private var detail: UILabel = {
        let label = UILabel()
        label.text = "신호 대기 상황이거나 정차 중일 때 차의 엔진을 일시 정지하여 연비를 향상시키고, 배출가스 발생을 억제하는 시스템입니다."
        label.font = .designSystem(.init(name: .regular, size: ._12))
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
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
    init(title: String, imageURL: String, description: String) {
        super.init(nibName: nil, bundle: nil)
        setUI()
        setLayout()
        setTitle(to: title)
        setImage(to: imageURL)
        setDescription(to: description)
    }
    // MARK: - Helpers
    func setUI() {
        [titleLabel, titleImage, detail, cancelButton].forEach {
            view.addSubview($0)
        }
    }
    func setLayout() {
        NSLayoutConstraint.activate([
            titleLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            titleLabel.heightAnchor.constraint(equalToConstant: 24),
            titleLabel.topAnchor.constraint(equalTo: view.topAnchor, constant: 24),
            
            cancelButton.widthAnchor.constraint(equalToConstant: 37),
            cancelButton.heightAnchor.constraint(equalToConstant: 37),
            cancelButton.topAnchor.constraint(equalTo: view.topAnchor, constant: 16),
            cancelButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: 16),
            
            titleImage.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            titleImage.widthAnchor.constraint(equalToConstant: 287),
            titleImage.heightAnchor.constraint(equalToConstant: 161),
            titleImage.topAnchor.constraint(equalTo: titleLabel.bottomAnchor),
            
            detail.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            detail.topAnchor.constraint(equalTo: titleImage.topAnchor, constant: 20),
            detail.widthAnchor.constraint(equalToConstant: 287),
            detail.heightAnchor.constraint(equalToConstant: 40)
        ])
    }
    func setTitle(to text: String) {
        titleLabel.text = text
    }
    func setImage(to url: String) {
        #warning("url로 변경 필요")
        let image = UIImage(named: url)
        titleImage.image = image
    }
    func setDescription(to text: String) {
        detail.text = text
    }
}