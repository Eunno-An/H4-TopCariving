//
//  ProgressView.swift
//  TopCariving
//
//  Created by 조승기 on 2023/08/18.
//

import UIKit

class ProgressView: UIImageView {
    enum Progress {
        case trim
        case engine
        case bodyType
        case drivingMethod
        case exteriorColor
        case interiorColor
        case selectOptions
        case hGenuineAccessories
        case NPerformance
        var imageName: String {
            switch self {
            case .trim:
                return "ProgressTrim"
            case .engine:
                return "ProgressEngine"
            case .bodyType:
                return "ProgressBodyType"
            case .drivingMethod:
                return "ProgressDrivingMethod"
            case .exteriorColor:
                return "ProgressExteriorColor"
            case .interiorColor:
                return "ProgressInteriorColor"
            case .selectOptions:
                return "ProgressSelectOptions"
            case .hGenuineAccessories:
                return "ProgressHGenuineAccessories"
            case .NPerformance:
                return "ProgressNPerformance"
            }
        }
    }
    // MARK: - UI properties
    
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        translatesAutoresizingMaskIntoConstraints = false
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        translatesAutoresizingMaskIntoConstraints = false
    }
    // MARK: - Helpers
    func setImage(to progress: Progress) {
        image = UIImage(named: progress.imageName)?.resized(to: window?.windowScene?.screen.bounds.width ?? 352)
    }
}
