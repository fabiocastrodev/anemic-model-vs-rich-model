package br.uepg.anemicdomainmodel.domain.entities

class Encomenda {
    var id: Long? = null

    lateinit var codigoRastreio: String
    lateinit var rota: Rota

    lateinit var remetente: Participante
    lateinit var destinatario: Participante
}