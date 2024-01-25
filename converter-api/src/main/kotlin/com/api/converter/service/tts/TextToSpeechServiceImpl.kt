package com.api.converter.service.tts

import com.api.converter.exception.TtsEmptyStringException
import com.sun.speech.freetts.Voice
import com.sun.speech.freetts.VoiceManager
import org.springframework.stereotype.Service

@Service
class TextToSpeechServiceImpl : TextToSpeechService {

    private lateinit var voice: Voice

    private val mp3StreamWrapper = MP3StreamWrapper()
    private val mp3StreamAudioPlayer = MP3StreamAudioPlayer(mp3StreamWrapper)

    init {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory")
        val voiceManager = VoiceManager.getInstance()
        voice = voiceManager.getVoice("kevin16")
        voice.allocate()
    }

    override fun speech(text: String): ByteArray {
        if (text.isBlank()) {
            throw TtsEmptyStringException("Text is empty or consists solely of whitespace characters")
        }
        voice.audioPlayer = mp3StreamAudioPlayer
        voice.speak(text)
        val inputBytes = mp3StreamWrapper.getErase()
        return inputBytes
    }
}