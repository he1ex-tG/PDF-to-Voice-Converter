package com.api.converter.service

import com.api.converter.service.pdf.PdfReaderService
import com.api.converter.service.tts.TextToSpeechService
import org.springframework.stereotype.Service

@Service
class ConverterServiceImpl(
    val pdfReaderService: PdfReaderService,
    val textToSpeechService: TextToSpeechService
) : ConverterService {

    override fun convert(data: ByteArray): ByteArray {
        val text = pdfReaderService.getText(data)
        return textToSpeechService.speech(text)
    }
}