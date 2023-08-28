//
//  ExteriorColorResponseDTO.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/24.
//

import Foundation

struct ExteriorColorResponseDTO: Decodable {
    let carOptionId: Int64
    let optionName: String
    let colorUrl: String
    let price: Int
    let tagResponses: [TagResponseDTO]
}

extension ExteriorColorResponseDTO {
    func toDomain() -> ExteriorColor {
        ExteriorColor(
            carOptionId: carOptionId,
            optionName: optionName,
            colorUrl: colorUrl,
            price: price,
            tagResponses: tagResponses.map { $0.toDomain() }
        )
    }
}
