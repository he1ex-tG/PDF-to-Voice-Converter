package com.objects.shared.dto

open class PvcUserInfoDto(
    open val id: String,
    open val username: String,
    open val password: String
) {

    override fun toString(): String {
        return "PvcUserInfoDto instance: id = $id, username = $username, password = $password"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PvcUserInfoDto) return false
        if ((id == other.id) && (username == other.username) && (password == other.password)) return true
        return false
    }

    override fun hashCode(): Int {
        return 31 * id.hashCode() + 31 * username.hashCode() + 31 * password.hashCode()
    }
}