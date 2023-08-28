//
//  ArchivingColor.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct ArchivingColor: Identifiable {
    typealias Identifier = Int64
    let id: Identifier
    let optionName: String
    let price: Int32
    let photoURL: String
}
