package io.github.iromul.mkstransfer.app.model.settings.printer

enum class PrintHostUploadMode(
    val displayName: String
) {

    NONE("None"),
    MKS_TFT("MKS TFT");

    companion object {
        val displayNames = listOf(NONE.displayName, MKS_TFT.displayName)
    }
}