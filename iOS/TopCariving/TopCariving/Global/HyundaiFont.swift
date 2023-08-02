//
//  HundaiFont.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/02.
//

import Foundation

enum HyundaiFont {
    enum Name: String {
        case light = "HyundaiSansHeadKROTFLight"
        case regular = "HyundaiSansHeadKROTFRegular"
        case medium = "HyundaiSansHeadKROTFMedium"
        case bold = "HyundaiSansHeadKROTFBold"
    }
    
    // swiftlint: disable identifier_name
    enum Size: CGFloat {
        case _26 = 26
        case _24 = 24
        case _20 = 20
        case _18 = 18
        case _16 = 16
        case _14 = 14
        case _12 = 12
        case _10 = 10
    }
    // swiftlint: enable identifier_name
    
    struct Custom {
        var name: String
        var size: CGFloat
        init(name: Name, size: Size) {
            self.name = name.rawValue
            self.size = size.rawValue
        }
    }
}
