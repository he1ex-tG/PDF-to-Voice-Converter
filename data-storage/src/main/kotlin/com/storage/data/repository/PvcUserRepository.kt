package com.storage.data.repository

import com.storage.data.entity.PvcUser
import org.springframework.data.repository.CrudRepository

interface PvcUserRepository : CrudRepository<PvcUser, String>