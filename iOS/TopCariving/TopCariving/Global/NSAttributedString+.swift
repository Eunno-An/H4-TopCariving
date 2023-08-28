//
//  NSAttributedString+.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/17.
//

import UIKit

extension NSAttributedString {
    static func makeBold(
        _ text: String,
        boldText: String,
        font: HyundaiFont.Custom,
        boldFont: HyundaiFont.Custom
    ) -> NSAttributedString {
        let attributedString = NSMutableAttributedString(string: text, attributes: [.font: UIFont.designSystem(font)])
        attributedString.addAttribute(.font,
                                      value: UIFont.designSystem(boldFont),
                                      range: (text as NSString).range(of: boldText))
        
        return attributedString
    }
}
