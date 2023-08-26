//
//  ArchiveFeed.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/25.
//

import Foundation

struct ArchiveFeed: Identifiable {
    typealias Identifier = Int64
    let id: Identifier
    let carArchiveResult: [String: [String]]
    let dayTime: String
    let carReview: String
    let tags: [Tag]
    let type: String
}
