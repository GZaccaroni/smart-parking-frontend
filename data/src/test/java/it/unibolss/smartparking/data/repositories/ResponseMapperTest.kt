package it.unibolss.smartparking.data.repositories

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class ResponseMapperTest {

    @Test
    fun testSuccessCase() = runTest {
        val sampleResult = "sample"

        val either = apiCall {
            sampleResult
        }
        assertTrue(either.isRight())
        assertEquals(sampleResult, (either as Either.Right).value)
    }

    @Test
    fun testErrorCase() = runTest {
        val response = Response.error<String>(500, sampleErrorResponseBody())

        val either = apiCall<Int> {
            throw HttpException(response)
        }
        assertTrue(either.isLeft())
        assertEquals(AppError.Unauthorized, (either as Either.Left).value)
    }

    private fun sampleErrorResponseBody(): ResponseBody {
        val sampleResponse =
            """
                {
                    "errorCode": "Unauthorized"
                }
            """
        val responseMediaType = MediaType.get("application/json")
        return ResponseBody.create(responseMediaType, sampleResponse)
    }
}
