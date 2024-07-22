package com.storage.data.entity

import com.objects.shared.dto.PvcUserDto
import com.objects.shared.dto.PvcUserInfoDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "PVC_user")
class PvcUser(
    @Id
    var id: String? = null,
    @Indexed(unique = true)
    val username: String,
    val password: String,
    @Indexed(unique = true)
    val email: String
) {

    constructor(pvcUserDto: PvcUserDto) : this(null, pvcUserDto.username, pvcUserDto.password, pvcUserDto.email)

    fun toPvcUserDto(): PvcUserDto = PvcUserDto(username, password, email)

    fun toPvcUserInfoDto(): PvcUserInfoDto = PvcUserInfoDto(
        id ?: throw NullPointerException("Can't convert PvcUser to PvcUserInfoDto because PvcUser.id is null"),
        username,
        password
    )

    override fun toString(): String {
        return "PvcUser instance: id = ${id ?: "null"}, username = $username, " +
                "password = $password, email = $email"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PvcUser) return false
        if ((id == other.id) && (username == other.username) && (password == other.password) && (email == other.email)) return true
        return false
    }

    override fun hashCode(): Int {
        return 31 * username.hashCode() + 31 * password.hashCode() + 31 * email.hashCode()
    }
}