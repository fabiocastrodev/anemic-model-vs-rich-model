package br.uepg.richmodel.domain.entities

class PurchaseItem(product: Product, quantity: Int) {
    private var _product: Product = product
    val product get() = _product

    private var _quantity: Int = 0
    val quantity get() = _quantity

    init {
        if (quantity < 0) {
            throw Exception("Valor deve ser maior ou igual a 0")
        }
    }

    fun addQuantity(value: Int) {
        if (value < 0) {
            throw Exception("Valor invalido para adicionar")
        }

        _quantity += value
    }

    fun subQuantity(value: Int) {
        if (_quantity - value < 0) {
            throw Exception("RemovedPurchaseItem")
        }

        _quantity -= value
    }
}