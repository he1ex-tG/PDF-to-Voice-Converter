package main.repository

import main.entity.PvcUser
import org.springframework.data.repository.CrudRepository

interface PvcUserRepository : CrudRepository<PvcUser, String>