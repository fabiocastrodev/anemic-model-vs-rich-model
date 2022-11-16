package br.uepg.anemicdomainmodel.domain.repositories

import br.uepg.anemicdomainmodel.domain.entities.Encomenda

interface EncomendaRepository {
    fun save(encomenda: Encomenda)
    fun findByCodigoRastreio(codigoRastreio: String): Encomenda
}