package rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val message: String = "") : Resource<T>()
    data class Error<out T>(val error: String, val data: T? = null): Resource<T>()
}