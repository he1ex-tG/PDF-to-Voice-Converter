package com.objects.shared.configuration

class FieldAuthServer(
    val oAuth2Client: SubfieldOAuth2Client,
    val address: String,
    val port: String,
    val appName: String
)