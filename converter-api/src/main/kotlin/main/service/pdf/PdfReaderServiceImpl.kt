package main.service.pdf

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy
import org.springframework.stereotype.Service

@Service
class PdfReaderServiceImpl : PdfReaderService {
    override fun getText(pdf: ByteArray): String {
        val pr = PdfReader(pdf)
        val sb = StringBuilder()
        for (page in 1..pr.numberOfPages) {
            sb.append(PdfTextExtractor.getTextFromPage(pr, page, SimpleTextExtractionStrategy()))
        }
        return sb.toString()
    }
}