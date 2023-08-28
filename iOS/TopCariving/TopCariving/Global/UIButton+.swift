//
//  UIButton+.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import UIKit

extension UIButton {
    var touchUpPublisher: AnyPublisher<Void, Never> {
        attachPublisher(for: .touchUpInside)
    }
}
