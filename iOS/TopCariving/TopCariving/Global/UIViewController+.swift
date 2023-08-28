//
//  UIViewController+.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/17.
//

import UIKit

extension UIViewController {
    func showAlert(
        with attributedString: NSAttributedString,
        acceptTitle: String,
        acceptHandler: @escaping () -> Void
    ) {
        let alertViewController = TCAlertViewController()
        alertViewController.setUp(
            with: attributedString,
            acceptTitle: acceptTitle,
            acceptHandler: acceptHandler
        )

        alertViewController.modalPresentationStyle = .overFullScreen
        self.present(alertViewController, animated: false)
    }
}
