//
//  CombineControlTarget.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/10.
//

import Combine
import UIKit

extension Combine.Publishers {
    struct CustomControl<Control: AnyObject>: Publisher {
        // swiftlint: disable nesting
        typealias Output = Void
        typealias Failure = Never
        // swiftlint: enable nesting
        
        let control: Control
        let addTargetAction: (Control, AnyObject, Selector) -> Void
        let removeTargetAction: (Control?, AnyObject, Selector) -> Void
        
        init(
            control: Control,
            addTargetAction: @escaping (Control, AnyObject, Selector) -> Void,
            removeTargetAction: @escaping (Control?, AnyObject, Selector) -> Void) {
            self.control = control
            self.addTargetAction = addTargetAction
            self.removeTargetAction = removeTargetAction
        }
        
        func receive<S>(subscriber: S)
        where S: Subscriber,
              S.Failure == Failure,
              S.Input == Output {
            let subscription = Subscription(
                subscriber: subscriber,
                control: control,
                addTargetAction: addTargetAction,
                removeTargetAction: removeTargetAction)
            subscriber.receive(subscription: subscription)
        }
    }
}

@available(iOS 13.0, *)
extension Combine.Publishers.CustomControl {
    class Subscription<S: Subscriber, Control: AnyObject>: Combine.Subscription where S.Input == Void {
        private var subscriber: S?
        private var control: Control?
        private let removeTargetAction: (Control?, AnyObject, Selector) -> Void
        private let action = #selector(handleAction)
        
        public init(
            subscriber: S,
            control: Control,
            addTargetAction: @escaping (Control, AnyObject, Selector) -> Void,
            removeTargetAction: @escaping (Control?, AnyObject, Selector) -> Void) {
            self.subscriber = subscriber
            self.control = control
            self.removeTargetAction = removeTargetAction
            addTargetAction(control, self, action)
        }
        
        func request(_ demand: Subscribers.Demand) {
            
        }
        
        func cancel() {
            subscriber = nil
        }
        
        @objc func handleAction() {
            _ = subscriber?.receive()
        }
    }
}
