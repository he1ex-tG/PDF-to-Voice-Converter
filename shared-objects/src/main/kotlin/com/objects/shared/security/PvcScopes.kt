package com.objects.shared.security

class PvcScopes {

    class AUTH {
        companion object {
            val AUTH = "auth:auth"
            val WRITE = "auth:write"
        }
    }

    class USER {
        companion object {
            val READ = "user:read"
            val WRITE = "user:write"
        }
    }

    class FILE {
        companion object {
            val READ = "file:read"
            val WRITE = "file:write"
        }
    }
}