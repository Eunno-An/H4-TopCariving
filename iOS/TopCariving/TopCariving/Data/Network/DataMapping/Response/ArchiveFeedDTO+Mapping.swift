//
//  ArchiveFeedDTO+Mapping.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/25.
//

import Foundation

struct ArchiveFeedDTO: Decodable {
    typealias Identifier = Int64
    let archivingId: Identifier
    let carArchiveResult: [String: [String]]
    let dayTime: String
    let carReview: String
    let tags: [TagResponseDTO]
    let type: String
}
extension ArchiveFeedDTO {
    func toDomain() -> ArchiveFeed {
        return .init(id: archivingId, carArchiveResult: carArchiveResult, dayTime: dayTime, carReview: carReview, tags: tags.map {$0.toDomain()}, type: type)
    }
}
