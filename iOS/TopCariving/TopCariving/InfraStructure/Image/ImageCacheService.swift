//
//  ImageCacheService.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/26.
//

import UIKit

class ImageCacheService {
    static let shared = ImageCacheService()
    
    private let memoryCache = NSCache<NSString, UIImage>()
    private let fileManager = FileManager.default
    private let pathURL: URL
    
    private init() {
        let directory = fileManager.urls(for: .cachesDirectory, in: .userDomainMask).first!
        pathURL = directory.appendingPathComponent("ImageCache")
        try? fileManager.createDirectory(at: pathURL, withIntermediateDirectories: true, attributes: nil)
        memoryCache.totalCostLimit = 100 * 1024 * 1024
    }
    func loadImage(key: String) -> UIImage? {
        if let image = memoryCache.object(forKey: NSString(string: key)) {
            return image
        }
        guard let url = URL(string: key) else { return nil }
        
        let path = url.pathComponents.joined(separator: "")
        
        guard let data = try? Data(contentsOf: pathURL.appendingPathComponent(path)),
              let image = UIImage(data: data) else { return nil }
        
        memoryCache.setObject(image, forKey: NSString(string: key))
        return image
    }
    func saveImage(key: String, image: UIImage) {
        memoryCache.setObject(image, forKey: NSString(string: key))
        
        guard let url = URL(string: key) else { return }
        let path = url.pathComponents.joined(separator: "")
        let fileURL = pathURL.appendingPathComponent(path)
        
        if let data = image.pngData() {
            try? data.write(to: fileURL)
        }
    }
}
