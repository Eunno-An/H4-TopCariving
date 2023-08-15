//
//  BaseOptionSubCategoryModel.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/15.
//

import Foundation

struct BaseOptionSubCategoryModel {
    private(set) var imageURL: String
    private(set) var title: String
    private(set) var optionInfo: String
    init(imageURL: String, title: String, optionInfo: String) {
        self.imageURL = imageURL
        self.title = title
        self.optionInfo = optionInfo
    }
}
