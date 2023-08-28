//
//  ModelPhoto.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/04.
//

struct ModelPhoto: Identifiable {
    typealias Identifier = Int64
    
    let id: Identifier
    let content: String
    let photoSVGURL: String
    let photoPNGURL: String
    let carOptionID: Identifier
}
