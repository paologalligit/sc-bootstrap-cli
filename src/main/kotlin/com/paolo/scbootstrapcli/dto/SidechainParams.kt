package com.paolo.scbootstrapcli.dto

data class SidechainParams(
    val keySeed: String,
    val vrfKeySeed: String,
    val maxPks: Int,
    val threshold: Int,
    val provingKeyPath: String,
    val verificationKeyPath: String,
    val withdrawalEpochLength: Int,
    val amount: Double,
    val walletSeed: String,
    val outputFilePath: String,
    val blocksBefore: Int = 0,
    val enableSubmit: Boolean = false,
    val signingCertificateEnabled: Boolean = false,
    val automaticForging: Boolean = false,
    val startZendCheck: Boolean = false
)
