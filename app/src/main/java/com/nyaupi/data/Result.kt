package com.nyaupi.data


enum class Status { SUCCESS, IN_PROGRESS, ERROR }

data class Result<T>(val status: Status,
                     val data: T? = null,
                     val error: Throwable? = null) {

    fun isInProgress() = status == Status.IN_PROGRESS
    fun isSuccess() = status == Status.SUCCESS
    fun isError() = status == Status.ERROR
}

