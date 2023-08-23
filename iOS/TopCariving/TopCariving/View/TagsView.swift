//
//  TagsView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import UIKit

class TagsView: UIView {
    enum Constant {
        static let interItemSpacing: CGFloat = 6
        static let interGroupSpacing: CGFloat = 12
    }
    enum Section {
        case tag
    }
    // MARK: - UI properties
    lazy var collectionView: UICollectionView = {
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
                                                                         heightDimension: .absolute(30)),
                                                       subitems: [item])
        group.interItemSpacing = .fixed(Constant.interItemSpacing)
        let section = NSCollectionLayoutSection(group: group)
        section.interGroupSpacing = 12
        
        return UICollectionViewCompositionalLayout(section: section)
    }()
    // MARK: - Properties
    private var dataSource: UICollectionViewDiffableDataSource<Section, String>!
    private var collectionViewHeightAnchor: NSLayoutConstraint?
    lazy var collectionViewBottomAnchor = self.collectionView.bottomAnchor
    
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
        isUserInteractionEnabled = false
        addSubview(collectionView)
    }
    private func setLayout() {
        collectionViewHeightAnchor = collectionView.heightAnchor.constraint(equalToConstant: 30)
        collectionViewHeightAnchor?.isActive = true
        NSLayoutConstraint.activate([
            collectionView.topAnchor.constraint(equalTo: topAnchor),
            collectionView.leadingAnchor.constraint(equalTo: leadingAnchor),
            collectionView.trailingAnchor.constraint(equalTo: trailingAnchor),
            collectionView.heightAnchor.constraint(equalTo: heightAnchor)
        ])
    }
    
    // MARK: - Helpers
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
    func refresh(by tags: [String]) {
        var snapShot = NSDiffableDataSourceSnapshot<Section, String>()
        snapShot.appendSections([Section.tag])
        snapShot.appendItems(tags)
        dataSource.apply(snapShot)
        
        changeHeight(by: tags)
    }
    private func changeHeight(by tags: [String]) {
        let width = CGRect.screenBounds.width - 32
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
