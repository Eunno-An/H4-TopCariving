//
//  SummaryViewController.swift
//  TopCariving
//
//  Created by cho seungki on 2023/08/15.
//

import UIKit

struct TestModel {
    let trim = [("펠리세이드 Le Blanc", "47,340,000 원"), ("디젤 2.2 / 4WD / 7인승", "+1,090,000 원")]
    let colors = [("외장", "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/blue.png", "문라이트 블루펄"),
                  ("내장", "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/blue.png", "문라이트 블루펄")]
    let options = [("컴포트  ||", "+1,090,000 원"), ("컴포트  ||", "+1,090,000 원"), ("컴포트  ||", "+1,090,000 원")]
    let totalPrice = 47340000 + 1090000 + 1090000 + 1090000
    
    let numberOfSection = 3
    var cellCount: Int {
        trim.count + colors.count + options.count
    }
}

class SummaryViewController: UIViewController {
    enum Section: Int, CaseIterable {
        case trim
        case color
        case option
    }
    
    // MARK: - UI Property
    private let titleLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._18))
        label.text = "견적요약보기"
        return label
    }()
    private lazy var collectionView: UICollectionView = {
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: self.collectionViewLayout)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.register(SummaryCell.self, forCellWithReuseIdentifier: SummaryCell.identifier)
        collectionView.register(SummaryColorCell.self, forCellWithReuseIdentifier: SummaryColorCell.identifier)
        collectionView.register(SummaryHeaderView.self,
                                forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader,
                                withReuseIdentifier: SummaryHeaderView.identifier)
        collectionView.dataSource = self
        return collectionView
    }()
    private lazy var collectionViewLayout: UICollectionViewLayout = {
        let layout = UICollectionViewFlowLayout()
        let width = CGRect.screenBounds.width ?? 352
        layout.minimumInteritemSpacing = 8
        layout.itemSize = .init(width: width, height: 22)
        layout.headerReferenceSize = .init(width: width, height: 42)
        layout.sectionInset = .init(top: 20, left: 0, bottom: 20, right: 0)
        layout.minimumLineSpacing = 12
        return layout
    }()
    // MARK: - Property
    let testModel = TestModel()
    
    // MARK: - LifeCycle
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
        if #available(iOS 15, *) {
            setSheet()
        }
    }
    
    // MARK: - Helper
    private func setUI() {
        view.backgroundColor = .white
        [titleLabel, collectionView].forEach {
            view.addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: view.topAnchor, constant: 25),
            titleLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 18),
            titleLabel.heightAnchor.constraint(equalToConstant: 24),
            
            collectionView.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 16),
            collectionView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            collectionView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            collectionView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor)
        ])
    }
    @available(iOS 15, *)
    private func setSheet() {
        if let sheetPresentationController = sheetPresentationController {
            sheetPresentationController.prefersGrabberVisible = true
            sheetPresentationController.detents = [.large()]
            if #available(iOS 16, *) {
                sheetPresentationController.detents.append(
                    .custom { [weak self] context in
                        guard let self else { return context.maximumDetentValue }
                        let cellHeight = CGFloat(self.testModel.cellCount * 22)
                        let sectionHeight = CGFloat(self.testModel.numberOfSection * 42)
                        return CGFloat( cellHeight + sectionHeight + 250)
                    }
                )
            }
        }
    }
}

extension SummaryViewController: UICollectionViewDataSource {
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        Section.allCases.count
    }
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        switch Section(rawValue: section) {
        case .trim:
            return testModel.trim.count
        case .color:
            return testModel.colors.count
        case .option:
            return testModel.options.count
        default:
            return 0
        }
    }
    func collectionView(
        _ collectionView: UICollectionView,
        viewForSupplementaryElementOfKind kind: String,
        at indexPath: IndexPath
    ) -> UICollectionReusableView {
        guard let header = collectionView.dequeueReusableSupplementaryView(
            ofKind: kind,
            withReuseIdentifier: SummaryHeaderView.identifier,
            for: indexPath
        ) as? SummaryHeaderView else { return UICollectionReusableView() }
        
        switch Section(rawValue: indexPath.section) {
        case .trim:
            header.setup(with: "총 견적 금액", price: .decimalStyle(from: testModel.totalPrice))
        case .color:
            header.setup(with: "색상")
        case .option:
            header.setup(with: "선택옵션 \(testModel.options.count)개")
        default:
            break
        }
        
        return header
    }
    func collectionView(
        _ collectionView: UICollectionView,
        cellForItemAt indexPath: IndexPath
    ) -> UICollectionViewCell {
        switch Section(rawValue: indexPath.section) {
        case .trim:
            guard let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: SummaryCell.identifier,
                for: indexPath
            ) as? SummaryCell else { break }
            cell.setup(with: testModel.trim[indexPath.row].0,
                       price: testModel.trim[indexPath.row].1)
            return cell
        case .color:
            guard let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: SummaryColorCell.identifier,
                for: indexPath
            ) as? SummaryColorCell else { break }
            cell.setUp(with: testModel.colors[indexPath.row].0,
                       image: testModel.colors[indexPath.row].1,
                       name: testModel.colors[indexPath.row].2)
            return cell
        case .option:
            guard let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: SummaryCell.identifier,
                for: indexPath
            ) as? SummaryCell else { break }
            cell.setup(with: testModel.options[indexPath.row].0,
                       price: testModel.options[indexPath.row].1)
            return cell
        default:
            break
        }
        return UICollectionViewCell()
    }
}
