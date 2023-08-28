//
//  MockImageService.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/18.
//

import UIKit

class ImageService {
    static let shared = ImageService()
    private let cache = ImageCacheService.shared
    
    private init() { }
    
    func loadImage(with stringURL: String) async -> UIImage? {
        if let image = cache.loadImage(key: stringURL) {
            return image
        }
        
        guard let url = URL(string: stringURL),
              let response = try? await URLSession.shared.downloadTask(with: url),
              let data = try? Data(contentsOf: response.0),
              let image = UIImage(data: data) else { return nil }
        
        self.cache.saveImage(key: stringURL, image: image)
        return image
    }

    func prefetchImage(stringURL: String) {
        guard cache.loadImage(key: stringURL) == nil else { return }
        guard let url = URL(string: stringURL) else { return }
        
        let task = URLSession.shared.downloadTask(with: url) { localURL, _, error in
            guard error == nil,
                  let localURL = localURL,
                  let data = try? Data(contentsOf: localURL),
                  let image = UIImage(data: data) else { return }
            
            self.cache.saveImage(key: stringURL, image: image)
        }
        task.resume()
    }
}
