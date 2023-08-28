//
//  ArhcivingDetailView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import UIKit

class ArchivingDetailView: UIView {
    enum Section {
        case detail
    }
    // MARK: - UI properties
    private lazy var collectionView: UICollectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout)
        collectionView.register(ArchivingDetailCell.self, forCellWithReuseIdentifier: ArchivingDetailCell.identifier)
        collectionView.register(ArchivingDetailHeader.self,
                                forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader,
                                withReuseIdentifier: ArchivingDetailHeader.identifier)
        collectionView.backgroundColor = .hyundaiLightSand
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        return collectionView
    }()
    private let collectionViewLayout: UICollectionViewLayout = {
        let item = NSCollectionLayoutItem(layoutSize: .init(widthDimension: .fractionalWidth(1.0),
                                                            heightDimension: .estimated(250)))
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: .init(widthDimension: .fractionalWidth(1.0),
                                                                         heightDimension: .estimated(250)),
                                                       subitems: [item])
        let headerSize = NSCollectionLayoutSize(widthDimension: .fractionalWidth(1.0),
                                                       heightDimension: .estimated(600))
        let header = NSCollectionLayoutBoundarySupplementaryItem(layoutSize: headerSize,
                                                                 elementKind: UICollectionView.elementKindSectionHeader,
                                                                 alignment: .top)
        let section = NSCollectionLayoutSection(group: group)
        section.interGroupSpacing = 12
        section.boundarySupplementaryItems = [header]
        return UICollectionViewCompositionalLayout(section: section)
    }()
    // MARK: - Properties
    private var dataSource: UICollectionViewDiffableDataSource<Section, ArchivingDetailCellModel>!
    
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
        [collectionView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            collectionView.topAnchor.constraint(equalTo: topAnchor),
            collectionView.leadingAnchor.constraint(equalTo: leadingAnchor),
            collectionView.trailingAnchor.constraint(equalTo: trailingAnchor),
            collectionView.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
    }
    private func setCollectionViewDataSource() {
        dataSource = .init(collectionView: collectionView,
                           cellProvider: { collectionView, indexPath, item in
            guard let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: ArchivingDetailCell.identifier,
                for: indexPath) as? ArchivingDetailCell else { return UICollectionViewCell() }
            cell.setUp(with: item)
            return cell
        })
    }
    func refresh(by models: [ArchivingDetailCellModel], review: String, summaryModel: MyCarivingCarSummaryModel) {
        var snapShot = dataSource.snapshot()
        snapShot.appendSections([.detail])
        snapShot.appendItems(models)
        dataSource.apply(snapShot)
        dataSource.supplementaryViewProvider = { collectionView, kind, indexPath in
            guard let header = collectionView.dequeueReusableSupplementaryView(
                ofKind: kind,
                withReuseIdentifier: ArchivingDetailHeader.identifier,
                for: indexPath
            ) as? ArchivingDetailHeader else { return nil }
            header.setUp(with: review, model: summaryModel)
            return header
        }
    }
}
