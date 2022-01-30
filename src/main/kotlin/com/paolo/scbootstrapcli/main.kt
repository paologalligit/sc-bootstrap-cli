package com.paolo.scbootstrapcli

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.paolo.scbootstrapcli.command.*
import com.paolo.scbootstrapcli.dto.SidechainParams
import com.paolo.scbootstrapcli.service.GenerateConfigServiceInput
import com.paolo.scbootstrapcli.service.SidechainBootstrapService
import com.paolo.scbootstrapcli.service.StreamGobbler
import java.io.File
import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    // Step 1: make sure the config file path is set
    val configFilePath = try {
        args[0]
    } catch (exception: IndexOutOfBoundsException) {
        throw IllegalStateException("Pass the config file path as first argument")
    }

    // Step 2: read all the params from file
    val mapper = jacksonObjectMapper()

    val configString = File(configFilePath).readText(Charsets.UTF_8)
    val configJson = mapper.readValue<SidechainParams>(configString)

    // Step 3: generate initial config
    val command = Command(
        KeySeedGenerator(),
        VrfKeySeedGenerator(),
        ProofInfoGenerator(),
        GenesisInfoGenerator()
    )
    val bootstrapService = SidechainBootstrapService(command)
    val initialConfig = bootstrapService.generateInitialConfig(GenerateConfigServiceInput(
        configJson.keySeed,
        configJson.vrfKeySeed,
        configJson.maxPks,
        configJson.threshold,
        configJson.provingKeyPath,
        configJson.verificationKeyPath
    ))

    // Step 3-bis: start zend if needed
    val process: Process = Runtime.getRuntime().exec("ls -larth")
    val streamGobbler = StreamGobbler(process.inputStream, System.out::println)
    Executors.newSingleThreadExecutor().submit(streamGobbler)
    // println("The output command is: ${process.outputStream}")
}