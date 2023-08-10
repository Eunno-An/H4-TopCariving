//
//  UIGestureRecognizer+.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import UIKit

@available(iOS 13.0, *)
private func gesturePublisher<Gesture: UIGestureRecognizer>(for gesture: Gesture) -> AnyPublisher<Gesture, Never> {
    Publishers.CustomControl(
        control: gesture,
        addTargetAction: { gesture, target, action in
            gesture.addTarget(target, action: action)
        },
        removeTargetAction: { gesture, target, action in
            gesture?.addTarget(target, action: action)
        })
    .subscribe(on: DispatchQueue.main)
    .map { gesture }
    .eraseToAnyPublisher()
}
