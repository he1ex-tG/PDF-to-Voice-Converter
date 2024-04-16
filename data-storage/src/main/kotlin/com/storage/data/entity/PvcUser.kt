package com.storage.data.entity

import com.objects.shared.dto.PvcUserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "PVC_user")
class PvcUser(
    @Id
    var id: String? = null,
    @Indexed(unique = true)
    val username: String,
    val password: String
) {

    constructor(pvcUserDto: PvcUserDto) : this(null, pvcUserDto.username, pvcUserDto.password)

    fun toPvcUserDto(): PvcUserDto = PvcUserDto(username, password)

    override fun toString(): String {
        return "PvcUser instance: id = ${id ?: "null"}, username = $username, " +
                "password = $password"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PvcUser) return false
        if ((id == other.id) && (username == other.username) && (password == other.password)) return true
        return false
    }

    override fun hashCode(): Int {
        return 31 * username.hashCode() + 31 * password.hashCode()
    }
}