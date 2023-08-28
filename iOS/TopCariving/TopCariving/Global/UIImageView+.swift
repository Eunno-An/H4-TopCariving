//
//  UIImageView+.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/26.
//

import UIKit

extension UIImageView {
    func setAsyncImage(url: String, size: CGSize? = nil) {
        Task {
            let image = await UIImage.imageService(url: url)?.resized(to: size ?? frame.size)
            if let image = image {
                setImage(to: image)
            } else {
                setImage(to: UIImage(systemName: "questionmark.app")?.resized(to: size ?? frame.size))
            }
        }
    }
    @MainActor private func setImage(to image: UIImage?) {
        self.image = image
    }
}
