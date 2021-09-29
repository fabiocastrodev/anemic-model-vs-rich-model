package br.uepg.richmodel.domain.factories

import br.uepg.richmodel.domain.vo.EAN13Barcode

class BarcodeFactory {
    companion object {
        fun createEAN13(value: String): EAN13Barcode {
            return EAN13Barcode(value)
        }
    }
}