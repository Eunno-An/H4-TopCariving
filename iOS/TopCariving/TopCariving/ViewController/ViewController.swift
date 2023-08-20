//
//  ViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/01.
//

import Combine
import UIKit

class ViewController: BaseMyCarViewController {
    // MARK: - UI properties
    private let scrollView: UIScrollView = UIScrollView()
    private let progressView = UIImageView(image: UIImage(named: "ProgressEngine"))
    private let rotatableView: RotatableOptionImageView = RotatableOptionImageView(frame: .zero)
    private let containerStackView = FoldableStackView()
    private let footerView = MyCarFooterView()
    
    // MARK: - Properties
    var bag = Set<AnyCancellable>()
    
    // MARK: - Lifecycles
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setLayout()
        injectMock()
    }
    
    // MARK: - Helpers
    private func setUI() {
        setTitle(to: "펠리세이드")
        view.backgroundColor = .white
        [scrollView, progressView, rotatableView, footerView].forEach {
            view.addSubview($0)
        }
        [rotatableView, containerStackView].forEach {
            scrollView.addSubview($0)
        }
    }
    
    private func setLayout() {
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        progressView.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            progressView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            progressView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            progressView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            progressView.heightAnchor.constraint(equalTo: progressView.widthAnchor, multiplier: 0.154),
            
            scrollView.topAnchor.constraint(equalTo: progressView.bottomAnchor),
            scrollView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor),
            scrollView.bottomAnchor.constraint(equalTo: footerView.topAnchor),
            
            rotatableView.topAnchor.constraint(equalTo: scrollView.contentLayoutGuide.topAnchor, constant: -40),
            rotatableView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            rotatableView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            rotatableView.heightAnchor.constraint(equalTo: view.widthAnchor, multiplier: 0.63),
            
            containerStackView.topAnchor.constraint(equalTo: rotatableView.bottomAnchor),
            containerStackView.centerXAnchor.constraint(equalTo: scrollView.centerXAnchor),
            containerStackView.widthAnchor.constraint(equalToConstant: view.frame.width - 32),
            containerStackView.bottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor),
            
            footerView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            footerView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            footerView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            footerView.heightAnchor.constraint(equalTo: footerView.widthAnchor, multiplier: 0.365)
        ])
    }
    
    private func injectMock() {
        // swiftlint: disable line_length
        let testIcons = [(URL(string: "https://topcariving.s3.ap-northeast-2.amazonaws.com/png/leBlanc1.png")!, "20인치\n알로이 휠"),
                       (URL(string: "https://topcariving.s3.ap-northeast-2.amazonaws.com/png/leBlanc2.png")!, "서라운드 뷰\n모니터"),
                       (URL(string: "https://topcariving.s3.ap-northeast-2.amazonaws.com/png/leBlanc3.png")!, "클러스터\n(12.3인 컬러 LCD)")]
        let titles = ["1. Le Blanc", "2. Exclusive", "3. Prestige", "4. Calligraphy"]
        let prices = ["47,720,000", "40,440,000", "47,720,000", "52,540,000"]
        (0..<4).forEach {
            let view = CarSummaryContainer()
            view.setInfo(to: titles[$0], price: prices[$0], icons: testIcons)
            containerStackView.addArrangedSubview(view)
        }
        footerView.tapNextButton.sink(receiveValue: { [weak self] in
            guard let self else { return }
            self.navigationController?.pushViewController(MyCarCompletionViewController(), animated: true)
        })
        .store(in: &bag)
    }
    
}
