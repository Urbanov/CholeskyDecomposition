package pw.edu.pl.rpc

import io.grpc.ManagedChannelBuilder
import pw.edu.pl.CholeskySolverGrpc
import pw.edu.pl.ProtobufMatrix
import pw.edu.pl.domain.Matrix

class RpcClient(target: String, messageSize: Int) {
    private val service: CholeskySolverGrpc.CholeskySolverBlockingStub

    init {
        val channel = ManagedChannelBuilder.forTarget(target)
            .usePlaintext()
            .maxInboundMessageSize(messageSize)
            .build()

        service = CholeskySolverGrpc.newBlockingStub(channel)
    }

    fun solveRow(matrix: Matrix): Matrix {
        return solve(matrix, service::solveRow)
    }

    fun solveColumn(matrix: Matrix): Matrix {
        return solve(matrix, service::solveColumn)
    }

    private fun solve(matrix: Matrix, service: (ProtobufMatrix) -> ProtobufMatrix): Matrix {
        return RpcService.fromProtobuf(service.invoke(RpcService.toProtobuf(matrix)))
    }
}