//
//  UIView+.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import UIKit

extension UIView {
  func throttleTapGesturePublisher() ->
    Publishers.Throttle<UITapGestureRecognizer.GesturePublisher<UITapGestureRecognizer>, RunLoop> {
    return UITapGestureRecognizer.GesturePublisher(recognizer: .init(), view: self)
      .throttle(
        for: .seconds(0.2),
        scheduler: RunLoop.main,
        latest: false
      )
  }
}
