//
//  ArchiveResponseDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct ArchiveResponseDTO: Decodable {
    typealias Identifier = Int64
    let options: [SearchOptionDTO]
    let archiveSearchResponses: [ArchiveFeedDTO]
}

extension ArchiveResponseDTO {
    func toDomain() -> Archiving {
        return .init(
            options: options.map {$0.toDomain()},
            archiveSearchResponses: archiveSearchResponses.map {$0.toDomain()}
        )
    }
}
