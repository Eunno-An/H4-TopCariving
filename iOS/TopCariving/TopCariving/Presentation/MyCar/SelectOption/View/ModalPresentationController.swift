//
//  ModalPresentationController.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/13.
//

import UIKit

class ModalPresentationController: UIPresentationController {
    override var frameOfPresentedViewInContainerView: CGRect {
        guard let containerView = containerView else { return CGRect.zero }
        containerView.backgroundColor = UIColor(cgColor: .init(red: 36/255, green: 36/255, blue: 36/255, alpha: 0.6))
        let modalSize = CGSize(width: containerView.bounds.width * 0.89, height: containerView.bounds.height * 0.387)
        let modalOrigin = CGPoint(
            x: (containerView.bounds.width - modalSize.width) / 2,
            y: (containerView.bounds.height - modalSize.height) / 2
        )
        return CGRect(origin: modalOrigin, size: modalSize)
    }
}
