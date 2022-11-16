package br.uepg.anemicdomainmodel.domain.entities

import br.uepg.anemicdomainmodel.domain.repositories.ParticipanteRepository
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
class ParticipanteTest {
    @Inject
    lateinit var participanteRepository: ParticipanteRepository

    @Test
    fun `testar trava de persistência de participante com cpf inválido`() {
        val exception = assertThrows(Exception::class.java) {
            val participante = Participante().apply {
                nome = "Fábio Meira de Castro"
                cpf = "45678912345"
            }

            participanteRepository.save(participante)
        }

        assertEquals("cpf inválido", exception.message)
    }
}