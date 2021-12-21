package com.example.downloadfiles.base


class Resource<T>(
    val value: T? = null,
    val t: Throwable? = null,
    val state: State,

    ) {
    companion object {
        enum class State {
            LOADING,
            ERROR,
            SUCCESS
        }

        fun <T> loading(): Resource<T> {
            return Resource(state = State.LOADING)
        }

        fun <T> success(value: T): Resource<T> {
            return Resource(state = State.SUCCESS, value = value)
        }

        fun <T> error(t: Throwable): Resource<T> {
            return Resource(state = State.ERROR, t = t)
        }
    }

}