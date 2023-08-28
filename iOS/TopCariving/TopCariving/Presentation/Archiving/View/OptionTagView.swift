//
//  OptionTagView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import Combine
import UIKit

class OptionTagView: UIView {
    enum Section {
        case option
    }
    // MARK: - UI properties
    private lazy var collectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout )
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.isScrollEnabled = false
        collectionView.register(OptionTagCell.self, forCellWithReuseIdentifier: OptionTagCell.identifier)
        collectionView.delegate = self
        return collectionView
    }()
    private let collectionViewLayout: UICollectionViewLayout = {
        let item = NSCollectionLayoutItem(layoutSize: .init(widthDimension: .estimated(60),
                                                            heightDimension: .absolute(30)))
        
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: .init(widthDimension: .fractionalWidth(1.0),
                                                                         heightDimension: .estimated(30)),
                                                       subitems: [item])
        group.interItemSpacing = .fixed(3)
        let section = NSCollectionLayoutSection(group: group)
        section.interGroupSpacing = 4
        
        return UICollectionViewCompositionalLayout(section: section)
    }()
    
    // MARK: - Properties
    private var dataSource: UICollectionViewDiffableDataSource<Section, String>!
    var collectionViewHeightAnchor: NSLayoutConstraint?
    var tapRemoveTagIndexPathSubject = PassthroughSubject<IndexPath, Never>()
    
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
        addSubview(collectionView)
    }
    private func setLayout() {
        collectionViewHeightAnchor = collectionView.heightAnchor.constraint(equalToConstant: 30)
        collectionViewHeightAnchor?.isActive = true
        NSLayoutConstraint.activate([
            collectionView.topAnchor.constraint(equalTo: topAnchor),
            collectionView.leadingAnchor.constraint(equalTo: leadingAnchor),
            collectionView.trailingAnchor.constraint(equalTo: trailingAnchor),
            heightAnchor.constraint(equalTo: collectionView.heightAnchor)
        ])
    }
    private func setCollectionViewDataSource() {
        dataSource = .init(collectionView: collectionView,
                           cellProvider: { collectionView, indexPath, item in
            guard let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: OptionTagCell.identifier,
                for: indexPath
            ) as? OptionTagCell else { return UICollectionViewCell() }
            cell.setName(to: item)
            return cell
        })
    }
    func refresh(by options: [String]) {
        var snapShot = NSDiffableDataSourceSnapshot<Section, String>()
        snapShot.appendSections([Section.option])
        snapShot.appendItems(options)
        dataSource.apply(snapShot)
        
        changeHeight(by: options)
    }
    private func changeHeight(by options: [String]) {
        let width = CGRect.screenBounds.width - 32
        var xPosition: CGFloat = 0.0
        var maxRow: CGFloat = 1.0
        
        options.forEach { option in
            let tagWidth = NSString(string: option).size(
                withAttributes: [NSAttributedString.Key.font: UIFont.designSystem(.init(name: .regular, size: ._14))]
            ).width + 44
            
            if (xPosition + 3 + tagWidth) > width {
                xPosition = 0
                maxRow += 1
            } else {
                xPosition += 3 + tagWidth
            }
        }
        
        collectionViewHeightAnchor?.constant = CGFloat(maxRow * 30 + (maxRow - 1) * 4)
    }
}

extension OptionTagView: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        tapRemoveTagIndexPathSubject.send(indexPath)
    }
}
