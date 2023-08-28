//
//  EngineResponseDTO+Mapping.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/23.
//

import Foundation

struct EngineResponseDTO: Decodable {
    let carOptionId: Int64
    let optionName: String
    let optionDetail: String
    let price: Int32
    let maxOutput: String
    let maxTorque: String
    let photoUrl: String
}
extension EngineResponseDTO {
    func toDomain() -> Engine {
        Engine(
            carOptionId: carOptionId,
            optionName: optionName,
            optionDetail: optionDetail,
            price: price,
            maxOutput: maxOutput,
            maxTorque: maxTorque,
            photoUrl: photoUrl
        )
    }
}
