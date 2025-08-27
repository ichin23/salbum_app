package com.ichin23.salbum.data.api.dto.salbum.user

data class LoginDTO(
    val access_token: Token,
    val token_type: String,
    val refresh_token: Token,
    val user: UserDTO
){
    override fun toString(): String {
        return """
            LoginDTO(\n
                access_token: $access_token,\n
                token_type: $token_type,\n
                refresh_token: $refresh_token,\n
                user: $user,\n
            )
        """.trimIndent()
    }
}
