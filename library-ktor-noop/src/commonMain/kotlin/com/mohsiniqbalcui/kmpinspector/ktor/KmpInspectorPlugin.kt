package com.mohsiniqbalcui.kmpinspector.ktor

import io.ktor.client.plugins.api.createClientPlugin

/** No-op twin of the real config; settable so host code compiles unchanged. */
class KmpInspectorPluginConfig {
    var maxBodyBytes: Int = 256 * 1024
}

/** No-op twin of the real plugin: installs without hooking any request. */
val KmpInspectorPlugin = createClientPlugin("KmpInspector", ::KmpInspectorPluginConfig) {}
