//
//  KeyChain.swift
//  TopCariving
//
//  Created by Eunno An on 2023/08/22.
//

import Security
import Foundation
enum KeyChain {
    static func saveStringToKeychain(value: String, key: String) async -> Bool {
        return await withCheckedContinuation { continuation in
            DispatchQueue.global(qos: .background).async {
                if let valueData = value.data(using: .utf8) {
                    let query: [String: Any] = [
                        kSecClass as String: kSecClassGenericPassword,
                        kSecAttrAccount as String: key,
                        kSecValueData as String: valueData
                    ]
                    let status = SecItemAdd(query as CFDictionary, nil)
                    let ret = !(status == errSecSuccess)
                    continuation.resume(returning: ret)
                } else {
                    continuation.resume(returning: false)
                }
            }
        }
    }
    static func getStringFromKeychain(key: String) async -> String? {
        return await withCheckedContinuation { continuation in
            DispatchQueue.global(qos: .background).async {
                let query: [String: Any] = [
                    kSecClass as String: kSecClassGenericPassword,
                    kSecAttrAccount as String: key,
                    kSecReturnData as String: kCFBooleanTrue!,
                    kSecMatchLimit as String: kSecMatchLimitOne
                ]
                var result: AnyObject?
                let status = SecItemCopyMatching(query as CFDictionary, &result)
                if status == errSecSuccess {
                    if let data = result as? Data, let string = String(data: data, encoding: .utf8) {
                        continuation.resume(returning: string)
                    } else {
                        continuation.resume(returning: nil)
                    }
                } else {
                    continuation.resume(returning: nil)
                }
            }
        }
    }
    static func deleteStringFromKeychain(key: String) async -> Bool {
        return await withCheckedContinuation { continuation in
            DispatchQueue.global(qos: .background).async {
                let query: [String: Any] = [
                    kSecClass as String: kSecClassGenericPassword,
                    kSecAttrAccount as String: key
                ]
                let status = SecItemDelete(query as CFDictionary)
                if status == errSecSuccess {
                    continuation.resume(returning: true)
                } else {
                    continuation.resume(returning: false)
                }
            }
        }
    }
}
