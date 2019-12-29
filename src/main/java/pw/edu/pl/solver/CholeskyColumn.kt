package pw.edu.pl.solver

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pw.edu.pl.domain.Matrix
import pw.edu.pl.rpc.RpcClient
import kotlin.math.sqrt

class CholeskyColumn : Cholesky {

    override fun solveSequential(matrix: Matrix): Matrix {
        val result = Matrix(matrix.rows, matrix.columns)
        for (column in 0 until matrix.columns - 1) {
            result[column, column] = sqrt(matrix[column, column])
            for (row in column + 1 until matrix.rows) {
                result[row, column] = matrix[row, column] / result[column, column]
            }
            for (columnModify in column + 1 until matrix.columns) {
                for (rowModify in columnModify until matrix.rows) {
                    matrix[rowModify, columnModify] = matrix[rowModify, columnModify] - (result[rowModify, column] * result[columnModify, column])
                }
            }
        }
        result[matrix.rows - 1, matrix.columns - 1] = sqrt(matrix[matrix.rows - 1, matrix.columns - 1])
        return result;
    }

    override fun solveThreads(matrix: Matrix): Matrix {
        val result = Matrix(matrix.rows, matrix.columns)
        for (column in 0 until matrix.columns - 1) {
            result[column, column] = sqrt(matrix[column, column])
            for (row in column + 1 until matrix.rows) {
                result[row, column] = matrix[row, column] / result[column, column]
            }
            runBlocking {
                for (columnModify in column + 1 until matrix.columns) {
                    launch(Dispatchers.Default) {
                        for (rowModify in columnModify until matrix.rows) {
                            matrix[rowModify, columnModify] = matrix[rowModify, columnModify] - (result[rowModify, column] * result[columnModify, column])
                        }
                    }
                }
            }
        }
        result[matrix.rows - 1, matrix.columns - 1] = sqrt(matrix[matrix.rows - 1, matrix.columns - 1])
        return result;
    }

    override fun solveRpc(matrix: Matrix, rpcClient: RpcClient): Matrix {
        return rpcClient.solveColumn(matrix)
    }
}