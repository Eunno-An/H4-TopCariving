//
//  ModifyOption.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct ModifyOption: Identifiable {
    typealias Identifier = Int64
    let id: Identifier
    let archivingResult: ArchiveResponseDTO
}
