package com.api.converter.service.pdf

interface PdfReaderService {

    fun getText(pdf: ByteArray): String
}