package pw.edu.pl.util

import pw.edu.pl.domain.Matrix
import pw.edu.pl.rpc.RpcClient
import pw.edu.pl.solver.Cholesky

class CholeskyExecutor {

    companion object {
        fun execute(cholesky: Cholesky, type: String, matrix: Matrix, rpcClient: RpcClient?): Matrix {
            return when (type) {
                "sequential" -> cholesky.solveSequential(matrix)
                "threads" -> cholesky.solveThreads(matrix)
                "grpc" -> {
                    rpcClient?.let {
                        cholesky.solveRpc(matrix, rpcClient)
                    } ?: throw Exception("could not execute Cholesky solver for type gRPC due to missing connection data")
                }
                else -> throw Exception("could not execute Cholesky solver for type = $type")
            }
        }
    }
}