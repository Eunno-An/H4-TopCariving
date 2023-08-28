//
//  SelectOptionRequestDTO.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/23.
//

import Foundation

struct SelectOptionRequestDTO: Encodable {
    typealias Identifier = Int64
    let carOptionId: Identifier
    let archivingId: Identifier?
}
