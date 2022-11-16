package br.uepg.anemicdomainmodel.domain.entities

class Rota {
    var id: Long? = null
    lateinit var nome: String

    lateinit var origem: Cidade
    lateinit var destino: Cidade

    lateinit var trechos: ArrayList<Trecho>
}