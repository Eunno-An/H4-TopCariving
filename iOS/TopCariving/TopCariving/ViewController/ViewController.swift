//
//  ViewController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/01.
//

import Combine
import UIKit

class ViewController: BaseMyCarViewController {
    var bag: Set<AnyCancellable> = .init()
    var myView: UIView = {
        var view: UIView = UIView(frame: .init(x: 100, y: 150, width: 200, height: 200))
        view.backgroundColor = .red
        return view
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .white
        
        view.addSubview(myView)
        myView.tapPublisher().sink { _ in
            print("Hi 배남석")
        }.store(in: &bag)
        
    }
}
