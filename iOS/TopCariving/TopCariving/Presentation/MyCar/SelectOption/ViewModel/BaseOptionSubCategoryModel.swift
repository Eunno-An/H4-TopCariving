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
    init(
        imageURL: String = "baseOptionSample",
        title: String = "ISG시스템",
        optionInfo: String = "신호 대기 상황이거나 정차 중일 때 차의 엔진을 일시 정지하여 연비를 향상시키고, 배출가스 발생을 억제하는 시스템입니다."
    ) {
        self.imageURL = imageURL
        self.title = title
        self.optionInfo = optionInfo
    }
}
