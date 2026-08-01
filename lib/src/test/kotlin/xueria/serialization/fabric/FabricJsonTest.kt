@file:Suppress("KotlinPrintToLogpoint")

package xueria.serialization.fabric

import kotlinx.serialization.json.Json
import kotlin.test.Test

class FabricJsonTest {

    private val encoder = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        prettyPrint = true
    }

    // language=json
    private val json = """
        {
          "schemeVersion": 1,
          "id": "test",
          "version": "1.0.0",
          "name": "Test",
          "environment": "*",
          "entrypoints": {
            "main": [
              "test.A",
              {
                "adapter": "kotlin",
                "value": "test.B"
              }
            ],
            "custom": ["bb.C"]
          },
          "depends": {
            "fabricloader": ">=0.19.1",
            "minecraft": "~26.2",
            "java": ">=25",
            "fabric-api": "*"
          },
          "accessWidener": "test.accesswidener"
        }
    """

    private val fabric = FabricJson(
        schemeVersion = 1,
        id = "test",
        version = "1.0.0",
        name = "Test",
        environment = Environment.CLIENT,
        entrypoints = mapOf(
            "main" to listOf(
                EntrypointClassName(
                    className = "test.A"
                ),
                EntrypointAdapter(
                    adapter = "kotlin",
                    className = "test.B",
                )
            )
        ),
        depends = mapOf(
            "fabricloader" to ">=0.19.1"
        ),
        accessWidener = "test.accesswidener"
    )

    @Test
    fun `fabric mod json serializer`() {
        println(encoder.encodeToString(fabric))
    }

    @Test
    fun `fabric mod json deserializer`() {
        println(encoder.decodeFromString<FabricJson>(json))
    }

}