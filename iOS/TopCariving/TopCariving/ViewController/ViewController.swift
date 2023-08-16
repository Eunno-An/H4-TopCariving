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
        uiView.tabPublisher.sink { _ in
            self.modalVC.modalPresentationStyle = .automatic
            self.present(self.modalVC, animated: true)
        }.store(in: &asd)
    }
    
    // data는 OptionSelectViewDataSource를 위한 데이터 소스 입니다.
    var data = (0..<6).map { OptionCardViewModel.init(image: "TopArchivingButton", name: "컴포트 \($0)", price: 400000, isAdded: false) }
}

extension ViewController: OptionSelectViewDataSource {
    func numberOfOption(_ optionSelectView: OptionSelectView) -> Int {
        5
    }
    func optionSelectViewModel(_ optionSelectView: OptionSelectView, at indexPath: IndexPath) -> OptionCardViewModel {
        data[indexPath.row]
    }
    
}
