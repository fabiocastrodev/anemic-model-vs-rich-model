package br.uepg.anemicdomainmodel.domain.repositories

import br.uepg.anemicdomainmodel.domain.entities.Participante

interface ParticipanteRepository {
    fun save(participante: Participante)
    fun findByCPF(cpf: String): Participante
}