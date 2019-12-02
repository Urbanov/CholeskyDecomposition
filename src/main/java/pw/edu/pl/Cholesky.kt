package pw.edu.pl

interface Cholesky {
    fun solveSequential(matrix: Matrix): Matrix
    fun solveThreads(matrix: Matrix): Matrix
}