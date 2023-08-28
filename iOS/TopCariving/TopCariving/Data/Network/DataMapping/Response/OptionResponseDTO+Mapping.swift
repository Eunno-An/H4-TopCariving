//
//  OptionResponseDTO.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/24.
//

import Foundation

struct OptionResponseDTO: Decodable {
    let carOptionId: Int64
    let optionName: String
    let optionDetail: String
    let price: Int
    let photoUrl: String
}

extension OptionResponseDTO {
    func toDomain() -> OptionResponse {
        .init(carOptionId: carOptionId,
              optionName: optionName,
              optionDetail: optionDetail,
              price: price,
              photoUrl: photoUrl)
    }
}
