//
//  ColorBothResponseDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct ColorBothResponseDTO: Decodable {
    let exteriorColorResponses: [ColorResponseDTO]
    let interiorColorResponses: [ColorResponseDTO]
}

extension ColorBothResponseDTO {
    func toDomain() -> ColorBoth {
        return .init(
            exteriorColors: exteriorColorResponses.map { $0.toDomain() },
            interiorColors: interiorColorResponses.map { $0.toDomain() })
    }
}
