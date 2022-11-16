package br.uepg.anemicdomainmodel.domain.entities

class Participante {
    var id: Long? = null
    lateinit var nome: String

    lateinit var cpf: String

    var email: String? = null
}