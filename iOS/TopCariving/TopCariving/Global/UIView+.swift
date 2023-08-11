//
//  UIView+.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/11.
//

import Combine
import UIKit

extension UIView {
    var tabPublisher: AnyPublisher<UITapGestureRecognizer, Never> {
        UITapGestureRecognizer().tapPublisher
    }
}
