//
//  String+.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/14.
//

import Foundation

extension String {
    static func decimalStyle(from number: Int) -> String {
        let numberFormatter = NumberFormatter()
        numberFormatter.numberStyle = .decimal
        return numberFormatter.string(from: NSNumber(value: number)) ?? ""
    }
}
