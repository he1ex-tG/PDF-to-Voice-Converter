package main.repository

import main.entity.PvcFile
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface PvcFileRepository : CrudRepository<PvcFile, String> {

    fun findAllByPvcUserId(pvcUserId: String): Iterable<PvcFile>
    fun findByIdAndPvcUserId(id: String, pvcUserId: String): Optional<PvcFile>
}