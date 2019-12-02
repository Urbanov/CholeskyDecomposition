package pw.edu.pl

class CholeskyExecutor {

    companion object {
        fun execute(cholesky: Cholesky, type: String, matrix: Matrix): Matrix {
            return when (type) {
                "sequential" -> cholesky.solveSequential(matrix)
                "threads" -> cholesky.solveThreads(matrix)
                else -> throw Exception("could not execute pw.edu.pl.Cholesky solver for type = $type")
            }
        }
    }
}