//
//  TappableView.swift
//  TopCariving
//
//  Created by cho seungki on 2023/08/06.
//

import Combine
import UIKit

class TappableView: UIView {
    var tapSubject = PassthroughSubject<Any, Never>()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(tapped(sender:)))
        addGestureRecognizer(tapGesture)
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(tapped(sender:)))
        addGestureRecognizer(tapGesture)
    }
    
    @objc private func tapped(sender: Any) {
        tapSubject.send(self)
    }
}
