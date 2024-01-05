package main.service

import main.service.pdf.PdfReaderService
import main.service.tts.TextToSpeechService
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