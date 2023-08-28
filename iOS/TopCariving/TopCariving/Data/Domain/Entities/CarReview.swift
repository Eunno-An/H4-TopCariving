//
//  CarReview.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/04.
//

struct CarReview: Identifiable {
    typealias Identifier = Int64
    
    let id: Identifier
    let review: String?
    let myCarID: Identifier
}
