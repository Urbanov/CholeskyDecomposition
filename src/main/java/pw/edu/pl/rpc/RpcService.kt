package pw.edu.pl.rpc

import com.google.protobuf.ByteString
import kotlinx.coroutines.asCoroutineDispatcher
import org.apache.commons.lang3.SerializationUtils
import pw.edu.pl.CholeskySolverImplBase
import pw.edu.pl.ProtobufMatrix
import pw.edu.pl.domain.Matrix
import pw.edu.pl.solver.CholeskyColumn
import pw.edu.pl.solver.CholeskyRow
import java.util.concurrent.Executors.newSingleThreadExecutor

class RpcService : CholeskySolverImplBase(coroutineContext = newSingleThreadExecutor().asCoroutineDispatcher()) {
    private val rowService = CholeskyRow()
    private val columnService = CholeskyColumn()

    companion object {
        fun toProtobuf(matrix: Matrix): ProtobufMatrix {
            return ProtobufMatrix.newBuilder()
                .setData(ByteString.copyFrom(SerializationUtils.serialize(matrix)))
                .build()
        }

        fun fromProtobuf(protobuf: ProtobufMatrix): Matrix {
            return SerializationUtils.deserialize(protobuf.data.toByteArray())
        }
    }

    override suspend fun solveRow(request: ProtobufMatrix): ProtobufMatrix {
        return solve(request, rowService::solveThreads)
    }

    override suspend fun solveColumn(request: ProtobufMatrix): ProtobufMatrix {
        return solve(request, columnService::solveThreads)
    }

    private fun solve(request: ProtobufMatrix, solver: (Matrix) -> Matrix): ProtobufMatrix {
        return toProtobuf(solver.invoke(fromProtobuf(request)))
    }
}