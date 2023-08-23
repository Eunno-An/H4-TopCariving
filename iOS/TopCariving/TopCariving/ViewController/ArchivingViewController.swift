//
//  ArchivingViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/20.
//

import Combine
import UIKit

class ArchivingViewController: BaseArchivingViewController {
    // MARK: - UI properties
    private let searchButton = ArchivingSearchButton()
    private let currentAddedLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._14))
        label.textColor = .darkGray
        label.text = "현재 추가한 옵션들을 가져왔어요"
        return label
    }()
    private let optionTagView = OptionTagView()
    private let reviewBackgroundView: UIView = {
        let view = UIView()
        view.translatesAutoresizingMaskIntoConstraints = false
        view.backgroundColor = .hyundaiLightSand
        return view
    }()
    private let newestLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.setFont(to: .init(name: .medium, size: ._14))
        label.textColor = .darkGray
        label.text = "팰리세이드에 관한 리뷰를 최신순으로 정렬했어요!"
        return label
    }()
    private let reviewView = ArchivingReviewView()
    // MARK: - Properties
    private var bag = Set<AnyCancellable>()
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
        setEvent()
        test()
    }
    
    // MARK: - Helpers
    private func setUI() {
        view.backgroundColor = .white
        [searchButton, currentAddedLabel, optionTagView, reviewBackgroundView, newestLabel, reviewView].forEach {
            view.addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            searchButton.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 14),
            searchButton.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            searchButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            searchButton.heightAnchor.constraint(equalToConstant: 46),
            
            currentAddedLabel.topAnchor.constraint(equalTo: searchButton.bottomAnchor, constant: 20),
            currentAddedLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 17),
            currentAddedLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -17),
            currentAddedLabel.heightAnchor.constraint(equalToConstant: 22),
            
            optionTagView.topAnchor.constraint(equalTo: currentAddedLabel.bottomAnchor, constant: 8),
            optionTagView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            optionTagView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            
            reviewBackgroundView.topAnchor.constraint(equalTo: optionTagView.bottomAnchor, constant: 20),
            reviewBackgroundView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            reviewBackgroundView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            reviewBackgroundView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            
            newestLabel.topAnchor.constraint(equalTo: reviewBackgroundView.topAnchor, constant: 20),
            newestLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            newestLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            newestLabel.heightAnchor.constraint(equalToConstant: 22),
            
            reviewView.topAnchor.constraint(equalTo: newestLabel.bottomAnchor, constant: 14),
            reviewView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            reviewView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            reviewView.bottomAnchor.constraint(equalTo: reviewBackgroundView.bottomAnchor, constant: -10)
        ])
    }
    private func setEvent() {
        searchButton.tabPublisher.sink(receiveValue: { [weak self] _ in
            guard let self else { return }
            let serachFilterViewController = SearchFilterViewController()
            serachFilterViewController.modalPresentationStyle = .pageSheet
            self.present(UINavigationController(rootViewController: serachFilterViewController), animated: true)
        })
        .store(in: &bag)
        reviewView.tapCellIndexPathSubject.sink(receiveValue: { [weak self] indexPath in
            guard let self else { return }
            print(indexPath)
            self.navigationController?.pushViewController(ArchivingDetailViewController(), animated: true)
        }).store(in: &bag)
    }
    private func test() {
        reviewView.refresh(by: [
            .init(carName: "팰리세이드 Le Blanc",
                  searchType: "구매",
                  date: Date(),
                  trim: "디젤 2.2 / 4WD / 7인승",
                  outColorName: "문라이트 블루펄",
                  inColorName: "퀼팅 천연(블랙)",
                  selectOptions: [],
                  tags: ["편리해요😉", "이것만 있으면 나도 주차고수🚘", "대형견도 문제 없어요🐶"]),
            .init(carName: "팰리세이드 Le Blanc",
                  searchType: "시승",
                  date: Date(),
                  trim: "디젤 2.2 / 4WD / 7인승",
                  outColorName: "문라이트 블루펄",
                  inColorName: "퀼팅 천연(블랙)",
                  selectOptions: [],
                  tags: ["이것만 있으면 나도 주차고수🚘", "편리해요😉", "대형견도 문제 없어요🐶"]),
            .init(carName: "팰리세이드 Le Blanc",
                  searchType: "시승",
                  date: Date(),
                  trim: "디젤 2.2 / 4WD / 7인승",
                  outColorName: "문라이트 블루펄",
                  inColorName: "퀼팅 천연(블랙)",
                  selectOptions: [],
                  tags: ["주차고수🚘", "이것만 있으면"])
        ])
        
        optionTagView.refresh(by: ["컴포트 || 패키지", "컴포트 || 패키지1", "컴포트 || 패키지3"])
    }
}
