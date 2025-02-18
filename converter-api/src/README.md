# PDF to Voice converter: Converter-API

Converter-API является микросервисом, составной частью проекта 
[PDF to Voice converter](https://github.com/he1ex-tG/PDF-to-Voice-Converter).

## Структура

Данный микросервис предоставляет третьим лицам, а также микросервисам из этого проекта 
[API](#1-api) для [конвертирования](#2-конвертер) PDF файлов в MP3.

### 1. API

Предоставляемое микросервисом API создано на базе [Spring Boot](https://spring.io/projects/spring-boot) и 
предлагает следующие end-point:

| __Тип запроса__ | __Путь__          | __MIME на входе__          | __MIME на выходе__         | __Описание__                                                                                         |
|-----------------|-------------------|----------------------------|----------------------------|------------------------------------------------------------------------------------------------------|
| POST            | /api/v1/converter | `application/octet-stream` | `application/octet-stream` |  Конвертировать `ByteArray` из тела запроса, содержащий PDF файл, в `ByteArray`, содержащий MP3 файл |

Единственного end-point достаточно, чтобы данный микросервис мог быть применен в рамках проекта. В целях 
расширения функциональных возможностей, неплохо смотрится вариант с добавлением end-point для 
конвертирования текста, а также усложнения структуры тела запроса или добавления дополнительных параметров,
передаваемых при обращении к API (например, указание скорости воспроизведения, мужской/женский голос и 
т.д.).  

Все end-point не защищены посредством использования 
[Spring Security](https://spring.io/projects/spring-security), поэтому являются
общедоступными. Такой подход выбран в первую очередь затем, чтобы показать гибкость микросервисной 
архитектуры. К данному модулю без авторизации могут обращаться как другие модули проекта 
([PDF to Voice converter: Processor](https://github.com/he1ex-tG/PDF-to-Voice-Converter/tree/master/processor)), 
так и третьи лица согласно своим потребностям.

### 2. Конвертер

Converting a PDF file into text (array of bytes) is made using the
[ITextPDF](https://itextpdf.com/) library. Then the text is converted by
the [FreeTTS](https://freetts.sourceforge.io/) library.

> __Note__
>
> By default, FreeTTS does not provide the ability to
> output the audio stream as a `ByteArrayInputStream` or `ByteArray`, for example.
> So, I made my own implementation of the `AudioPlayer` interface.
> This approach makes it possible to avoid intermediate saving of
> audio data to a file, as well as to hot convert from WAV to MP3 using
> [Lame](https://lame.sourceforge.io/).

### 3. Exception handling

As mentioned above, ConverterAPI is an internal microservice, so it is assumed that the
correct data will be passed in the request body, i.e. not null `TransferData` object,
not blank `TransferData.content`, etc. However, errors may occur if `TransferData.content`
will contain a non-pdf file or empty string, for example. For such cases, exception handling
is provided by extending `ResponseEntityExceptionHandler` class, using of the
`@ControllerAdvice` and `@ExceptionHandler` annotations.

The entire list of `ITextPDF` module exceptions (`BadPasswordException`,
`IllegalPdfSyntaxException`, `InvalidPdfException`, `UnsupportedPdfException`) and one
custom exception (`TtsEmptyStringException`) are handled in the project.

As a result of exception handling, the corresponding `RequestEntity` object is generated
and sent to the consumer.

### 4. Tests

Functional tests of both the API and the converter are located in `./src/test`
directory.

## Build Instructions

To successfully build the project, you should first compile Lame yourself. After
compilation, it will be placed in the Maven local repository (included in the
`repositories` section in the `build.gradle.kts` file). Compilation instructions
and source codes can be found on the [Lame](https://lame.sourceforge.io/) website.

The project is built after the dependency issues are resolved. For example,

from command line (in project root directory)

    # ./gradlew bootJar

or manually run build task `bootJar` from IDE.

The compiled Jar is in `./build/libs/ConverterAPI-[version]-SNAPSHOT.jar`.

## Usage

Here:
- [host] is the host where the project is running, [host] = `localhost` by
  default.
- [port] is the port. It can be changed in the `application.yaml` settings
  file. [port] = `8082` by default.

Files conversion:



    # curl -X POST -H "Content-type: application/json" -d @C:/hw.txt -o C:/hw.dto http://[host]:[port]/api/v1/file

Text conversion:

File `hw.txt`

    {
      "content": [72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33]
    }

Command

    # curl -X POST -H "Content-type: application/json" -d @C:/hw.txt -o C:/hw.dto http://[host]:[port]/api/v1/text

## TODO

- [x] Conversion process
    - [x] PDF file to text
    - [x] Text to audio format (WAV by default)
        - [x] WAV to MP3
        - [ ] Male/female voice choosing
- [x] REST API endpoints
    - [x] File conversion
    - [x] Text conversion

## Technologies Used:

1. [Spring Boot](https://spring.io/projects/spring-boot)
2. [ITextPDF](https://itextpdf.com/)
3. [FreeTTS](https://freetts.sourceforge.io/)
4. [Lame](https://lame.sourceforge.io/)
