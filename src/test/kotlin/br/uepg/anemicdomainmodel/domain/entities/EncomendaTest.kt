package br.uepg.anemicdomainmodel.domain.entities

import br.uepg.anemicdomainmodel.domain.repositories.EncomendaRepository
import br.uepg.anemicdomainmodel.domain.repositories.ParticipanteRepository
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
class EncomendaTest {
    @Inject
    lateinit var encomendaRepository: EncomendaRepository

    @Test
    fun `testar trava de persistência de código de rastreio inválido e inconsistencia de atributos`() {
        val exception = assertThrows(Exception::class.java) {
            val encomenda = Encomenda().apply {
                codigoRastreio = "123"
            }

            encomendaRepository.save(encomenda)
        }

        assertEquals("codigo inválido", exception.message)
    }
}