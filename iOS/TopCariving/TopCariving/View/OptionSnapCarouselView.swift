//
//  OptionDescriptionCollectionView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/13.
//

import UIKit

struct OptionDescriptionViewModel: Hashable {
    let index: Int
    let maxIndex: Int
    let title: String
    let optionDescription: String
}

class OptionSnapCarouselView: UIView {
    enum Section {
        case optionDescription
    }
    
    // MARK: - UI properties
    private lazy var collectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.register(
            OptionDescriptionCell.self,
            forCellWithReuseIdentifier: OptionDescriptionCell.identifier
        )
        collectionView.alwaysBounceVertical = false
        return collectionView
    }()
    private let collectionViewLayout: UICollectionViewCompositionalLayout = {
        let item = NSCollectionLayoutItem(layoutSize: .init(widthDimension: .fractionalWidth(1),
                                                            heightDimension: .fractionalHeight(1)))
        item.contentInsets = .init(top: 0, leading: 16, bottom: 0, trailing: 16)
        let group = NSCollectionLayoutGroup.horizontal(
            layoutSize: .init(widthDimension: .fractionalWidth(1), heightDimension: .fractionalHeight(1)),
            subitems: [item]
        )
        let section = NSCollectionLayoutSection(group: group)
        section.orthogonalScrollingBehavior = .paging
        return UICollectionViewCompositionalLayout(section: section)
    }()
    
    // MARK: - Properties
    private var dataSource: UICollectionViewDiffableDataSource<Section, OptionDescriptionViewModel>!
    
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
        addSubview(collectionView)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            collectionView.topAnchor.constraint(equalTo: topAnchor),
            collectionView.bottomAnchor.constraint(equalTo: bottomAnchor),
            collectionView.leadingAnchor.constraint(equalTo: leadingAnchor),
            collectionView.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    private func setCollectionViewDataSource() {
        dataSource = .init(
            collectionView: collectionView,
            cellProvider: { collectionView, indexPath, item in
                guard let cell = collectionView.dequeueReusableCell(
                    withReuseIdentifier: OptionDescriptionCell.identifier,
                    for: indexPath
                ) as? OptionDescriptionCell else { return UICollectionViewCell() }
                cell.setUp(with: item)
                return cell
        })
    }
    func refresh(by models: [OptionDescriptionViewModel]) {
        var snapShot = NSDiffableDataSourceSnapshot<Section, OptionDescriptionViewModel>()
        snapShot.appendSections([Section.optionDescription])
        snapShot.appendItems(models)
        dataSource.apply(snapShot)
    }
}
