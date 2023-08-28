//
//  OptionDescriptionCollectionView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/13.
//

import Combine
import UIKit

struct OptionDescriptionModel: Hashable {
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
    private lazy var collectionView: UICollectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.register(
            OptionDescriptionCell.self,
            forCellWithReuseIdentifier: OptionDescriptionCell.identifier
        )
        collectionView.isPagingEnabled = true
        collectionView.alwaysBounceVertical = false
        collectionView.delegate = self
        return collectionView
    }()
    private lazy var collectionViewLayout: UICollectionViewFlowLayout = {
        let layout = UICollectionViewFlowLayout()
        layout.itemSize = .init(width: CGRect.screenBounds.width - 32, height: 131)
        layout.scrollDirection = .horizontal
        layout.sectionInset = .init(top: 0, left: 16, bottom: 0, right: 16)
        layout.minimumLineSpacing = 32
        return layout
    }()

    // MARK: - Properties
    private var dataSource: UICollectionViewDiffableDataSource<Section, OptionDescriptionModel>!
    var didDisplayCellIndexPathSubject = PassthroughSubject<IndexPath, Never>()
    
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
    func refresh(by models: [OptionDescriptionModel]) {
        var snapShot = NSDiffableDataSourceSnapshot<Section, OptionDescriptionModel>()
        snapShot.appendSections([Section.optionDescription])
        snapShot.appendItems(models)
        dataSource.apply(snapShot)
    }
}

extension OptionSnapCarouselView: UICollectionViewDelegate {
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        guard let indexPath = collectionView.indexPathsForVisibleItems.first else { return }
        didDisplayCellIndexPathSubject.send(indexPath)
    }
}
