//
//  Trim.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct Trim: Identifiable {
    typealias Identifier = Int64
    let id: Identifier
    let optionName: String
    let optionDetail: String
    let price: Int32
    let photoURL: String
}
