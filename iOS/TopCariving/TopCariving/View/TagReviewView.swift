//
//  MyCarTagReviewView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/12.
//

import UIKit

class TagReviewView: UIView {
    enum Constant {
        static let interItemSpacing: CGFloat = 6
        static let interGroupSpacing: CGFloat = 12
    }
    enum Section {
        case tag
    }
    
    // MARK: - UI properties
    private let optionNameLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._20))
        return label
    }()
    private let reviewLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.text = "에 대해 시승자들은 이런 후기를 남겼어요"
        return label
    }()
    private lazy var collectionView: UICollectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout )
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.isScrollEnabled = false
        collectionView.register(TagCell.self, forCellWithReuseIdentifier: TagCell.identifier)
        return collectionView
    }()
    private let collectionViewLayout: UICollectionViewLayout = {
        let item = NSCollectionLayoutItem(layoutSize: .init(widthDimension: .estimated(60),
                                                            heightDimension: .absolute(30)))
        
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: .init(widthDimension: .fractionalWidth(1.0),
                                                                         heightDimension: .estimated(30)),
                                                       subitems: [item])
        group.interItemSpacing = .fixed(Constant.interItemSpacing)
        let section = NSCollectionLayoutSection(group: group)
        section.interGroupSpacing = 12
        
        return UICollectionViewCompositionalLayout(section: section)
    }()
    
    // MARK: - Properties
    private var dataSource: UICollectionViewDiffableDataSource<Section, String>!
    var collectionViewHeightAnchor: NSLayoutConstraint?
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setCollectionViewDataSource()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setCollectionViewDataSource()
    }
    
    // MARK: - Helpers
    private func setUI() {
        translatesAutoresizingMaskIntoConstraints = false
        backgroundColor = .white
        layer.cornerRadius = 8
        [optionNameLabel, reviewLabel, collectionView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        collectionViewHeightAnchor = collectionView.heightAnchor.constraint(equalToConstant: 30)
        collectionViewHeightAnchor?.isActive = true
        
        NSLayoutConstraint.activate([
            optionNameLabel.topAnchor.constraint(equalTo: topAnchor),
            optionNameLabel.leadingAnchor.constraint(equalTo: leadingAnchor),
            optionNameLabel.trailingAnchor.constraint(equalTo: trailingAnchor),
            optionNameLabel.heightAnchor.constraint(equalToConstant: 28),
            
            reviewLabel.topAnchor.constraint(equalTo: optionNameLabel.bottomAnchor, constant: 5),
            reviewLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 2),
            reviewLabel.trailingAnchor.constraint(equalTo: trailingAnchor),
            reviewLabel.heightAnchor.constraint(equalToConstant: 22),
            
            collectionView.topAnchor.constraint(
                equalTo: reviewLabel.bottomAnchor,
                constant: Constant.interGroupSpacing
            ),
            collectionView.leadingAnchor.constraint(equalTo: leadingAnchor),
            collectionView.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    private func setCollectionViewDataSource() {
        dataSource = .init(
            collectionView: collectionView,
            cellProvider: { collectionView, indexPath, item in
            guard let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: TagCell.identifier,
                for: indexPath
            ) as? TagCell else { return UICollectionViewCell() }
            cell.setTag(to: item)
            return cell
        })
    }
    func refresh(by tags: [String], with name: String) {
        var snapShot = NSDiffableDataSourceSnapshot<Section, String>()
        snapShot.appendSections([Section.tag])
        snapShot.appendItems(tags)
        dataSource.apply(snapShot)
        
        changeHeight(by: tags)
        optionNameLabel.text = name
    }
    private func changeHeight(by tags: [String]) {
        let width = (window?.windowScene?.screen.bounds.width ?? 375) - 32
        var xPosition: CGFloat = 0.0
        var maxRow: CGFloat = 1.0
        
        tags.forEach { tag in
            let tagWidth = NSString(string: tag).size(
                withAttributes: [NSAttributedString.Key.font: UIFont.designSystem(.init(name: .regular, size: ._14))]
            ).width
            
            if (xPosition + Constant.interItemSpacing + tagWidth) > width {
                xPosition = 0
                maxRow += 1
            } else {
                xPosition += Constant.interItemSpacing + tagWidth
            }
        }
        
        collectionViewHeightAnchor?.constant = CGFloat(maxRow * 30 + (maxRow - 1) * Constant.interGroupSpacing)
    }
}
