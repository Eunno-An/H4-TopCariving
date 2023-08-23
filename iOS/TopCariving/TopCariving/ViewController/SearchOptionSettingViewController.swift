//
//  SearchOptionSettingViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/21.
//

import UIKit

class SearchOptionSettingViewController: BaseSearchViewController {
    enum Section {
        case option
    }
    // MARK: - UI properties
    private lazy var collectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout )
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.isScrollEnabled = false
        collectionView.register(SettingOptionTagCell.self, forCellWithReuseIdentifier: SettingOptionTagCell.identifier)
        collectionView.delegate = self
        collectionView.allowsMultipleSelection = true
        return collectionView
    }()
    private let collectionViewLayout: UICollectionViewLayout = {
        let item = NSCollectionLayoutItem(layoutSize: .init(widthDimension: .estimated(60),
                                                            heightDimension: .absolute(30)))
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: .init(widthDimension: .fractionalWidth(1.0),
                                                                         heightDimension: .estimated(30)),
                                                       subitems: [item])
        group.interItemSpacing = .fixed(5)
        let section = NSCollectionLayoutSection(group: group)
        section.interGroupSpacing = 7
        
        return UICollectionViewCompositionalLayout(section: section)
    }()
    private let optionTagView: OptionTagView = OptionTagView()
    private let acceptButton: CTAButton = {
       let button = CTAButton()
        button.setTitle(to: "선택 항목 적용하기")
        return button
    }()
    // MARK: - Properties
    private var dataSource: UICollectionViewDiffableDataSource<Section, SettingOptionTagCellModel>!
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
        setCollectionViewDataSource()
        test()
    }
    
    // MARK: - Helpers
    private func setUI() {
        view.backgroundColor = .white
        setTitle(to: "옵션 설정")
        [collectionView, optionTagView, acceptButton].forEach {
            view.addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            collectionView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 10),
            collectionView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 16),
            collectionView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -16),
            collectionView.bottomAnchor.constraint(equalTo: optionTagView.topAnchor),
            
            optionTagView.bottomAnchor.constraint(equalTo: acceptButton.topAnchor, constant: -10),
            optionTagView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 16),
            optionTagView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -16),
            
            acceptButton.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -20),
            acceptButton.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 16),
            acceptButton.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -16),
            acceptButton.heightAnchor.constraint(equalToConstant: 56)
        ])
    }
    private func setCollectionViewDataSource() {
        dataSource = .init(collectionView: collectionView,
                           cellProvider: { collectionView, indexPath, item in
            guard let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: SettingOptionTagCell.identifier,
                for: indexPath
            ) as? SettingOptionTagCell else { return UICollectionViewCell() }
            cell.setUp(with: item)
            return cell
        })
    }
    func refresh(by options: [SettingOptionTagCellModel]) {
        var snapShot = NSDiffableDataSourceSnapshot<Section, SettingOptionTagCellModel>()
        snapShot.appendSections([Section.option])
        snapShot.appendItems(options)
        dataSource.apply(snapShot)
    }
    
    var data = ["컴포트 || 패키지", "주차 보조 시스템 ||", "2열 통풍시트", "듀얼 와이드 선루프", "현대스마트센스 |", "빌트인 캠(보조배터리 포함)"]
    func test() {
        refresh(by: data.map { SettingOptionTagCellModel.init(name: $0, isSelected: false) })
        optionTagView.refresh(by: data)
    }
}

extension SearchOptionSettingViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
    }
    func collectionView(_ collectionView: UICollectionView, didDeselectItemAt indexPath: IndexPath) {
        
    }
}
