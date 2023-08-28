//
//  ModifyOptionResponseDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct ModifyOptionResponseDTO: Decodable {
    typealias Identifier = Int64
    let carOptionID: Identifier
    let archivingResult: ArchiveResponseDTO
}

extension ModifyOptionResponseDTO {
    func toDomain() -> ModifyOption {
        return .init(
            id: carOptionID,
            archivingResult: archivingResult)
    }
}
