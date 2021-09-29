package br.uepg.richmodel.domain.entities

import java.util.*

class PurchaseOrder {
    var id: Long = 0L

    var _receivedValue: Double = 0.0
    val receivedValue get() = this._receivedValue

    var _changeValue: Double = 0.0
    val changeValue get() = this._changeValue

    var _discountValue: Double = 0.0
    val discountValue get() = this._discountValue

    var _items: MutableList<PurchaseItem> = mutableListOf()
    val items get() = this._items.toList().toMutableList()

    val totalValue get()
        = this._items.sumOf { item -> item.product.unitaryValue } - this._discountValue

    var _date: Date = Date()

    val date get() = _date

    fun pay(value: Double) {
        if (_items.isEmpty()) {
            throw Exception("Ordem de compra vazia")
        }

        if (value < totalValue) {
            throw Exception("Valor recebido não é suficiente")
        }

        this._receivedValue = value
        this._changeValue = totalValue - value
    }

    fun applyDiscountValue(value: Double) {
        if (value <= 0) {
            throw Exception("Valor de desconto deve ser maior que 0")
        }

        this._discountValue = value
    }

    fun removeDiscountValue(value: Double) {
        if (value > _discountValue) {
            throw Exception("Valor maior que o desconto aplicado")
        }

        this._discountValue -= value
    }

    fun addProduct(product: Product, quantity: Int = 1) {
        val item = this._items.find { item -> item.product == product }
        if (item !== null) {
            item.addQuantity(quantity)
        } else {
            val item = PurchaseItem(product, quantity)

            this._items.add(item)
        }
    }

    fun removeProduct(product: Product, quantity: Int = 1) {
        val item = this._items.find { item -> item.product == product }
        if (item != null) {
            try {
                item.subQuantity(quantity)
            } catch (e: Exception) {
                this._items.remove(item)
            }
        }
    }
}