package br.com.zup.edu

import br.com.zup.edu.shared.grpc.KeyManagerGrpcFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultaChavePixControllerTest{
    @field:Inject
    lateinit var carregaChaveStub: KeymanagerCarregaGrpcServiceGrpc.KeymanagerCarregaGrpcServiceBlockingStub

    @field:Inject
    lateinit var listaChaveStub: KeymanagerListaGrpcServiceGrpc.KeymanagerListaGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    val CHAVE_EMAIL = "teste@teste.com.br"
    val CHAVE_CELULAR = "+5511912345678"
    val CONTA_CORRENTE = TipoDeConta.CONTA_CORRENTE
    val TIPO_DE_CHAVE_EMAIL = TipoDeChave.EMAIL
    val TIPO_DE_CHAVE_CELULAR = TipoDeChave.CELULAR
    val INSTITUICAO = "Itau"
    val TITULAR = "Woody"
    val DOCUMENTO_DO_TITULAR = "34597563067"
    val AGENCIA = "0001"
    val NUMERO_DA_CONTA = "1010-1"
    val CHAVE_CRIADA_EM = LocalDateTime.now()

    @Test
    internal fun `deve carregar uma chave pix existente`() {

        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        given(carregaChaveStub.carrega(Mockito.any())).willReturn(carregaChavePixResponse(clientId, pixId))


        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clientId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertNotNull(response.body())
    }

    @Test
    internal fun `deve listar todas as chaves pix existente`() {

        val clientId = UUID.randomUUID().toString()

        val respostaGrpc = listaChavePixResponse(clientId)

        given(listaChaveStub.lista(Mockito.any())).willReturn(respostaGrpc)


        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clientId/pix/")
        val response = client.toBlocking().exchange(request, List::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertNotNull(response.body())
        Assertions.assertEquals(response.body()!!.size, 2)
    }

    private fun listaChavePixResponse(clientId: String): ListaChavePixResponse {
        val chaveEmail = ListaChavePixResponse.ChavePix.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setTipo(TIPO_DE_CHAVE_EMAIL)
            .setChave(CHAVE_EMAIL)
            .setTipoDeConta(CONTA_CORRENTE)
            .setCriadaEm(CHAVE_CRIADA_EM.let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano)
                    .build()
            })
            .build()

        val chaveCelular = ListaChavePixResponse.ChavePix.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setTipo(TIPO_DE_CHAVE_CELULAR)
            .setChave(CHAVE_CELULAR)
            .setTipoDeConta(CONTA_CORRENTE)
            .setCriadaEm(CHAVE_CRIADA_EM.let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano)
                    .build()
            })
            .build()


        return ListaChavePixResponse.newBuilder()
            .setClientId(clientId)
            .addAllChaves(listOf(chaveEmail, chaveCelular))
            .build()

    }

    private fun carregaChavePixResponse(clientId: String, pixId: String) =
        CarregaChavePixResponse.newBuilder()
            .setClientId(clientId)
            .setPixId(pixId)
            .setChave(CarregaChavePixResponse.ChavePix
                .newBuilder()
                .setTipo(TIPO_DE_CHAVE_EMAIL)
                .setChave(CHAVE_EMAIL)
                .setConta(CarregaChavePixResponse.ChavePix.ContaInfo.newBuilder()
                    .setTipo(CONTA_CORRENTE)
                    .setInstituicao(INSTITUICAO)
                    .setNomeDoTitular(TITULAR)
                    .setCpfDoTitular(DOCUMENTO_DO_TITULAR)
                    .setAgencia(AGENCIA)
                    .setNumeroDaConta(NUMERO_DA_CONTA)
                    .build()
                )
                .setCriadaEm(CHAVE_CRIADA_EM.let {
                    val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                    Timestamp.newBuilder()
                        .setSeconds(createdAt.epochSecond)
                        .setNanos(createdAt.nano)
                        .build()
                })).build()

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubListaMock() = Mockito.mock(KeymanagerListaGrpcServiceGrpc.KeymanagerListaGrpcServiceBlockingStub::class.java)

        @Singleton
        fun stubDetalhesMock() = Mockito.mock(KeymanagerCarregaGrpcServiceGrpc.KeymanagerCarregaGrpcServiceBlockingStub::class.java)
    }
}