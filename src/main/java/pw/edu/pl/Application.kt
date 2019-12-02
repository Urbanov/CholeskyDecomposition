package pw.edu.pl

import java.lang.Exception
import kotlin.system.measureTimeMillis

open class Application {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            try {
                val matrix = Matrix(args[0])
                val cholesky: Cholesky = CholeskyFactory.createByType(args[1])
                val executionTime = measureTimeMillis {
                    CholeskyExecutor.execute(cholesky, args[2], matrix)
                }
                print("execution time: $executionTime ms")
            }
            catch (e: Exception) {
                print("error: ${e.message}");
            }
        }
    }
}
