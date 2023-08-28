//
//  UILabel+.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/08.
//

import UIKit

extension UILabel {
    func setFont(to font: HyundaiFont.Custom) {
        self.font = .designSystem(font)
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.alignment = .center
        paragraphStyle.lineHeightMultiple = font.size.lineHeight / font.size.rawValue
        attributedText = NSAttributedString(string: text ?? "",
                                            attributes: [NSAttributedString.Key.kern: 0,
                                                         NSAttributedString.Key.paragraphStyle: paragraphStyle])
    }
}
