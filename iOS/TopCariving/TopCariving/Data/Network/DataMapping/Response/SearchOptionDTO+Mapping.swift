//
//  SearchOptionDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/25.
//

import Foundation

struct SearchOptionDTO: Decodable {
    typealias identifier = Int64
    let carOptionID: identifier
    let optionName: String
}
extension SearchOptionDTO {
    func toDomain() -> SearchOption {
        return .init(id: carOptionID, optionName: optionName)
    }
}

