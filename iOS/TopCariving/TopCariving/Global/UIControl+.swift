//
//  UIControl+.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import UIKit

extension UIControl {
    func attachPublisher(for event: UIControl.Event) -> AnyPublisher<Void, Never> {
        Publishers.CustomEvent(control: self, controlEvent: event).eraseToAnyPublisher()
    }
}
