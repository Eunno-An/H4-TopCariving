//
//  CarArchiving.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/04.
//

import Foundation

struct CarArchiving: Identifiable {
    typealias Identifier = Int64
    
    let id: Identifier
    let userID: Identifier
    let dayTime: Date
    let isComplete: Bool
    let isAlive: Bool
}
