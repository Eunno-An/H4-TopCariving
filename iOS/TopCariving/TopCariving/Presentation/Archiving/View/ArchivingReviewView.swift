//
//  ArchivingReviewView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import Combine
import UIKit

class ArchivingReviewView: UIView {
    enum Section {
        case review
    }
    // MARK: - UI properties
    lazy var collectionView: UICollectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.register(ArchivingReviewCell.self, forCellWithReuseIdentifier: ArchivingReviewCell.identifier)
        collectionView.backgroundColor = .clear
        collectionView.delegate = self
        return collectionView
    }()
    private let collectionViewLayout: UICollectionViewLayout = {
        let item = NSCollectionLayoutItem(layoutSize: .init(widthDimension: .fractionalWidth(1),
                                                            heightDimension: .estimated(188)))
        
        let group = NSCollectionLayoutGroup.vertical(layoutSize: .init(widthDimension: .fractionalWidth(1.0),
                                                                       heightDimension: .estimated(188)),
                                                     subitems: [item])
        let section = NSCollectionLayoutSection(group: group)
        section.interGroupSpacing = 12
        
        return UICollectionViewCompositionalLayout(section: section)
    }()
    
    // MARK: - Properties
    private var dataSource: UICollectionViewDiffableDataSource<Section, ArchivingReviewCellModel>!
    private var bag = Set<AnyCancellable>()
    var tapCellIndexPathSubject = PassthroughSubject<IndexPath, Never>()
    private var viewModel = ArchivingReviewViewModel(httpClient: HTTPClient())
    private var cellDataSubscription: AnyCancellable?
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setCollectionViewDataSource()
        setupDataSubscription()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
        setCollectionViewDataSource()
        setupDataSubscription()
    }
    // MARK: - Helpers
    private func setUI() {
        collectionView.prefetchDataSource = self
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
        dataSource = .init(collectionView: collectionView,
                           cellProvider: { collectionView, indexPath, item in
            guard let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: ArchivingReviewCell.identifier,
                for: indexPath) as? ArchivingReviewCell else { return UICollectionViewCell() }
            cell.setUp(with: item)
            return cell
        })
    }
    func refresh(by models: [ArchivingReviewCellModel]) {
        var snapShot = self.dataSource.snapshot()
        snapShot.appendSections([.review])
        snapShot.appendItems(models, toSection: .review)
        self.dataSource.apply(snapShot)
    }
    @objc func scrollRefresh(_ sender: Any) {
        viewModel.fetchReviewCellData(page: viewModel.loadPage + 1)
    }
    private func setupDataSubscription() {
        print("Setting subscriber")
        viewModel.reviewCellData.sink { [weak self] cellModels in
            print("Receive Now! : \(cellModels)")
            self?.updateUI(with: cellModels)
        }.store(in: &viewModel.bag)
    }
    func updateUI(with cellModels: [ArchivingReviewCellModel]) {
        refresh(by: cellModels)
    }
}

extension ArchivingReviewView: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        tapCellIndexPathSubject.send(indexPath)
    }
}

extension ArchivingReviewView: UICollectionViewDataSourcePrefetching {
    func collectionView(_ collectionView: UICollectionView, prefetchItemsAt indexPaths: [IndexPath]) {
        let lastIndexPath = indexPaths.last?.item ?? 0
        let count = viewModel.reviewCellData.value.count
        if lastIndexPath >= count - 1 {
            viewModel.requestMoreData(page: viewModel.loadPage + 1)
        }
    }
}
