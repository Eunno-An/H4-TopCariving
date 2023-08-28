//
//  UIImage+.swift
//  TopCariving
//
//  Created by cho seungki on 2023/08/05.
//

import UIKit

extension UIImage {
    func resized(to size: CGSize) -> UIImage {
        UIGraphicsImageRenderer(size: size).image { _ in
            draw(in: CGRect(origin: .zero, size: size))
        }
    }
    func resized(to width: CGFloat) -> UIImage {
        let ratio = width / size.width
        let size = CGSize(width: width, height: size.height * ratio)
        
        return UIGraphicsImageRenderer(size: size).image { _ in
            draw(in: CGRect(origin: .zero, size: size))
        }
    }
    static func imageService(url: String) async -> UIImage? {
        await ImageService.shared.loadImage(with: url)
    }
}
