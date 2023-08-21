//
//  ColorSelctionView.swift
//  TopCariving
//
//  Created by cho seungki on 2023/08/15.
//

import Combine
import UIKit

class ColorSelectionView: UIView {
    enum Section {
        case color
    }
    // MARK: - UI Property
    private let categoryLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._20))
        label.textColor = .hyundaiBlackGray
        return label
    }()
    private let colorLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .regular, size: ._14))
        label.textColor = .hyundaiBlackGray
        label.textAlignment = .right
        return label
    }()
    private lazy var collectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.register(ColorCell.self, forCellWithReuseIdentifier: ColorCell.identifier)
        collectionView.delegate = self
        collectionView.clipsToBounds = false
        collectionView.showsHorizontalScrollIndicator = false
        return collectionView
    }()
    private let collectionViewLayout: UICollectionViewLayout = {
        let layout = UICollectionViewFlowLayout()
        layout.scrollDirection = .horizontal
        layout.minimumInteritemSpacing = 11
        layout.itemSize = .init(width: 48, height: 48)
        return layout
    }()
    
    // MARK: - Property
    private var dataSource: UICollectionViewDiffableDataSource<Section, String>!
    var tapColorSubject = PassthroughSubject<IndexPath, Never>()
    
    // MARK: - LifeCycle
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
    
    // MARK: - Helper
    private func setUI() {
        translatesAutoresizingMaskIntoConstraints = false
        [categoryLabel, colorLabel, collectionView].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            categoryLabel.topAnchor.constraint(equalTo: topAnchor),
            categoryLabel.heightAnchor.constraint(equalToConstant: 28),
            categoryLabel.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            colorLabel.topAnchor.constraint(equalTo: topAnchor, constant: 6),
            colorLabel.trailingAnchor.constraint(equalTo: trailingAnchor),
            colorLabel.heightAnchor.constraint(equalToConstant: 22),
            
            collectionView.topAnchor.constraint(equalTo: categoryLabel.bottomAnchor, constant: 16),
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
                    withReuseIdentifier: ColorCell.identifier,
                    for: indexPath
                ) as? ColorCell else { return UICollectionViewCell() }
                
                cell.setup(with: item)
                return cell
        })
    }
    func refresh(by images: [String]) {
        var snapShot = NSDiffableDataSourceSnapshot<Section, String>()
        snapShot.appendSections([Section.color])
        snapShot.appendItems(images)
        dataSource.apply(snapShot)
        collectionView.selectItem(at: IndexPath(item: 0, section: 0),
                                  animated: false,
                                  scrollPosition: .left)
    }
    func setCategoryName(to text: String) {
        categoryLabel.text = text + " 색상"
    }
    func setColorName(to text: String) {
        colorLabel.text = text
    }
}

extension ColorSelectionView: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        tapColorSubject.send(indexPath)
    }
}
