//
//  BaseOptonMainCategoryModel.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/15.
//

import Foundation

struct BaseOptionMainCategoryModel {
    private(set) var title: String
    private(set) var subCategories: [BaseOptionSubCategoryModel]
    init(title: String, subCategories: [BaseOptionSubCategoryModel]) {
        self.title = title
        self.subCategories = subCategories
    }
}
