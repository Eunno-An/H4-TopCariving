//
//  ColorSectionViewController.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/18.
//

import Combine
import UIKit

class ColorSectionViewController: BaseMyCarViewController {
    // MARK: - UI properties
    private let scrollView: UIScrollView = UIScrollView()
    private let rotatableView = RotatableOptionImageView()
    private let colorSelectionView = ColorSelectionView()
    private let tagReviewView = TagReviewView()
    // MARK: - Properties
    let viewModel: ExteriorColorSelectionViewModel
    
    // MARK: - Lifecycles
    init(viewModel: ExteriorColorSelectionViewModel) {
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
    }
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        bind()
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
            tagReviewView.tagsViewBottomAnchor.constraint(equalTo: scrollView.contentLayoutGuide.bottomAnchor,
                                                          constant: -8)
            ])
    }
    func bind() {
        colorSelectionView.setCategoryName(to: "외장")
        let output = viewModel.transform(input: .init(
            viewDidLoadPublisher: Just(()).eraseToAnyPublisher(),
            tapNextButtonPublisher: footerView.tapNextButton.eraseToAnyPublisher(),
            tapColorIndexPublisher: colorSelectionView.tapColorSubject.map { $0.row }.eraseToAnyPublisher(),
            rotateIndexPublisher: rotatableView.currentImageIndexSubject.eraseToAnyPublisher()
        ))
        
        output.errorSubject
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] error in
                guard let self else { return }
                self.showAlert(with: NSAttributedString(string: error), acceptTitle: "확인", acceptHandler: {})
            }).store(in: &bag)
        
        output.unauthorizedSubject
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] in
                guard let self else { return }
                self.showAlert(with: NSAttributedString(string: "인증에 실패하였습니다."),
                               acceptTitle: "로그인",
                               acceptHandler: { [weak self] in
                    guard let self else { return }
                    self.navigationController?.popToRootViewController(animated: true)
                })
            }).store(in: &bag)
        
        output.colorImageSubject
            .receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] colors in
                guard let self else { return }
                colorSelectionView.refresh(by: colors)
            }).store(in: &bag)
        
        footerView.tapNextButton.sink(receiveValue: { [weak self] in
            guard let self else { return }
            navigationController?.pushViewController(InteriorColorSelctionViewController(), animated: true)
        }).store(in: &bag)
        
        Publishers.CombineLatest(output.colorNameSubject, output.tagsSubject).receive(on: DispatchQueue.main)
            .sink(receiveValue: { [weak self] (name, tags) in
                guard let self else { return }
                tagReviewView.refresh(by: tags, with: name)
            }).store(in: &bag)
        output.carImageSubject
            .sink(receiveValue: { [weak self] image in
                guard let self else { return }
                rotatableView.setImage(to: image)
            }).store(in: &bag)
    }
}
