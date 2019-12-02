package pw.edu.pl

class CholeskyFactory {

    companion object {
        fun createByType(type: String): Cholesky {
            return when (type) {
                "row" -> CholeskyRow()
                "column" -> CholeskyColumn()
                else -> throw Exception("could not create pw.edu.pl.Cholesky instance for type = $type")
            }
        }
    }
}