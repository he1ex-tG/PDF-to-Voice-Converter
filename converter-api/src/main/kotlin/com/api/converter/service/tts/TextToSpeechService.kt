package com.api.converter.service.tts

interface TextToSpeechService {

    fun speech(text: String): ByteArray
}