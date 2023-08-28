//
//  TrimResponseDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct TrimResponseDTO: Decodable {
    typealias Identifier = Int64
    let carOptionID: Identifier
    let optionName: String
    let optionDetail: String
    let price: Int32
    let photoURL: String
}

extension TrimResponseDTO {
    func toDomain() -> Trim {
        return .init(
            id: carOptionID,
            optionName: optionName,
            optionDetail: optionDetail,
            price: price,
            photoURL: photoURL)
    }
}
