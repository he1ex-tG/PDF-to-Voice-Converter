package com.api.converter.service.tts

class MP3StreamWrapper {
    private var _inputBytes: ByteArray = byteArrayOf()

    fun get(): ByteArray = _inputBytes

    fun getErase(): ByteArray {
        val byteArray = _inputBytes
        _inputBytes = byteArrayOf()
        return byteArray
    }

    fun erase(): ByteArray {
        _inputBytes = byteArrayOf()
        return _inputBytes
    }

    fun set(byteArray: ByteArray): ByteArray {
        _inputBytes = byteArray
        return _inputBytes
    }

}