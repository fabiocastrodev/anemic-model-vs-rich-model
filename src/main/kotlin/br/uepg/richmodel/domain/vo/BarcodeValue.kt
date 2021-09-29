package br.uepg.richmodel.domain.vo

abstract class BarcodeValue protected constructor(value: String) : Any() {
    val value = value

    override fun toString(): String {
        return value
    }
}