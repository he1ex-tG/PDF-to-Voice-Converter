package com.api.converter.service.tts

import com.sun.speech.freetts.audio.AudioPlayer
import net.sourceforge.lame.lowlevel.LameEncoder
import net.sourceforge.lame.mp3.Lame
import net.sourceforge.lame.mp3.MPEGMode
import java.io.*
import java.util.*
import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem

class MP3StreamAudioPlayer(
    private val mp3StreamWrapper: MP3StreamWrapper
) : AudioPlayer {

    private val defaultFormat: AudioFormat = AudioFormat(
        AudioFormat.Encoding.PCM_FLOAT,
        16000.0F,
        16,
        1,
        2,
        16000.0F,
        true
    )
    private var currentFormat: AudioFormat = defaultFormat
    private var outputData: ByteArray = byteArrayOf()
    private var curIndex = 0
    private var totBytes = 0
    private val outputType: AudioFileFormat.Type = AudioFileFormat.Type.WAVE
    private val outputList: Vector<InputStream> = Vector<InputStream>()

    private fun encodeToMp3(audioInputStream: AudioInputStream): ByteArray {
        val encoder = LameEncoder(
            audioInputStream.format,
            256,
            MPEGMode.STEREO,
            Lame.QUALITY_HIGHEST,
            false
        )
        val mp3 = ByteArrayOutputStream()
        val inputBuffer = ByteArray(encoder.pcmBufferSize)
        val outputBuffer = ByteArray(encoder.pcmBufferSize)
        var bytesRead: Int
        var bytesWritten: Int
        while (0 < audioInputStream.read(inputBuffer).also { bytesRead = it }) {
            bytesWritten = encoder.encodeBuffer(inputBuffer, 0, bytesRead, outputBuffer)
            mp3.write(outputBuffer, 0, bytesWritten)
        }
        encoder.close()
        return mp3.toByteArray()
    }

    @Synchronized
    override fun setAudioFormat(format: AudioFormat) {
        currentFormat = format
    }

    override fun getAudioFormat(): AudioFormat {
        return currentFormat
    }

    override fun pause() {}

    @Synchronized
    override fun resume() {
    }

    @Synchronized
    override fun cancel() {
    }

    @Synchronized
    override fun reset() {
    }

    override fun startFirstSampleTimer() {}

    @Synchronized
    override fun close() {}

    override fun getVolume(): Float {
        return 1.0f
    }

    override fun setVolume(volume: Float) {}

    override fun begin(size: Int) {
        outputData = ByteArray(size)
        curIndex = 0
    }

    override fun end(): Boolean {
        outputList.add(ByteArrayInputStream(outputData))
        totBytes += outputData.size
        return true
    }

    override fun drain(): Boolean {
        val inputStream: InputStream = SequenceInputStream(outputList.elements())
        val audioInputStream = AudioInputStream(inputStream, currentFormat, (totBytes / currentFormat.frameSize).toLong())
        val waveOutputStream = ByteArrayOutputStream()
        AudioSystem.write(audioInputStream, outputType, waveOutputStream)
        val waveAudioInputStream = AudioSystem.getAudioInputStream(waveOutputStream.toByteArray().inputStream())
        mp3StreamWrapper.set(encodeToMp3(waveAudioInputStream))
        return true
    }

    @Synchronized
    override fun getTime(): Long {
        return -1L
    }

    @Synchronized
    override fun resetTime() {
    }

    override fun write(audioData: ByteArray): Boolean {
        return this.write(audioData, 0, audioData.size)
    }

    override fun write(bytes: ByteArray, offset: Int, size: Int): Boolean {
        System.arraycopy(bytes, offset, outputData, curIndex, size)
        curIndex += size
        return true
    }

    override fun toString(): String = ""

    override fun showMetrics() {}
}