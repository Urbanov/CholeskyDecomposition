package pw.edu.pl

import pw.edu.pl.domain.Matrix
import pw.edu.pl.rpc.RpcClient
import pw.edu.pl.rpc.RpcServer
import pw.edu.pl.solver.Cholesky
import pw.edu.pl.util.CholeskyExecutor
import pw.edu.pl.util.CholeskyFactory
import kotlin.system.measureTimeMillis

open class Application {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            try {
                val messageSize = 128 * 1024 * 1024

                if (args[0] == "server") {
                    val port = Integer.valueOf(args.getOrNull(1) ?: throw Exception("missing port for gRPC server"))
                    RpcServer(port, messageSize)
                    return
                }

                val matrix: Matrix = Matrix(args[0])
                val cholesky: Cholesky = CholeskyFactory.createByType(args[1])
                val rpcClient: RpcClient? = args.getOrNull(3)?.let { RpcClient(it, messageSize) }
                val executionTime = measureTimeMillis {
                    CholeskyExecutor.execute(cholesky, args[2], matrix, rpcClient)
                }
                println("execution time: $executionTime ms")
            }
            catch (e: Exception) {
                e.printStackTrace()
                println("error: ${e.message}");
            }
        }
    }
}
