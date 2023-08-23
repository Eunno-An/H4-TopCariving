//
//  ArchivingReviewCell.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import UIKit

struct ArchivingReviewCellModel: Hashable {
    let carName: String
    let searchType: String
    let date: Date
    let trim: String
    let outColorName: String
    let inColorName: String
    let selectOptions: [String]
    let tags: [String]
}

class ArchivingReviewCell: UICollectionViewCell {
    // MARK: - UI properties
    private let titleLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .bold, size: ._18))
        label.text = "펠리세이드 Le Blanc"
        label.textColor = .hyundaiBlackGray
        return label
    }()
    private let dateLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._12))
        label.textColor = .hyundaiGold
        label.backgroundColor = .hyundaiLightSand
        label.clipsToBounds = true
        label.layer.cornerRadius = 16
        label.textAlignment = .center
        return label
    }()
    private let trimLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textColor = .hyundaiBlackGray
        return label
    }()
    private let outColorLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._14))
        label.textColor = .hyundaiBlackGray
        label.text = "외장"
        return label
    }()
    private let outColorNameLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textColor = .init(red: 139/255, green: 139/255, blue: 139/255, alpha: 1.0)
        return label
    }()
    private let inColorLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._14))
        label.textColor = .hyundaiBlackGray
        label.text = "내장"
        return label
    }()
    private let inColorNameLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textColor = .init(red: 139/255, green: 139/255, blue: 139/255, alpha: 1.0)
        return label
    }()
    private let selectOptionLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._14))
        label.textColor = .hyundaiBlackGray
        label.text = "선택옵션"
        return label
    }()
    private let selectOptionScrollView: UIScrollView = {
        let scrollView = UIScrollView()
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        return scrollView
    }()
    private let selectOptionStackView: UIStackView = {
        let stackView = UIStackView()
        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.axis = .horizontal
        stackView.spacing = 6
        stackView.alignment = .fill
        return stackView
    }()
    private let tagsView = TagsView()
    // MARK: - Properties
    static let identifier = "ArhcivingReviewCell"
    
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
    override func prepareForReuse() {
        super.prepareForReuse()
        setUp(with: .init(carName: "",
                          searchType: "",
                          date: Date(),
                          trim: "",
                          outColorName: "",
                          inColorName: "",
                          selectOptions: [],
                          tags: []))
    }
    // MARK: - Helpers
    private func setUI() {
        backgroundColor = .white
        layer.cornerRadius = 8
        layer.borderColor = UIColor.hyundaiSand.cgColor
        layer.borderWidth = 1
        [titleLabel, dateLabel, trimLabel, outColorLabel, outColorNameLabel, inColorLabel,
         inColorNameLabel, selectOptionLabel, selectOptionScrollView, tagsView].forEach {
            addSubview($0)
        }
        selectOptionScrollView.addSubview(selectOptionStackView)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: topAnchor, constant: 21),
            titleLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 17),
            titleLabel.widthAnchor.constraint(equalToConstant: 180),
            titleLabel.heightAnchor.constraint(equalToConstant: 20),
            
            dateLabel.topAnchor.constraint(equalTo: topAnchor, constant: 24),
            dateLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -17),
            dateLabel.widthAnchor.constraint(equalToConstant: 148),
            dateLabel.heightAnchor.constraint(equalToConstant: 24),
            
            trimLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 6),
            trimLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 17),
            trimLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -17),
            trimLabel.heightAnchor.constraint(equalToConstant: 24),
            
            outColorLabel.topAnchor.constraint(equalTo: trimLabel.bottomAnchor, constant: 13),
            outColorLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 17),
            outColorLabel.widthAnchor.constraint(equalToConstant: 25),
            outColorLabel.heightAnchor.constraint(equalToConstant: 22),
            
            outColorNameLabel.topAnchor.constraint(equalTo: trimLabel.bottomAnchor, constant: 13),
            outColorNameLabel.leadingAnchor.constraint(equalTo: outColorLabel.trailingAnchor, constant: 5),
            outColorNameLabel.trailingAnchor.constraint(equalTo: inColorLabel.leadingAnchor, constant: 2),
            outColorNameLabel.heightAnchor.constraint(equalToConstant: 22),
            
            inColorLabel.topAnchor.constraint(equalTo: trimLabel.bottomAnchor, constant: 13),
            inColorLabel.leadingAnchor.constraint(equalTo: centerXAnchor),
            inColorLabel.widthAnchor.constraint(equalToConstant: 25),
            inColorLabel.heightAnchor.constraint(equalToConstant: 22),
            
            inColorNameLabel.topAnchor.constraint(equalTo: trimLabel.bottomAnchor, constant: 13),
            inColorNameLabel.leadingAnchor.constraint(equalTo: inColorLabel.trailingAnchor, constant: 5),
            inColorNameLabel.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -17),
            inColorNameLabel.heightAnchor.constraint(equalToConstant: 22),
            
            selectOptionLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 17),
            selectOptionLabel.topAnchor.constraint(equalTo: inColorLabel.bottomAnchor, constant: 10),
            selectOptionLabel.widthAnchor.constraint(equalToConstant: 50),
            selectOptionLabel.heightAnchor.constraint(equalToConstant: 22),
            
            selectOptionScrollView.topAnchor.constraint(equalTo: outColorLabel.bottomAnchor, constant: 9),
            selectOptionScrollView.leadingAnchor.constraint(equalTo: selectOptionLabel.trailingAnchor, constant: 7),
            selectOptionScrollView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -17),
            selectOptionScrollView.heightAnchor.constraint(equalToConstant: 24),
            
            tagsView.topAnchor.constraint(equalTo: selectOptionLabel.bottomAnchor, constant: 10),
            tagsView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 17),
            tagsView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -17),
            bottomAnchor.constraint(equalTo: tagsView.bottomAnchor, constant: 10)
        ])
    }
    func setUp(with model: ArchivingReviewCellModel) {
        titleLabel.text = model.carName
        let dateForamtter = DateFormatter()
        dateForamtter.locale = .init(identifier: "ko_KR")
        dateForamtter.dateFormat = "yy년 M월 d일에 " + model.searchType + "했어요"
        dateLabel.text = dateForamtter.string(from: model.date)
        trimLabel.text = model.trim
        outColorNameLabel.text = model.outColorName
        inColorNameLabel.text = model.inColorName
        tagsView.refresh(by: model.tags)
    }
}
