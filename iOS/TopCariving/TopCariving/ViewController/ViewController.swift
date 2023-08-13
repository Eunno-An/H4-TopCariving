//
//  ViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/01.
//

import Combine
import UIKit

class ViewController: BaseMyCarViewController {
    private var asd: Set<AnyCancellable> = .init()
    let modalVC = IncludedBaseItemModalViewController()
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = .white
        let uiView = UIView(frame: .init(x: 100, y: 300, width: 200, height: 200))
        
        uiView.backgroundColor = .hyundaiBlackGray
        view.addSubview(uiView)
        uiView.tapPublisher().sink { _ in
            print("Hi")
            self.modalVC.modalPresentationStyle = .automatic
            self.present(self.modalVC, animated: true)
        }.store(in: &asd)
        
        NotificationCenter.default.addObserver(self, selector: #selector(presentSubCategoryModal), name: Notification.Name("SubCategoryCellTapped"), object: nil)
    }
    @objc func presentSubCategoryModal() {
        print("HI")
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            let modal = SubCategoryModalViewController()
            modal.modalPresentationStyle = .overFullScreen
            modal.modalTransitionStyle = .crossDissolve
            present(modal, animated: true)
        }
    }
}
