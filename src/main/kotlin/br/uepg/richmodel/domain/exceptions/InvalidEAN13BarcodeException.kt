package br.uepg.richmodel.domain.exceptions

import br.uepg.richmodel.domain.vo.DomainException

class InvalidEAN13BarcodeException(val value: String) : DomainException("invalid_ean13_barcode", "Invalid EAN13 barcode $value")