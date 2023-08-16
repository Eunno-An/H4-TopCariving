//
//  OptionSelectView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/12.
//

import Combine
import UIKit

protocol OptionSelectViewDataSource: NSObject {
    func numberOfOption(_ optionSelectView: OptionSelectView) -> Int
    func optionSelectViewModel(_ optionSelectView: OptionSelectView, at indexPath: IndexPath) -> OptionCardViewModel
}

class OptionSelectView: UIView {
    // MARK: - UI properties
    private let optionCountLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.textColor = .darkGray
        label.setFont(to: .init(name: .medium, size: ._16))
        return label
    }()
    private let includedOptionButton: UIButton = {
        let button = UIButton()
        button.translatesAutoresizingMaskIntoConstraints = false
        button.setTitle("기본 포함 품목", for: .normal)
        button.setTitleColor(.black, for: .normal)
        button.titleLabel?.font = .designSystem(.init(name: .regular, size: ._14))
        button.layer.cornerRadius = 4
        button.backgroundColor = .hyundaiSand
        return button
    }()
    private lazy var collectionView: UICollectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout)
        collectionView.isScrollEnabled = true
        collectionView.contentInset = .zero
        collectionView.showsHorizontalScrollIndicator = false
        collectionView.register(OptionCardCell.self, forCellWithReuseIdentifier: OptionCardCell.identifier)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        return collectionView
    }()
    private let collectionViewLayout = {
        let layout = UICollectionViewFlowLayout()
        layout.scrollDirection = .horizontal
        layout.itemSize = CGSize(width: 160, height: 197)
        layout.sectionInset = .init(top: 0, left: 0, bottom: 0, right: 5)
        layout.headerReferenceSize.width = 5
        return layout
    }()
    
    // MARK: - Properties
    weak var datasource: OptionSelectViewDataSource?
    var tapAddButtonSubject = PassthroughSubject<IndexPath, Never>()
    var tapCellSubject = PassthroughSubject<IndexPath, Never>()
    
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
    
    // MARK: - Helpers
    private func setUI() {
        translatesAutoresizingMaskIntoConstraints = false
        [optionCountLabel, includedOptionButton, collectionView].forEach {
            addSubview($0)
        }
        backgroundColor = .white
        collectionView.dataSource = self
        collectionView.delegate = self
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            optionCountLabel.topAnchor.constraint(equalTo: topAnchor, constant: 13),
            optionCountLabel.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 16),
            optionCountLabel.heightAnchor.constraint(equalToConstant: 24),
            optionCountLabel.widthAnchor.constraint(equalToConstant: 100),
            
            includedOptionButton.topAnchor.constraint(equalTo: topAnchor, constant: 13),
            includedOptionButton.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -16),
            includedOptionButton.widthAnchor.constraint(equalToConstant: 100),
            includedOptionButton.heightAnchor.constraint(equalToConstant: 26),
            
            collectionView.topAnchor.constraint(equalTo: optionCountLabel.bottomAnchor, constant: 13),
            collectionView.heightAnchor.constraint(equalToConstant: 197),
            collectionView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 0),
            collectionView.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
    func refresh() {
        collectionView.reloadData()
        optionCountLabel.text = "선택옵션 \(datasource?.numberOfOption(self) ?? 0)개"
    }
}

extension OptionSelectView: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        tapCellSubject.send(indexPath)
    }
}

extension OptionSelectView: UICollectionViewDataSource {
    func collectionView(
        _ collectionView: UICollectionView,
        numberOfItemsInSection section: Int
    ) -> Int {
        datasource?.numberOfOption(self) ?? 0
    }
    func collectionView(
        _ collectionView: UICollectionView,
        cellForItemAt indexPath: IndexPath
    ) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(
            withReuseIdentifier: OptionCardCell.identifier,
            for: indexPath
        ) as? OptionCardCell,
              let datasource = datasource else { return UICollectionViewCell() }
        
        cell.setUp(with: datasource.optionSelectViewModel(self, at: indexPath))
        cell.tapAddButtonPublisher
            .sink(receiveValue: { [weak self] in
                guard let self else { return }
                cell.isAdded.toggle()
                self.tapAddButtonSubject.send(indexPath)
            })
            .store(in: &cell.bag)
        
        return cell
    }
}
