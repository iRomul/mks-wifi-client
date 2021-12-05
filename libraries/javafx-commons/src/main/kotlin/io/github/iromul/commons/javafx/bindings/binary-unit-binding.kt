package io.github.iromul.commons.javafx.bindings

import com.jakewharton.byteunits.BinaryByteUnit
import javafx.beans.binding.NumberExpression
import javafx.beans.binding.StringBinding
import javafx.collections.FXCollections
import javafx.collections.ObservableList

fun NumberExpression.asBinaryUnit(): StringBinding {
    return object : StringBinding() {
        init {
            super.bind(this@asBinaryUnit)
        }

        override fun dispose() {
            super.unbind(this@asBinaryUnit)
        }

        override fun computeValue(): String {
            val value = this@asBinaryUnit.value

            return value?.let { BinaryByteUnit.format(it.toLong()) } ?: ""
        }

        override fun getDependencies(): ObservableList<*> {
            return FXCollections.singletonObservableList(this@asBinaryUnit)
        }
    }
}