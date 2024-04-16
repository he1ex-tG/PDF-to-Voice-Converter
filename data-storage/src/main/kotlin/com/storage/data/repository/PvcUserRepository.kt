package com.storage.data.repository

import com.storage.data.entity.PvcUser
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface PvcUserRepository : CrudRepository<PvcUser, String> {

    fun findByUsername(username: String): Optional<PvcUser>
}