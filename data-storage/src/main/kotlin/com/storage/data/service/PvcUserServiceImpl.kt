package com.storage.data.service

import com.objects.shared.dto.PvcUserDto
import com.storage.data.entity.PvcUser
import com.storage.data.exception.LoadPvcUserException
import com.storage.data.exception.LoadPvcUserNotFoundException
import com.storage.data.exception.SavePvcUserDuplicateException
import com.storage.data.exception.SavePvcUserException
import com.storage.data.repository.PvcUserRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class PvcUserServiceImpl(
    private val pvcUserRepository: PvcUserRepository
) : PvcUserService {

    override fun authPvcUser(username: String): PvcUserDto {
        val pvcUser = pvcUserRepository.findByUsername(username)
        try {
            return pvcUser.get().toPvcUserDto()
        } catch (ex: Throwable) {
            when (ex) {
                is NoSuchElementException -> {
                    throw LoadPvcUserNotFoundException("Auth user function thrown an exception with message: " +
                            "user with username = \"$username\" not found"
                    )
                }
                else -> {
                    throw LoadPvcUserException("Auth user function thrown an exception with message: " +
                            (ex.message ?: "No message")
                    )
                }
            }
        }
    }

    override fun savePvcUser(pvcUserDto: PvcUserDto): PvcUserDto {
        try {
            return pvcUserRepository.save(PvcUser(pvcUserDto)).toPvcUserDto()
        } catch (ex: Throwable) {
            when (ex) {
                is DuplicateKeyException -> {
                    val regex = """index: (\w+)""".toRegex()
                    val duplicateIndex = regex.find(ex.localizedMessage)?.groups?.get(1)?.value
                    throw SavePvcUserDuplicateException("Duplicate error, choose another \"${duplicateIndex ?: "{unknown index}"}\"")
                }
                else -> {
                    throw SavePvcUserException("Save user to repository function thrown an exception with message: " +
                            "${ex.message ?: "No message"}, user with username = ${pvcUserDto.username} is not saved")
                }
            }
        }
    }
}