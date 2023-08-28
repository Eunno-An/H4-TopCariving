//
//  CTAButton.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/08.
//

import UIKit

class CTAButton: UIButton {
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
        translatesAutoresizingMaskIntoConstraints = false
        backgroundColor = .hyundaiPrimaryBlue
        layer.cornerRadius = 8
    
        setTitleColor(.hyundaiLightSand, for: .normal)
        titleLabel?.font = UIFont.designSystem(.init(name: .bold, size: ._18))
    }
   
    func setTitle(to text: String) {
        setTitle(text, for: .normal)
    }
}
