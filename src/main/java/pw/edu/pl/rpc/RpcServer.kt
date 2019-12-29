package pw.edu.pl.rpc

import io.grpc.ServerBuilder

class RpcServer {
    companion object {
        operator fun invoke(port: Int, messageSize: Int) {
            val server = ServerBuilder
                .forPort(port)
                .addService(RpcService())
                .maxInboundMessageSize(messageSize)
                .build()

            Runtime.getRuntime().addShutdownHook(Thread {
                println("stopping server...")
                server.shutdown()
                server.awaitTermination()
            })

            server.start()
            println("server running on port $port")
            server.awaitTermination()
        }
    }
}