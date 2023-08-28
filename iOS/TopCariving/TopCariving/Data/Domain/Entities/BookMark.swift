//
//  BookMark.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/04.
//

struct BookMark: Identifiable {
    typealias Identifier = Int64
    
    let id: Identifier
    let userID: Identifier
    let archivingID: Identifier
    let isAlive: Bool
}
