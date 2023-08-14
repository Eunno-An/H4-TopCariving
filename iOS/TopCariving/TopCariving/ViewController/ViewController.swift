//
//  ViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/01.
//

import Combine
import UIKit

class ViewController: BaseMyCarViewController {
    private var asd = Set<AnyCancellable>()
    let modalVC = IncludedBaseItemModalViewController()
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = .white
        let uiView = UIView(frame: .init(x: 100, y: 300, width: 200, height: 200))
        
        uiView.backgroundColor = .hyundaiBlackGray
        view.addSubview(uiView)
        uiView.tapPublisher().sink { _ in
            self.modalVC.modalPresentationStyle = .automatic
            self.present(self.modalVC, animated: true)
        }.store(in: &asd)
    }
}
