package com.storage.data.main.repository

import com.storage.data.main.entity.PvcUser
import org.springframework.data.repository.CrudRepository

interface PvcUserRepository : CrudRepository<PvcUser, String>