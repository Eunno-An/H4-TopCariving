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
    
    // MARK: - Helpers
    func drawTicket(holeYPosition: CGFloat) {
        let ticketShapeLayer = CAShapeLayer()
        ticketShapeLayer.frame = self.bounds
        ticketShapeLayer.fillColor = UIColor.hyundaiLightSand.cgColor
        let ticketShapePath = UIBezierPath(roundedRect: ticketShapeLayer.bounds, cornerRadius: 8)
        let topLeftArcPath = UIBezierPath(
            arcCenter: CGPoint(x: 0, y: holeYPosition),
            radius: 30/2,
            startAngle: CGFloat(Double.pi / 2),
            endAngle: CGFloat(Double.pi + Double.pi / 2),
            clockwise: false
        )
        topLeftArcPath.close()
        
        let topRightArcPath = UIBezierPath(
            arcCenter: CGPoint(x: ticketShapeLayer.frame.width, y: holeYPosition),
            radius: 30/2,
            startAngle: CGFloat(Double.pi / 2),
            endAngle: CGFloat(Double.pi + Double.pi / 2),
            clockwise: true
        )
        topRightArcPath.close()
        
        ticketShapePath.append(topLeftArcPath)
        ticketShapePath.append(topRightArcPath.reversing())
        
        ticketShapeLayer.path = ticketShapePath.cgPath
        layer.addSublayer(ticketShapeLayer)
    }
}
