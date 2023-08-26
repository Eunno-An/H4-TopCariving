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
    private lazy var collectionView: UICollectionView = {
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
    private lazy var refreshControl: UIRefreshControl = {
        let refreshControl = UIRefreshControl()
        refreshControl.tintColor = .white
        refreshControl.addTarget(self, action: #selector(scrollRefresh(_:)), for: .allEvents)
        return refreshControl
    }()
    
    // MARK: - Properties
    private var dataSource: UICollectionViewDiffableDataSource<Section, ArchivingReviewCellModel>!
    private var bag = Set<AnyCancellable>()
    var tapCellIndexPathSubject = PassthroughSubject<IndexPath, Never>()
    private var viewModel = ArchivingReviewViewModel(httpClient: HTTPClient())
    
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
        collectionView.refreshControl = refreshControl
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
        viewModel.process.receive(on: DispatchQueue.main)
            .sink { [weak self] status in
                guard let self else { return }
                DispatchQueue.main.async {
                    switch status {
                    case .finished:
                        self.refreshControl.endRefreshing()
                    default:
                        break
                    }
                }
                
            }.store(in: &bag)
        var snapShot = dataSource.snapshot()
        snapShot.appendSections([.review])
        snapShot.appendItems(models)
        dataSource.apply(snapShot)
    }
    @objc func scrollRefresh(_ sender: Any) {
        viewModel.fetchReviewCellData(page: 1)
    }
    
}

extension ArchivingReviewView: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        tapCellIndexPathSubject.send(indexPath)
    }
}
