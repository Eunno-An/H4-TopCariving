//
//  ColorSectionViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/18.
//

import UIKit

class ColorSectionViewController: BaseMyCarViewController {
    // MARK: - UI properties
    private let scrollView: UIScrollView = UIScrollView()
    private let rotatableView = RotatableOptionImageView()
    private let colorSelectionView = ColorSelectionView()
    private let tagReviewView = TagReviewView()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        testReviewView("문라이트 블루펄0")
        testColor()
        testPush()
    }
    
    // MARK: - Helpers
    override func setUI() {
        super.setUI()
        setProgress(to: .exteriorColor)
        view.addSubview(scrollView)
        [rotatableView, colorSelectionView, tagReviewView].forEach {
            scrollView.addSubview($0)
        }
    }
    override func setLayout() {
        super.setLayout()
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            scrollView.topAnchor.constraint(equalTo: progressView.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: footerView.topAnchor),
            
            rotatableView.topAnchor.constraint(equalTo: scrollView.contentLayoutGuide.topAnchor, constant: -40),
            rotatableView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            rotatableView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            rotatableView.heightAnchor.constraint(equalTo: view.widthAnchor, multiplier: 0.63),
            
            colorSelectionView.topAnchor.constraint(equalTo: rotatableView.bottomAnchor, constant: 10),
            colorSelectionView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            colorSelectionView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            colorSelectionView.heightAnchor.constraint(equalToConstant: 92),
            
            tagReviewView.topAnchor.constraint(equalTo: colorSelectionView.bottomAnchor, constant: 20),
            tagReviewView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            tagReviewView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16),
            tagReviewView.collectionView.bottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor, constant: -8)
            ])
    }
    private func testReviewView(_ name: String) {
        tagReviewView.refresh(by: ["어린이👶", "이것만 있으면 나도 주차고수🚘", "대형견도 문제 없어요🐶", "큰 짐도 OK🧳"].shuffled(), with: name)
    }

    private func testColor() {
        let images = [
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/black.png",
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/silver.png",
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/blue.png",
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/brown.png",
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/gray.png",
            "https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/white.png"
        ]
        let names = ["어비스블랙펄",
                     "쉬머링 실버 메탈릭",
                     "문라이프 블루 펄",
                     "가이아 브라운 펄",
                     "그라파이트 그레이 메탈릭",
                     "크리미 화이트 펄"]
        colorSelectionView.setCategoryName(to: "외장")
        colorSelectionView.refresh(by: images)
        colorSelectionView.setColorName(to: "어비스블랙펄")
        let prfix = ["", "silver_", "blue_", "brown_", "gray_", "white_"]
        colorSelectionView.tapColorSubject.sink(receiveValue: {
            self.colorSelectionView.setColorName(to: names[$0.row])
            self.testReviewView(names[$0.row])
            self.rotatableView.prefix = prfix[$0.row]
        }).store(in: &bag)
    }
    private func testPush() {
        footerView.tapNextButton.sink(receiveValue: { [weak self] in
            guard let self else { return }
            self.navigationController?.pushViewController(InteriorColorSelctionViewController(), animated: true)
        }).store(in: &bag)
    }
}
