package com.objects.shared.configuration

class FieldAuthServer(
    val oAuth2Client: SubfieldOAuth2Client,
    val uri: String,
    val port: String,
    val appName: String
)