package br.com.zup.edu.shared.grpc


import br.com.zup.edu.KeymanagerCarregaGrpcServiceGrpc
import br.com.zup.edu.KeymanagerListaGrpcServiceGrpc
import br.com.zup.edu.KeymanagerRegistraGrpcServiceGrpc
import br.com.zup.edu.KeymanagerRemoveGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun registraChave() =KeymanagerRegistraGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removeChave() = KeymanagerRemoveGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carregaChave () = KeymanagerCarregaGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChave () = KeymanagerListaGrpcServiceGrpc.newBlockingStub(channel)

}