package br.uepg.richmodel.domain.vo

import br.uepg.richmodel.domain.exceptions.InvalidEAN13BarcodeException

class EAN13Barcode(value: String) : BarcodeValue(value) {
    init {
        if (!isValid(value)) {
            throw InvalidEAN13BarcodeException(value)
        }
    }

    private fun isValid(value: String): Boolean {
        if (value.matches("^[0-9]{13}$".toRegex())) {
            val sumEAN = value.toCharArray(startIndex = 0, endIndex = 11).mapIndexed { idx, c ->
                c.toString().toInt() * if (idx % 2 == 0) 1 else 3
            }.sum()

            val multiple = (sumEAN / 10).let { m ->
                if (sumEAN % m != 0) m + 1 else m
            } * 10

            val digit = multiple - sumEAN

            return digit.toString() == value[12].toString()
        }

        return false
    }
}