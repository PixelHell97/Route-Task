package com.pixel.data

import com.google.gson.Gson
import com.pixel.data.api.model.Response
import com.pixel.domain.common.InternetConnectionError
import com.pixel.domain.common.Resource
import com.pixel.domain.common.ServerError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

suspend fun <T> executeApi(apiCall: suspend () -> T): T {
    try {
        val response = apiCall.invoke()
        return response
    } catch (ex: HttpException) {
        if (ex.code() in 400..600) {
            val serverResponse = ex.response()?.errorBody()?.string()
            val response = Gson().fromJson(serverResponse, Response::class.java)
            throw ServerError(
                response.message,
                response.statusMsg,
                httpEx = ex,
            )
        }
        throw ex
    } catch (ex: IOException) {
        throw InternetConnectionError(ex)
    } catch (ex: TimeoutException) {
        throw InternetConnectionError(ex)
    } catch (ex: Exception) {
        throw ex
    }
}

suspend fun <T> toFlow(getData: suspend () -> T): Flow<Resource<T>> =
    flow {
        emit(Resource.Loading)
        val response = getData.invoke()
        emit(Resource.Success(response))
    }.flowOn(Dispatchers.IO)
        .catch { ex ->
            when (ex) {
                is ServerError -> {
                    emit(Resource.ServerFail(ex))
                }

                is InternetConnectionError -> {
                    emit(Resource.Fail(ex))
                }

                else -> emit(Resource.Fail(ex))
            }
        }
