//
//  CombineControlEvent.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import Foundation
import UIKit.UIControl

extension Combine.Publishers {
    struct CustomEvent<Control: UIControl>: Publisher {
        // swiftlint: disable nesting
        typealias Output = Void
        typealias Failure = Never
        // swiftlint: enable nesting
        
        private let control: Control
        private let controlEvent: Control.Event
        
        public init(control: Control, controlEvent: Control.Event) {
            self.control = control
            self.controlEvent = controlEvent
        }
        
        func receive<S>(subscriber: S) where S: Subscriber, Never == S.Failure, Void == S.Input {
            let subscription = Subscription(subscriber: subscriber, control: control, event: controlEvent)
            subscriber.receive(subscription: subscription)
        }
    }
}

extension Combine.Publishers.CustomEvent {
    final class Subscription<S: Subscriber, Control: UIControl>: Combine.Subscription where S.Input == Void {
        private var subscriber: S?
        weak private var control: Control?
        
        init(subscriber: S, control: Control, event: Control.Event) {
            self.subscriber = subscriber
            self.control = control
            control.addTarget(self, action: #selector(action), for: event)
        }
        
        func request(_ demand: Subscribers.Demand) {
            
        }
        
        func cancel() {
            subscriber = nil
        }
        
        @objc func action() {
            _ = subscriber?.receive()
        }
    }
}
