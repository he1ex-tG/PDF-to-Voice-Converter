plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "pdf-to-voice-converter"

include("data-storage")
include("converter-api")
include("user-interface")
include("shared-objects")
include("processor")
