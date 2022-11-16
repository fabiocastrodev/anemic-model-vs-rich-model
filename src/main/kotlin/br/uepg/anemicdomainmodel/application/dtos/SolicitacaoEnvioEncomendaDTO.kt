package br.uepg.anemicdomainmodel.application.dtos

data class SolicitacaoEnvioEncomendaDTO(
    val origemId: Long,
    val destinoId: Long,
    val remetente: ParticipanteDTO,
    val destinatario: ParticipanteDTO
    )
