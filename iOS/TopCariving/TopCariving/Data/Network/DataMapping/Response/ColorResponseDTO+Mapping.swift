//
//  ColorResponseDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct ColorResponseDTO: Decodable {
    typealias Identifier = Int64
    let carOptionID: Identifier
    let optionName: String
    let photoURL: String
    let price: Int32
    let tagResponses: [TagResponseDTO]
}

extension ColorResponseDTO {
    func toDomain() -> Color {
        return .init(
            id: carOptionID,
            optionName: optionName,
            photoURL: photoURL,
            price: price,
            tagResponses: tagResponses.map {$0.toDomain()})
    }
}
