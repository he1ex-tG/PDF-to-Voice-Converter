package main.service.tts

interface TextToSpeechService {

    fun speech(text: String): ByteArray
}