//
//  UIFont+.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/02.
//

import UIKit

extension UIFont {
    static func designSystem(_ hyundaiFont: HyundaiFont.Custom) -> UIFont {
        .init(name: hyundaiFont.name.rawValue, size: hyundaiFont.size.rawValue) ?? .systemFont(ofSize: 100)
    }
}
