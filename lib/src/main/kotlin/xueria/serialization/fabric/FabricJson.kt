package xueria.serialization.fabric

import kotlinx.serialization.Serializable

@Serializable
data class FabricJson(
    val schemeVersion: Int,
    val id: String,
    val version: String,

    val name: String? = null,
    val icon: String? = null,

    val environment: Environment = Environment.BOTH,

    val entrypoints: Map<String, List<Entrypoint>> = mapOf(),

    val depends: Map<String, String> = mapOf(),

    val accessWidener: String? = null,
)