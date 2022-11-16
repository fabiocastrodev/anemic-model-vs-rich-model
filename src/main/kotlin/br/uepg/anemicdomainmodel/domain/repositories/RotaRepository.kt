package br.uepg.anemicdomainmodel.domain.repositories

import br.uepg.anemicdomainmodel.domain.entities.Rota

interface RotaRepository {
    fun save(rota: Rota)
    fun findByOrigemDestino(origemId: Long, destinoId: Long): Rota
}