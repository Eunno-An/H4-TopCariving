//
//  EstimationChange.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct EstimationChange: Identifiable {
    typealias Identifier = Int64
    let id: Identifier
    let optionIDs: [Identifier]
}
