//
//  CarOption.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/04.
//

struct CarOption: Identifiable {
    typealias Identifier = Int64

    let id: Identifier
    let category: String?
    let categoryDetail: String?
    let optionName: String?
    let optionDetail: String?
    let price: Int
    let photoUrl: String?
    let parentOptionId: Identifier
}
