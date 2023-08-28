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
    // swiftlint: enable identifier_name
        var kern: CGFloat {
            switch self {
            case ._26:
                return -0.52
            case ._24:
                return -0.48
            case ._20:
                return -0.4
            case ._18:
                return -0.36
            case ._16:
                return -0.32
            case ._14:
                return -0.28
            case ._12:
                return -0.24
            case ._10:
                return -0.2
            }
        }
        
        var lineHeight: CGFloat {
            switch self {
            case ._26:
                return 36
            case ._24:
                return 32
            case ._20:
                return 28
            case ._18:
                return 24
            case ._16:
                return 24
            case ._14:
                return 22
            case ._12:
                return 20
            case ._10:
                return 20
            }
        }
    }
    
    struct Custom {
        var name: Name
        var size: Size
        init(name: Name, size: Size) {
            self.name = name
            self.size = size
        }
    }
}
