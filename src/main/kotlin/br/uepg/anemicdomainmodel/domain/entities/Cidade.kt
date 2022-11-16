package br.uepg.anemicdomainmodel.domain.entities

class Cidade {
    var id: Long? = null

    lateinit var uf: String
    lateinit var nome: String
    var codigoIBGE: Long = -1L
}