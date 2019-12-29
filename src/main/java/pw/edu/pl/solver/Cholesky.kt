package pw.edu.pl.solver

import pw.edu.pl.domain.Matrix
import pw.edu.pl.rpc.RpcClient

interface Cholesky {
    fun solveSequential(matrix: Matrix): Matrix
    fun solveThreads(matrix: Matrix): Matrix
    fun solveRpc(matrix: Matrix, rpcClient: RpcClient): Matrix
}