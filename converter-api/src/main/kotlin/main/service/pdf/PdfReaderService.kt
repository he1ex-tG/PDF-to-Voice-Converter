package main.service.pdf

interface PdfReaderService {

    fun getText(pdf: ByteArray): String
}