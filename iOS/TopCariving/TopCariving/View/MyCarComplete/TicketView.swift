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
    override func layoutSubviews() {
        super.layoutSubviews()
        drawTicket()
    }
    // MARK: - Helpers
    private func setUI() {
        backgroundColor = .hyundaiGold
        layer.borderWidth = 1
        layer.borderColor = UIColor.hyundaiGold.cgColor
    }
    private func drawTicket() {
        let ticketShapeLayer = CAShapeLayer()
        ticketShapeLayer.frame = self.bounds
        
        let ticketShapePath = UIBezierPath(roundedRect: ticketShapeLayer.bounds, cornerRadius: 8)
        let topLeftArcPath = UIBezierPath(arcCenter: CGPoint(x: 0, y: 100), radius: 30/2, startAngle: CGFloat(Double.pi / 2), endAngle: CGFloat(Double.pi + Double.pi / 2), clockwise: false)
        topLeftArcPath.close()
        
        let topRightArcPath = UIBezierPath(arcCenter: CGPoint(x: ticketShapeLayer.frame.width, y: 100), radius: 30/2, startAngle: CGFloat(Double.pi / 2), endAngle: CGFloat(Double.pi + Double.pi / 2), clockwise: true)
        topRightArcPath.close()
        
        ticketShapePath.append(topLeftArcPath)
        ticketShapePath.append(topRightArcPath.reversing())
        
        ticketShapeLayer.path = ticketShapePath.cgPath
        
        layer.addSublayer(ticketShapeLayer)
    }
}
