package an.example.randomchat.common

data class ApiResponse(
    val success: Boolean,
    val data: Any? = null,
    val message: String? = null
){
    companion object{
        fun ok(data: Any?) = ApiResponse(true,data)

        fun error(message: String? = null) = ApiResponse(false, message = message)

    }

}
