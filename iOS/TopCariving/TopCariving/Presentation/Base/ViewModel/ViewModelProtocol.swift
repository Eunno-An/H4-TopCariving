//
//  DummyViewModel.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/01.
//

import Foundation

protocol ViewModelType {
    associatedtype Input
    associatedtype Output
    
    func transform(input: Input) -> Output
}
