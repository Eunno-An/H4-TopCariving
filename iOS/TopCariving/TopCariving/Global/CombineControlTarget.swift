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
            addTargetAction: @escaping (AnyObject, AnyObject, Selector) -> Void,
            removeTargetAction: @escaping (AnyObject?, AnyObject, Selector) -> Void) {
            self.control = control
            self.addTargetAction = addTargetAction
            self.removeTargetAction = removeTargetAction
        }
        
        func receive<S>(subscriber: S)
        where S: Subscriber,
        Never == S.Failure,
        Void == S.Input {
            let subscription = Subscription(
                subscriber: subscriber,
                control: control,
                addTargetAction: addTargetAction,
                removeTargetAction: removeTargetAction)
        }
    }
}

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
