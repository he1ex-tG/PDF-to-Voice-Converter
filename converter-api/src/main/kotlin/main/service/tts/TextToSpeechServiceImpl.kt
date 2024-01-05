package main.service.tts

import com.sun.speech.freetts.Voice
import com.sun.speech.freetts.VoiceManager
import org.springframework.stereotype.Service

@Service
class TextToSpeechServiceImpl : TextToSpeechService {

    private lateinit var voice: Voice

    private val mp3StreamWrapper = MP3StreamWrapper()
    private val inputBytes: ByteArray
        get() = mp3StreamWrapper.inputBytes

    private val mp3StreamAudioPlayer = MP3StreamAudioPlayer(mp3StreamWrapper)

    init {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory")
        val voiceManager = VoiceManager.getInstance()
        voice = voiceManager.getVoice("kevin16")
        voice.allocate()
    }

    override fun speech(text: String): ByteArray {
        voice.audioPlayer = mp3StreamAudioPlayer
        voice.speak(text)
        return inputBytes
    }
}