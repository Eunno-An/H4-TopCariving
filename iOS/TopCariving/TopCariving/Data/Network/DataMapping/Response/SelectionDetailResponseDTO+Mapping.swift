//
//  SelectionDetailRequestDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct SelectionDetailRequestDTO: Decodable {
    typealias Identifier = Int64
    let carOptionID: Identifier
    let optionName: String
    let optionDetail: String
}

extension SelectionDetailRequestDTO {
    func toDomain() -> SelectionDetail {
        return .init(
            id: carOptionID,
            optionName: optionName,
            optionDetail: optionDetail)
    }
}
