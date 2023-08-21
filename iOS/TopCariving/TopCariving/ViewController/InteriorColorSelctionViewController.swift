//
//  InteriorColorSelctionViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/18.
//

import UIKit

class InteriorColorSelctionViewController: BaseMyCarViewController {
    // MARK: - UI properties
    private let scrollView: UIScrollView = UIScrollView()
    private let carImageView = UIImageView()
    private let colorSelectionView = ColorSelectionView()
    private let tagReviewView = TagReviewView()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        testReviewView("퀄팅 천연(블랙)")
        testColor()
        testPush()
    }
    
    // MARK: - Helpers
    override func setUI() {
        super.setUI()
        setProgress(to: .interiorColor)
        view.addSubview(scrollView)
        [carImageView, colorSelectionView, tagReviewView].forEach {
            scrollView.addSubview($0)
        }
    }
    override func setLayout() {
        super.setLayout()
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        carImageView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: progressView.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: footerView.topAnchor),
            
            carImageView.topAnchor.constraint(equalTo: scrollView.contentLayoutGuide.topAnchor, constant: -40),
            carImageView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            carImageView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            carImageView.heightAnchor.constraint(equalTo: view.widthAnchor, multiplier: 0.63),
            
            colorSelectionView.topAnchor.constraint(equalTo: carImageView.bottomAnchor, constant: 10),
            colorSelectionView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            colorSelectionView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            colorSelectionView.heightAnchor.constraint(equalToConstant: 92),
            
            tagReviewView.topAnchor.constraint(equalTo: colorSelectionView.bottomAnchor, constant: 20),
            tagReviewView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            tagReviewView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            tagReviewView.collectionView.bottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor,
                                                                 constant: -8)
            ])
    }
    private func testReviewView(_ name: String) {
        tagReviewView.refresh(by: ["어린이👶", "이것만 있으면 나도 주차고수🚘", "대형견도 문제 없어요🐶", "큰 짐도 OK🧳"].shuffled(), with: name)
    }

    private func testColor() {
        let images = [
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/black.png",
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/gray.png"
        ]
        let interImages = [
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/internal_color/black_internal.png",
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/internal_color/gray_internal.png"
        ]
        let names = ["퀄팅 천연(블랙)", "쿨그레이"]
        carImageView.setAsyncImage(url: interImages[0])
        colorSelectionView.setCategoryName(to: "내장")
        colorSelectionView.refresh(by: images)
        colorSelectionView.setColorName(to: names[0])
        colorSelectionView.tapColorSubject.sink(receiveValue: {
            self.colorSelectionView.setColorName(to: names[$0.row])
            self.testReviewView(names[$0.row])
            self.carImageView.setAsyncImage(url: interImages[$0.row])
        }).store(in: &bag)
    }
    private func testPush() {
        footerView.tapNextButton.sink(receiveValue: { [weak self] in
            guard let self else { return }
            self.navigationController?.pushViewController(SelectOptionViewController(), animated: true)
        }).store(in: &bag)
    }
}
