//
//  UIGestureRecognizer+.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import UIKit

extension UITapGestureRecognizer {
    var tapPublisher: AnyPublisher<UITapGestureRecognizer, Never> {
        gesturePublisher(for: self)
    }
}

private func gesturePublisher<Gesture: UIGestureRecognizer>(for gesture: Gesture) -> AnyPublisher<Gesture, Never> {
    Publishers.CustomTarget(
        control: gesture,
        addTargetAction: { gesture, target, action in
            gesture.addTarget(target, action: action)},
        removeTargetAction: { gesture, target, action in
            gesture.removeTarget(target, action: action)
        })
    .subscribe(on: DispatchQueue.main)
    .map { gesture }
    .eraseToAnyPublisher()
}
