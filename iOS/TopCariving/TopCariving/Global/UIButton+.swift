//
//  UIButton+.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import UIKit

@available(iOS 13.0, *)
extension UIButton {
    var touchUpPublisher: AnyPublisher<Void, Never> {
        attachPublisher(for: .touchUpInside)
    }
}
