//
    //  AnotherProcedureView.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/20.
//

import UIKit

class AnotherProcedureView: UIView {
    // MARK: - UI properties
    private let anotherProcedureTitle: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "구매를 위한 다른 절차가 필요하신가요?"
        label.font = .designSystem(.init(name: .medium, size: ._16))
        return label
    }()
    private let purchaseConsulting: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "구매상담 신청하기"
        label.font = .designSystem(.init(name: .medium, size: ._14))
        label.layer.cornerRadius = 8
        label.clipsToBounds = true
        label.textAlignment = .center
        label.backgroundColor = .hyundaiLightSand
        return label
    }()
    private let downloadPDF: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "PDF 다운로드"
        label.font = .designSystem(.init(name: .medium, size: ._14))
        label.layer.cornerRadius = 8
        label.clipsToBounds = true
        label.textAlignment = .center
        label.backgroundColor = .hyundaiLightSand
        return label
    }()
    private let downloadCatalog: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "카탈로그 다운로드"
        label.font = .designSystem(.init(name: .medium, size: ._14))
        label.layer.cornerRadius = 8
        label.clipsToBounds = true
        label.textAlignment = .center
        label.backgroundColor = .hyundaiLightSand
        return label
    }()
    private let vehicleInquiry: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = "전시 차량 조회"
        label.font = .designSystem(.init(name: .medium, size: ._14))
        label.layer.cornerRadius = 8
        label.clipsToBounds = true
        label.textAlignment = .center
        label.backgroundColor = .hyundaiLightSand
        return label
    }()
    // MARK: - Properties
    
    // MARK: - Lifecycles
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUI()
        setLayout()
    }
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setUI()
        setLayout()
    }
    
    // MARK: - Helpers
    private func setUI() {
        [anotherProcedureTitle, purchaseConsulting, downloadPDF, downloadCatalog, vehicleInquiry].forEach {
            addSubview($0)
        }
    }
    private func setLayout() {
        NSLayoutConstraint.activate([
            anotherProcedureTitle.heightAnchor.constraint(equalToConstant: 24),
            anotherProcedureTitle.widthAnchor.constraint(equalToConstant: 250),
            anotherProcedureTitle.topAnchor.constraint(equalTo: topAnchor),
            anotherProcedureTitle.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            purchaseConsulting.heightAnchor.constraint(equalToConstant: 52),
            purchaseConsulting.widthAnchor.constraint(equalToConstant: 167),
            purchaseConsulting.topAnchor.constraint(equalTo: anotherProcedureTitle.bottomAnchor, constant: 12),
            purchaseConsulting.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            downloadPDF.heightAnchor.constraint(equalToConstant: 52),
            downloadPDF.widthAnchor.constraint(equalToConstant: 167),
            downloadPDF.topAnchor.constraint(equalTo: purchaseConsulting.topAnchor),
            downloadPDF.trailingAnchor.constraint(equalTo: trailingAnchor),
            
            downloadCatalog.heightAnchor.constraint(equalToConstant: 52),
            downloadCatalog.widthAnchor.constraint(equalToConstant: 167),
            downloadCatalog.topAnchor.constraint(equalTo: purchaseConsulting.bottomAnchor, constant: 8),
            downloadCatalog.leadingAnchor.constraint(equalTo: leadingAnchor),
            
            vehicleInquiry.heightAnchor.constraint(equalToConstant: 52),
            vehicleInquiry.widthAnchor.constraint(equalToConstant: 167),
            vehicleInquiry.topAnchor.constraint(equalTo: downloadCatalog.topAnchor),
            vehicleInquiry.trailingAnchor.constraint(equalTo: trailingAnchor)
        ])
    }
}
