//
//  ColorCell.swift
//  TopCariving
//
//  Created by cho seungki on 2023/08/15.
//

import UIKit

class ColorCell: UICollectionViewCell {
    // MARK: - UI Property
    private let colorImageView = {
        let imageView = UIImageView()
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.layer.cornerRadius = 8
        imageView.layer.borderColor = UIColor.hyundaiActiveBlue.cgColor
        imageView.layer.masksToBounds = true
        return imageView
    }()
    private let checkImageView = {
        let image = UIImage(systemName: "checkmark.circle.fill")?
            .applyingSymbolConfiguration(.init(paletteColors: [UIColor.white, UIColor.hyundaiActiveBlue]))
        let imageView = UIImageView(image: image)
        imageView.translatesAutoresizingMaskIntoConstraints = false
        imageView.layer.masksToBounds = true
        imageView.contentMode = .scaleAspectFit
        imageView.isHidden = true
        return imageView
    }()
    // MARK: - Properties
    static let identifier = "ColorCell"
    override var isSelected: Bool {
        willSet {
            newValue ? select() : deselect()
        }
    }
    
    // MARK: - LifeCycle
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
    override func prepareForReuse() {
        super.prepareForReuse()
        colorImageView.image = nil
    }
    
    // MARK: - Helper
    private func setUI() {
        [colorImageView, checkImageView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            colorImageView.topAnchor.constraint(equalTo: topAnchor),
            colorImageView.bottomAnchor.constraint(equalTo: bottomAnchor),
            colorImageView.leadingAnchor.constraint(equalTo: leadingAnchor),
            colorImageView.trailingAnchor.constraint(equalTo: trailingAnchor),
            
            checkImageView.topAnchor.constraint(equalTo: topAnchor, constant: -11),
            checkImageView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: 8),
            checkImageView.widthAnchor.constraint(equalToConstant: 22),
            checkImageView.heightAnchor.constraint(equalToConstant: 22)
        ])
    }
    private func select() {
        colorImageView.layer.borderWidth = 3
        checkImageView.isHidden = false
    }
    private func deselect() {
        colorImageView.layer.borderWidth = 0
        checkImageView.isHidden = true
    }
    func setup(with image: String) {
        colorImageView.backgroundColor = .red
        // 이미지 서비스로 변경
    }
}
