//
//  CombineGestureEvent.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/11.
//

import Combine
import UIKit

extension Combine.Publishers {
    struct CustomTarget<Control: AnyObject>: Publisher {
        typealias Output = Void
        typealias Failure = Never
        
        let control: Control
        let addTargetAction: (Control, AnyObject, Selector) -> Void
        let removeTargetAction: (Control, AnyObject, Selector) -> Void
        
        init(control: Control, addTargetAction: @escaping (Control, AnyObject, Selector) -> Void, removeTargetAction: @escaping (Control, AnyObject, Selector) -> Void) {
            self.control = control
            self.addTargetAction = addTargetAction
            self.removeTargetAction = removeTargetAction
        }
        
        func receive<S>(subscriber: S) where S: Subscriber, S.Failure == Failure, S.Input == Output {
            let subscription = Subscription(
                subscriber: subscriber,
                control: control,
                addTargetAction: addTargetAction,
                removeTargetAction: removeTargetAction)
            subscriber.receive(subscription: subscription)
        }
    }
}

extension Combine.Publishers.CustomTarget {
    class Subscription<S: Subscriber, Control: AnyObject>: Combine.Subscription where S.Input == Void {
        private var subscriber: S?
        weak private var control: Control?
        
        let removeTargetAction: (Control, AnyObject, Selector) -> Void
        let action = #selector(handleAction)
        
        init(subscriber: S, control: Control, addTargetAction: @escaping (Control, AnyObject, Selector) -> Void, removeTargetAction: @escaping (Control, AnyObject, Selector) -> Void) {
            self.subscriber = subscriber
            self.control = control
            self.removeTargetAction = removeTargetAction
            addTargetAction(control, self, action)
        }
        
        func request(_ demand: Subscribers.Demand) {
            
        }
        
        func cancel() {
            subscriber = nil
            removeTargetAction(control!, self, action)
        }
        
        @objc func handleAction() {
            _ = subscriber?.receive()
        }
    }
}
