//
//  ArchivingOptionResponseDTO.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct ArchivingOptionResponseDTO: Decodable {
    typealias Identifier = Int64
    let carOptionID: Identifier
    let optionName: String
    let price: Int32
}

extension ArchivingOptionResponseDTO {
    func toDomain() -> ArchivingOption {
        return .init(
            id: carOptionID,
            optionName: optionName,
            price: price)
    }
}
