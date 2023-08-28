//
//  BasicOptionResponseDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct BasicOptionResponseDTO: Decodable {
    let categories: [String]
    let data: [TrimResponseDTO]
}

extension BasicOptionResponseDTO {
    func toDomain() -> BasicOption {
        return .init(
            categories: categories,
            data: data.map { $0.toDomain() })
    }
}
