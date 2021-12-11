package io.github.iromul.commons.javafx.beans.property

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import kotlin.reflect.KProperty1

fun simpleStringProperty(
    bean: String?,
    prop: KProperty1<*, String?>,
    initialValue: String? = null
): SimpleStringProperty =
    SimpleStringProperty(bean, prop.name, initialValue)

fun <T> simpleObjectProperty(bean: T?, prop: KProperty1<*, T?>, initialValue: T? = null): SimpleObjectProperty<T> =
    SimpleObjectProperty(bean, prop.name, initialValue)