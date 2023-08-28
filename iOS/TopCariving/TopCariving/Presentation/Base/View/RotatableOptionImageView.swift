//
//  RotatableOptionImageView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/08.
//

import Combine
import UIKit

class RotatableOptionImageView: UIView {
    // MARK: - UI properties
    private let imageView: UIImageView = UIImageView(image: UIImage(named: "image_001"))
    private var degreeView: DegreeView = DegreeView()
    private var elipseLayer: CAShapeLayer = CAShapeLayer()
    
    // MARK: - Properties
    private var imageNumber = 0
    private var previousImageNumber = 0
    private var currentRotationAngle: CGFloat = 0
    private var previousRotationAngle: CGFloat = 0
    
    var currentImageIndexSubject = CurrentValueSubject<Int, Never>(0)
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
        setImage(to: "https://d37c20hjk1kqzz.cloudfront.net/360/abyss/image_001.png")
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
    }
    override func layoutSublayers(of layer: CALayer) {
        super.layoutSublayers(of: layer)
        setElipseLayer()
        layer.insertSublayer(elipseLayer, at: 0)
    }
    
    // MARK: - Helpers
    private func setUI() {
        translatesAutoresizingMaskIntoConstraints = false
        imageView.translatesAutoresizingMaskIntoConstraints = false
        degreeView.translatesAutoresizingMaskIntoConstraints = false
        
        isUserInteractionEnabled = true
        
        let panGesture = UIPanGestureRecognizer(target: self, action: #selector(handlePan))
        addGestureRecognizer(panGesture)
        
        imageView.contentMode = .scaleAspectFit
        
        addSubview(imageView)
        addSubview(degreeView)
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            imageView.topAnchor.constraint(equalTo: topAnchor),
            imageView.bottomAnchor.constraint(equalTo: bottomAnchor),
            imageView.trailingAnchor.constraint(equalTo: trailingAnchor),
            imageView.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            degreeView.widthAnchor.constraint(equalTo: widthAnchor, multiplier: 0.442),
            degreeView.heightAnchor.constraint(equalTo: heightAnchor, multiplier: 0.201),
            degreeView.centerXAnchor.constraint(equalTo: centerXAnchor),
            degreeView.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
    }
    private func setElipseLayer() {
        let ovalRect = CGRect(
            x: (layer.bounds.width - (layer.bounds.width * 0.904))/2,
            y: layer.bounds.height * 0.7,
            width: layer.bounds.width * 0.904,
            height: layer.bounds.height * 0.178)
        let ovalPath = UIBezierPath(ovalIn: ovalRect)
        
        elipseLayer = CAShapeLayer()
        elipseLayer.path = ovalPath.cgPath
        elipseLayer.strokeColor = UIColor.hyundaiSand.cgColor
        elipseLayer.fillColor = UIColor.hyundaiLightSand.cgColor
        elipseLayer.lineWidth = 1.0
    }
    @objc private func handlePan(sender: UIPanGestureRecognizer) {
        let translation = sender.translation(in: self)
        let progress = translation.x / self.bounds.width
        
        switch sender.state {
        case .began:
            previousImageNumber = imageNumber
            previousRotationAngle = currentRotationAngle
        case .changed:
            currentRotationAngle = previousRotationAngle + 2 * .pi * progress
            imageNumber = previousImageNumber - Int(round(currentRotationAngle / (2 * .pi / 60)))
            
            imageNumber %= 60
            
            if imageNumber < 0 {
                imageNumber += 59
            }
            
            currentImageIndexSubject.send(imageNumber)
        case .ended:
            currentRotationAngle = 0
        default:
            break
        }
    }
    func setImage(to image: String) {
        imageView.setAsyncImage(url: image)
    }
}
