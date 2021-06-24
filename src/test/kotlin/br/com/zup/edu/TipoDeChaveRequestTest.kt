package br.com.zup.edu

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TipoDeChaveRequestTest{

    @Nested
    inner class ALEATORIA { // facilita a visualização na execução dos testes
        @Test
        internal fun `deve ser valido quando chave aleatoria for nula ou vazia`() {
            val tipoDeChave = TipoDeChaveRequest.ALEATORIA
            with(TipoDeChave.ALEATORIA) {
                assertTrue(tipoDeChave.valida(null))
                assertTrue(tipoDeChave.valida(""))
            }
        }

        @Test
        internal fun `nao deve ser valido quando chave aleatoria possuir um valor`() {
            val tipoDeChave = TipoDeChaveRequest.ALEATORIA
            with(TipoDeChave.ALEATORIA) {
                assertFalse(tipoDeChave.valida("qualquer coisa"))
            }
        }
    }

    @Nested
    inner class CPF {
        @Test
        internal fun `deve retornar true quando cpf for valido`() {
            val tipoDeChave = TipoDeChaveRequest.CPF
            with(TipoDeChave.CPF) {
                assertTrue(tipoDeChave.valida("72670126049"))
            }
        }

        @Test
        internal fun `deve retornar false quando cpf nao for valido`() {
            val tipoDeChave = TipoDeChaveRequest.CPF
            with(TipoDeChave.CPF) {
                assertFalse(tipoDeChave.valida("726707654219"))
            }
        }

        @Test
        internal fun `deve retornar false quando cpf nao for informado`() {
            val tipoDeChave = TipoDeChaveRequest.CPF
            with(TipoDeChave.CPF) {
                assertFalse(tipoDeChave.valida(""))
                assertFalse(tipoDeChave.valida(null))
            }
        }
    }

    @Nested
    inner class CELULAR {

        @Test
        internal fun `deve retornar true quando celular for um numero valido`() {
            val tipoDeChave = TipoDeChaveRequest.CELULAR
            with(TipoDeChave.CELULAR) {
                assertTrue(tipoDeChave.valida("+5511987654321"))
            }
        }

        @Test
        internal fun `nao deve ser valido quando celular for um numero invalido`() {
            val tipoDeChave = TipoDeChaveRequest.CELULAR
            with(TipoDeChave.CELULAR) {
                assertFalse(tipoDeChave.valida("11987654321"))
                assertFalse(tipoDeChave.valida("+55a11987654321"))
            }
        }

        @Test
        internal fun `nao deve ser valido quando celular nao for informado`() {
            val tipoDeChave = TipoDeChaveRequest.CELULAR
            with(TipoDeChave.CELULAR) {
                assertFalse(tipoDeChave.valida(""))
                assertFalse(tipoDeChave.valida(null))
            }
        }
    }

    @Nested
    inner class EMAIL {

        @Test
        internal fun `deve ser valido quando email for endereco valido`() {
            val tipoDeChave = TipoDeChaveRequest.EMAIL
            with(TipoDeChave.EMAIL) {
                assertTrue(tipoDeChave.valida("rponte@gmail.com"))
            }
        }

        @Test
        internal fun `nao deve ser valido quando email estiver em um formato invalido`() {
            val tipoDeChave = TipoDeChaveRequest.EMAIL
            with(TipoDeChave.EMAIL) {
                assertFalse(tipoDeChave.valida("rponte.gmail.com"))
            }
        }

        @Test
        internal fun `nao deve ser valido quando email nao for informado`() {
            val tipoDeChave = TipoDeChaveRequest.EMAIL
            with(TipoDeChave.EMAIL) {
                assertFalse(tipoDeChave.valida(""))
                assertFalse(tipoDeChave.valida(null))
            }
        }
    }

}