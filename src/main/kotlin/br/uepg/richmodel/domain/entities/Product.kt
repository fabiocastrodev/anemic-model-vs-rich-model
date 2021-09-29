package br.uepg.richmodel.domain.entities

import br.uepg.richmodel.domain.vo.BarcodeValue

class Product(name: String, unitaryValue: Double, barcode: BarcodeValue)  {
    var id: Long = 0L

    private var _name: String = ""

    val name get() = _name

    private var _unitaryValue: Double = 0.0

    val unitaryValue get() = _unitaryValue

    private lateinit var _barcode: BarcodeValue

    val barcode get() = _barcode

    private var _category: ProductCategory? = null

    val category get() = _category

    init {
        changeName(name)
        changeUnitaryValue(unitaryValue)
        changeBarcode(barcode)
    }

    constructor(name: String, unitaryValue: Double, barcode: BarcodeValue, category: ProductCategory) : this(name, unitaryValue, barcode) {
        changeCategory(category)
    }

    fun changeName(name: String) {
        if (name.isNotEmpty() || name.length > 120) {
            throw Exception("Nome inválido")
        }

        this._name = name
    }

    fun changeUnitaryValue(value: Double) {
        if (value < 0) {
            throw Exception("The value must be greater or equal to 0")
        }

        this._unitaryValue = value
    }

    fun changeBarcode(barcode: BarcodeValue) {
        this._barcode = barcode
    }

    fun changeCategory(category: ProductCategory) {
        this._category = category
    }

    fun removeCategory() {
        this._category = null
    }
}