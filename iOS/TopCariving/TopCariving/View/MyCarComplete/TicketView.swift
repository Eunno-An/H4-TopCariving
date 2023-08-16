//
//  TicketView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/17.
//

import UIKit

class TicketView: UIView {
    // MARK: - UI properties
    
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
    }
    // MARK: - Helpers
    private func setUI() {
        backgroundColor = .hyundaiGold
        layer.borderWidth = 1
        layer.borderColor = UIColor.hyundaiGold.cgColor
    }
}
