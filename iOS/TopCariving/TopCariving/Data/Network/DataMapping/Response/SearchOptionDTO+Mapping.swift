//
//  SearchOptionDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/25.
//

import Foundation

struct SearchOptionDTO: Decodable {
    typealias Identifier = Int64
    let carOptionId: Identifier
    let optionName: String
}
extension SearchOptionDTO {
    func toDomain() -> SearchOption {
        return .init(id: carOptionId, optionName: optionName)
    }
}
