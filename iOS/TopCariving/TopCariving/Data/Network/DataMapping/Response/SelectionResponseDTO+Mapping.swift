//
//  SelectionResponseDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct SelectionResponseDTO: Decodable {
    typealias Identifier = Int64
    let carOptionID: Identifier
    let optionName: String
    let price: Int32
    let photoURL: String
    let details: [SelectionDetailRequestDTO]
    let tags: [TagResponseDTO]
}

extension SelectionResponseDTO {
    func toDomain() -> Selection {
        return .init(
            id: carOptionID, 
            optionName: optionName,
            price: price,
            photoURL: photoURL,
            details: details.map { $0.toDomain() },
            tags: tags.map {$0.toDomain()})
    }
}
