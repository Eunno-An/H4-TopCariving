//
//  CombineControlEvent.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import Foundation
import UIKit.UIControl

@available(iOS 13.0, *)
extension Combine.Publishers {
    struct CustomEvent<Control: UIControl>: Publisher {
        typealias Output = Void
        typealias Failure = Never
        
        private let control: Control
        private let controlEvent: Control.Event
        
        public init(control: Control, controlEvent: Control.Event) {
            self.control = control
            self.controlEvent = controlEvent
        }
        
        func receive<S>(subscriber: S) where S : Subscriber, Never == S.Failure, Void == S.Input {
            #warning("subscription추가")
        }
    }
}

