package br.com.zup.edu.shared.grpc


import br.com.zup.edu.KeymanagerRegistraGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun registraChave() =KeymanagerRegistraGrpcServiceGrpc.newBlockingStub(channel)

}