package pw.edu.pl.util

import pw.edu.pl.solver.Cholesky
import pw.edu.pl.solver.CholeskyColumn
import pw.edu.pl.solver.CholeskyRow

class CholeskyFactory {

    companion object {
        fun createByType(type: String): Cholesky {
            return when (type) {
                "row" -> CholeskyRow()
                "column" -> CholeskyColumn()
                else -> throw Exception("could not create Cholesky instance for type = $type")
            }
        }
    }
}