//
//  OptionRequestDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct OptionRequestDTO: Encodable {
    typealias Identifier = Int64
    let ids: [Identifier]
}
