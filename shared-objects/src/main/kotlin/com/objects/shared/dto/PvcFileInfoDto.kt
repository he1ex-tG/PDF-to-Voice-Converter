package com.objects.shared.dto

open class PvcFileInfoDto(
    open val id: String,
    open val filename: String
) {

    override fun toString(): String {
        return "PvcFileInfoDto instance: id = $id, filename = $filename"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PvcFileInfoDto) return false
        if ((id == other.id) && (filename == other.filename)) return true
        return false
    }

    override fun hashCode(): Int {
        return 31 * id.hashCode() + 31 * filename.hashCode()
    }
}