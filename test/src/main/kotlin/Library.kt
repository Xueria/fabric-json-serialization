@file:JvmName("Library")
@file:Suppress("KotlinPrintToLogpoint")

import kotlinx.serialization.json.Json
import xueria.serialization.fabric.FabricJson

fun main() {
    val encoder = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        prettyPrint = true
    }

    val fabric = FabricJson(
        schemeVersion = 1,
        id = "test",
        version = "1.0.0",
    )

    println(encoder.encodeToString(fabric))
}