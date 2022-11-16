package br.uepg.anemicdomainmodel.application.services

import br.uepg.anemicdomainmodel.application.dtos.SolicitacaoEnvioEncomendaDTO
import br.uepg.anemicdomainmodel.domain.entities.Encomenda
import br.uepg.anemicdomainmodel.domain.entities.Participante
import br.uepg.anemicdomainmodel.domain.repositories.EncomendaRepository
import br.uepg.anemicdomainmodel.domain.repositories.ParticipanteRepository
import br.uepg.anemicdomainmodel.domain.repositories.RotaRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import kotlin.random.Random

@ApplicationScoped
class EncomendaService {
    @Inject
    lateinit var rotaRepository: RotaRepository

    @Inject
    lateinit var participanteRepository: ParticipanteRepository

    @Inject
    lateinit var encomendaRepository: EncomendaRepository

    @Inject
    lateinit var emailService: EmailService

    @Inject
    lateinit var participanteService: ParticipanteService

    fun gerarCodigoRastreio(): String {
        val code = (1..8).map { Char(Random.nextInt(65, 90)) }.joinToString("")

        return code
    }

    fun solicitarEnvioEncomenda(request: SolicitacaoEnvioEncomendaDTO): Encomenda {
        try {
            lateinit var remetente: Participante
            lateinit var destinatario: Participante

            if (!participanteService.isCPF(request.remetente.cpf)) {
                throw Exception("cpf inválido")
            }

            if (!participanteService.isCPF(request.destinatario.cpf)) {
                throw Exception("cpf inválido")
            }

            val rota = rotaRepository.findByOrigemDestino(request.origemId, request.destinoId)

            try {
                remetente = participanteRepository.findByCPF(request.remetente.cpf)
            } catch (e: Exception) {
                remetente = Participante().apply {
                    cpf = request.remetente.cpf
                    nome = request.remetente.nome

                    email = request.remetente.email
                }

                participanteRepository.save(remetente)
            }

            if (request.remetente.cpf == request.destinatario.cpf) {
                destinatario = remetente
            } else {
                try {
                    destinatario = participanteRepository.findByCPF(request.destinatario.cpf)
                } catch (e: Exception) {
                    destinatario = Participante().apply {
                        cpf = request.destinatario.cpf
                        nome = request.destinatario.nome

                        email = request.destinatario.email
                    }

                    participanteRepository.save(destinatario)
                }
            }

            val encomenda = Encomenda().apply {
                this.rota = rota
                this.codigoRastreio = gerarCodigoRastreio()

                this.remetente = remetente
                this.destinatario = destinatario
            }

            encomendaRepository.save(encomenda)

            if (!destinatario.email.isNullOrBlank()) {
                emailService.enviarCodigoRastreio(destinatario.email!!, encomenda.codigoRastreio)
            }

            return encomenda
        } catch (e: Exception) {
            throw Exception("não foi possível seguir com a solicitação")
        }
    }
}