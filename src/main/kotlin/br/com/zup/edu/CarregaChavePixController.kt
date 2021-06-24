package br.com.zup.edu

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clientes/{clientId}")
class ConsultaChavePixController (
    val consultaChavePixClient: KeymanagerCarregaGrpcServiceGrpc.KeymanagerCarregaGrpcServiceBlockingStub,
    val listaChavesPixClient: KeymanagerListaGrpcServiceGrpc.KeymanagerListaGrpcServiceBlockingStub)  {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{pixId}")
    fun carrega(clientId: UUID,
                pixId: UUID) : HttpResponse<Any> {

        LOGGER.info("[$clientId] carrega chave pix por id: $pixId")
        val chaveResponse = consultaChavePixClient.carrega(
            CarregaChavePixRequest.newBuilder()
            .setPixId(CarregaChavePixRequest.FiltroPorPixId.newBuilder()
                .setClientId(clientId.toString())
                .setPixId(pixId.toString())
                .build()).
            build())

        return HttpResponse.ok(DetalheChavePixResponse(chaveResponse))
    }

    @Get("/pix/")
    fun lista(clientId: UUID) : HttpResponse<Any> {

        LOGGER.info("[$clientId] listando chaves pix")
        val pix = listaChavesPixClient.lista(ListaChavePixRequest.newBuilder()
            .setClientId(clientId.toString())
            .build())

        val chaves = pix.chavesList.map { ChavePixResponse(it) }
        return HttpResponse.ok(chaves)
    }
}