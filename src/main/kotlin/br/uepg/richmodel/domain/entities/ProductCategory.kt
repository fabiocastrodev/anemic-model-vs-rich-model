package br.uepg.richmodel.domain.entities

class ProductCategory(name: String) {
    var id: Long = 0L

    private var _name: String = ""

    val name get() = _name

    init {
        changeName(name)
    }

    fun changeName(name: String) {
        if (name.isEmpty() || name.length > 120) {
            throw Exception("Nome inválido")
        }

        this._name = name
    }
}