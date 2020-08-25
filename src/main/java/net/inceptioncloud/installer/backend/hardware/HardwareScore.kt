package net.inceptioncloud.installer.backend.hardware

import net.inceptioncloud.installer.Logger
import net.inceptioncloud.installer.backend.hardware.components.CPU
import net.inceptioncloud.installer.backend.hardware.components.RAM
import oshi.SystemInfo
import oshi.hardware.CentralProcessor

object HardwareScore {
    private var calcTime = 0L

    fun runTest(): Long {
        calcTime = System.currentTimeMillis()
        Logger.log("Calculating hardware score...")

        val systemInfo = SystemInfo()
        val hardware = systemInfo.hardware
        val cpu = hardware.processor

        val coresCount = CPU.getCoresCount()
        val maxClock = (cpu.maxFreq / 1000000).toInt()
        val ramSize = RAM.getSize()

        return calculateScore(coresCount, maxClock, ramSize)
    }

    private fun calculateScore(coresCount: Int, maxClock: Int, ramSize: Int): Long {
        var score = 0L

        for (clock in 1..coresCount) {
            score += maxClock
        }

        for (mb in 1..ramSize) {
            score++
        }


        Logger.log("Calculation finished in ${System.currentTimeMillis() - calcTime}ms!")
        Logger.log("Reached score: $score")

        return score
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

}