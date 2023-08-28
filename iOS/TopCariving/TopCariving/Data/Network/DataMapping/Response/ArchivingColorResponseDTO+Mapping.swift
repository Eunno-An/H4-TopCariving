//
//  ArchivingColorResponseDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct ArchivingColorDTO: Decodable {
    typealias Identifier = Int64
    let carOptionID: Identifier
    let optionName: String
    let price: Int32
    let photoURL: String
}

extension ArchivingColorDTO {
    func toDomain() -> ArchivingColor {
        return .init(
            id: carOptionID,
            optionName: optionName,
            price: price,
            photoURL: photoURL)
    }
}
