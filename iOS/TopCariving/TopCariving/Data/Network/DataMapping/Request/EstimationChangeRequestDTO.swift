//
//  EstimationChangeRequestDTO.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct EstimationChangeRequestDTO: Encodable {
    typealias Identifier = Int64
    let ids: [Identifier]
    let archivingId: Identifier
}
