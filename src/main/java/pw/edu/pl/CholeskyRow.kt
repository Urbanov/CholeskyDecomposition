package pw.edu.pl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.pow
import kotlin.math.sqrt

class CholeskyRow : Cholesky {

    override fun solveSequential(matrix: Matrix): Matrix {
        val result = Matrix(matrix.rows, matrix.columns)
        for (column in 0 until matrix.columns) {
            result[column, column] = calculateOuterLoopValue(matrix, result, column)
            for (row in column + 1 until matrix.rows) {
                result[row, column] = calculateInnerLoopValue(matrix, result, row, column)
            }
        }
        return result;
    }

    override fun solveThreads(matrix: Matrix): Matrix {
        val result = Matrix(matrix.rows, matrix.columns)
        for (column in 0 until matrix.columns) {
            result[column, column] = calculateOuterLoopValue(matrix, result, column)
            runBlocking {
                for (row in column + 1 until matrix.rows) {
                    launch(Dispatchers.Default) {
                        result[row, column] = calculateInnerLoopValue(matrix, result, row, column)
                    }
                }
            }
        }
        return result;
    }

    private fun calculateOuterLoopValue(source: Matrix, target: Matrix, column: Int): Double {
        val sum = (0 until column)
            .map { target[column, it].pow(2) }
            .sum()

        return sqrt(source[column, column] - sum)
    }

    private fun calculateInnerLoopValue(source: Matrix, target: Matrix, row: Int, column: Int): Double {
        val sum = (0 until column)
            .map { target[row, it] * target[column, it] }
            .sum()

        return (source[row, column] - sum) / target[column, column]
    }
}