package io.github.iromul.mkstransfer.app

import com.jakewharton.byteunits.DecimalByteUnit
import com.sun.jna.Native
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.Kernel32Util
import com.sun.jna.platform.win32.WinBase
import com.sun.jna.platform.win32.WinNT
import com.sun.jna.ptr.IntByReference
import org.junit.jupiter.api.Test
import kotlin.reflect.KMutableProperty0

class JNATest {

    @Test
    internal fun name() {
        val drives = Kernel32Util.getLogicalDriveStrings()

        drives
            .map {
                val type = when (Kernel32Util.getDriveType(it)) {
                    WinBase.DRIVE_UNKNOWN -> WinBase::DRIVE_UNKNOWN.name
                    WinBase.DRIVE_NO_ROOT_DIR -> WinBase::DRIVE_NO_ROOT_DIR.name
                    WinBase.DRIVE_REMOVABLE -> WinBase::DRIVE_REMOVABLE.name
                    WinBase.DRIVE_FIXED -> WinBase::DRIVE_FIXED.name
                    WinBase.DRIVE_REMOTE -> WinBase::DRIVE_REMOTE.name
                    WinBase.DRIVE_RAMDISK -> WinBase::DRIVE_RAMDISK.name
                    else -> "Other"
                }

                Drive(it, type)
            }
            .forEach {
                println(it)

                val parameters = GetVolumeInformationParameters()

                val ok = Kernel32.INSTANCE.GetVolumeInformation(
                    it.name,
                    parameters.lpVolumeNameBuffer, parameters.nVolumeNameSize,
                    parameters.lpVolumeSerialNumber,
                    parameters.lpMaximumComponentLength,
                    parameters.lpFileSystemFlags,
                    parameters.lpFileSystemNameBuffer, parameters.nFileSystemNameSize
                )

                if (ok) {
                    val volumeInformation = parameters.toVolumeInformation()

                    println(volumeInformation)

                    val available = WinNT.LARGE_INTEGER()

                    if (volumeInformation.fileSystemName.isNotEmpty()) {
                        Kernel32.INSTANCE.GetDiskFreeSpaceEx(
                            it.name,
                            available,
                            null,
                            null
                        )

                        println("Space available: ${DecimalByteUnit.format(available.value)}")
                    }
                }
            }
    }

    data class Drive(
        val name: String,
        val type: String,
    )

    class GetVolumeInformationParameters {

        private companion object {
            const val VOLUME_NAME_SIZE = 100
            const val FILE_SYSTEM_NAME_SIZE = 100
        }

        val lpVolumeNameBuffer = CharArray(VOLUME_NAME_SIZE)
        val nVolumeNameSize = VOLUME_NAME_SIZE
        val lpVolumeSerialNumber = IntByReference()
        val lpMaximumComponentLength = IntByReference()
        val lpFileSystemFlags = IntByReference()
        val lpFileSystemNameBuffer = CharArray(FILE_SYSTEM_NAME_SIZE)
        val nFileSystemNameSize = FILE_SYSTEM_NAME_SIZE

        fun toVolumeInformation() = VolumeInformation(
            Native.toString(lpVolumeNameBuffer),
            lpVolumeSerialNumber.value,
            lpMaximumComponentLength.value,
            lpFileSystemFlags.value,
            Native.toString(lpFileSystemNameBuffer)
        )
    }

    data class VolumeInformation(
        val volumeName: String,
        val volumeNameSerialNumber: Int,
        val maximumComponentLength: Int,
        val fileSystemFlags: Int,
        val fileSystemName: String
    )

    class IntPropertyByReference(
        private val prop: KMutableProperty0<Int>,
    ) : IntByReference(prop.get()) {

        override fun setValue(value: Int) {
            prop.set(value)
            super.setValue(value)
        }
    }

    fun KMutableProperty0<Int>.jnaByReference() = IntPropertyByReference(this)
}