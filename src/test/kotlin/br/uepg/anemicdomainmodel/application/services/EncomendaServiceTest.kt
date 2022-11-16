package br.uepg.anemicdomainmodel.application.services

import br.uepg.anemicdomainmodel.application.dtos.ParticipanteDTO
import br.uepg.anemicdomainmodel.application.dtos.SolicitacaoEnvioEncomendaDTO
import br.uepg.anemicdomainmodel.domain.entities.Cidade
import br.uepg.anemicdomainmodel.domain.entities.Encomenda
import br.uepg.anemicdomainmodel.domain.entities.Participante
import br.uepg.anemicdomainmodel.domain.entities.Rota
import br.uepg.anemicdomainmodel.domain.repositories.EncomendaRepository
import br.uepg.anemicdomainmodel.domain.repositories.ParticipanteRepository
import br.uepg.anemicdomainmodel.domain.repositories.RotaRepository
import io.quarkus.test.Mock
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@QuarkusTest
class EncomendaServiceTest {
    @Inject
    lateinit var encomendaService: EncomendaService

    @Test
    fun `testar processo de encomenda`() {
        val encomenda = encomendaService.solicitarEnvioEncomenda(SolicitacaoEnvioEncomendaDTO(
            origemId = 1,
            destinoId = 2,
            remetente = ParticipanteDTO("Fábio Meira de Castro", "44666517871"),
            destinatario = ParticipanteDTO("José Silva", "79374085887")
        ))

        println(encomenda.codigoRastreio)

        assertNotNull(encomenda)
    }

    @Test
    fun `testar rota não existente`() {
        val exception = assertThrows(Exception::class.java) {
            encomendaService.solicitarEnvioEncomenda(SolicitacaoEnvioEncomendaDTO(
                origemId = 1,
                destinoId = 3,
                remetente = ParticipanteDTO("Fábio Meira de Castro", "44666517871"),
                destinatario = ParticipanteDTO("José Silva", "79374085887")
            ))
        }


        assertEquals("rota não encontrado", exception.message)
    }

    @Mock
    @ApplicationScoped
    class RotaRepositoryMockable : RotaRepository {
        val rows = arrayListOf<Rota>(
            Rota().apply {
                origem = Cidade().apply {
                    id = 1
                    nome = "Ponta Grossa"
                    uf = "PR"
                }

                destino = Cidade().apply {
                    id = 2
                    nome = "Curitiba"
                    uf = "PR"
                }
            }
        )

        override fun save(rota: Rota) {
            if (rota.id == null) {
                val id = rows.sumOf { r -> r.id!! } + 1

                rota.id = id
                rows.add(rota)
            } else {
                val exists = rows.find { r -> r.id == rota.id }
                rows.remove(exists)
                rows.add(rota)
            }
        }

        override fun findByOrigemDestino(origemId: Long, destinoId: Long): Rota {
            val rota = rows.find { r -> r.origem.id == origemId && r.destino.id == destinoId }
                ?: throw Exception("rota não encontrado")

            return rota
        }
    }

    @Mock
    @ApplicationScoped
    class EncomendaRepositoryMockable : EncomendaRepository {
        val rows = arrayListOf<Encomenda>()

        override fun save(encomenda: Encomenda) {
            if (encomenda.id == null) {
                val id = rows.sumOf { r -> r.id!! } + 1

                encomenda.id = id
                rows.add(encomenda)
            } else {
                val exists = rows.find { r -> r.id == encomenda.id }
                rows.remove(exists)
                rows.add(encomenda)
            }
        }

        override fun findByCodigoRastreio(codigoRastreio: String): Encomenda {
            val encomenda = rows.find { e -> e.codigoRastreio == codigoRastreio }
                ?: throw Exception("encomenda não encontrado")

            return encomenda
        }
    }

    @Mock
    @ApplicationScoped
    class ParticipanteRepositoryMockable : ParticipanteRepository {
        val rows = arrayListOf<Participante>()

        override fun save(participante: Participante) {
            if (participante.id == null) {
                val id = rows.sumOf { r -> r.id!! } + 1

                participante.id = id
                rows.add(participante)
            } else {
                val exists = rows.find { r -> r.id == participante.id }
                rows.remove(exists)
                rows.add(participante)
            }
        }

        override fun findByCPF(cpf: String): Participante {
            val participante = rows.find { e -> e.cpf == cpf }
                ?: throw Exception("participante não encontrado")

            return participante
        }
    }

    @Mock
    @ApplicationScoped
    class EmailServiceMockable : EmailService {
        override fun enviarCodigoRastreio(email: String, codigoRastreio: String) {
            println("send email $email $codigoRastreio")
        }
    }
}