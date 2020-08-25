package net.inceptioncloud.installer.backend.hardware.components

object CPU {

    /**
     * @return Count of all physical- and logical-cores
     */
    fun getCoresCount(): Int = Runtime.getRuntime().availableProcessors()

}