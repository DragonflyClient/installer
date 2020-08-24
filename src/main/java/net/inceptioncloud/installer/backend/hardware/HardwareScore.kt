package net.inceptioncloud.installer.backend.hardware

import net.inceptioncloud.installer.backend.hardware.components.CPU
import net.inceptioncloud.installer.backend.hardware.components.RAM
import oshi.SystemInfo
import oshi.hardware.CentralProcessor

private var calcTime = 0L

fun runTest() {

    val systemInfo = SystemInfo()
    val hardware = systemInfo.hardware
    val cpu = hardware.processor

    val coresCount = CPU.getCoresCount()
    val maxClock = (cpu.maxFreq / 1000000).toInt()
    val ramSize = RAM.getSize()

    println()
    println("CPU Cores: ${coresCount}x")
    println("CPU max Clock: ${maxClock}MHz")
    println("RAM Size: ${ramSize}MB")

    println("--------------------------------------------")
    println("Calculating hardware score...").also { calcTime = System.currentTimeMillis() }
    calculateScore(coresCount, maxClock, ramSize)

}

private fun calculateScore(coresCount: Int, maxClock: Int, ramSize: Int) {
    var score = 0

    for (clock in 1..coresCount) {
        score += maxClock
    }

    for (mb in 1..ramSize) {
        score++
    }

    println("")
    println("Calculation finished in ${System.currentTimeMillis() - calcTime}ms!")
    println("Reached score: $score")
}

private fun getAverageCpuClock(cpu: CentralProcessor): Int {
    var values = 0L
    var size = 0

    for (coreClock in cpu.currentFreq) {
        values += coreClock
        size++
    }

    return ((values / size) / 1000000).toInt()
}