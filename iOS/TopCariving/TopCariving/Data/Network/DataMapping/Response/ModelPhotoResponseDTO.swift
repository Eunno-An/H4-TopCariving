//
//  ModelPhotoResponseDTO.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/07.
//

import Foundation

struct ModelPhotoResponseDTO: Decodable {
    let content: String
    let photoSvgUrl: String
    let photoPngUrl: String
}

extension ModelPhotoResponseDTO {
    func toDomain() -> ModelPhoto {
        return .init(
            content: content,
            photoSVGURL: photoSvgUrl,
            photoPNGURL: photoPngUrl)
    }
}
