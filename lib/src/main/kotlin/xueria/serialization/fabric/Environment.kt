package xueria.serialization.fabric

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = EnvironmentSerializer::class)
enum class Environment(val env: String) {
    BOTH(env = "*"),
    CLIENT(env = "client"),
    SERVER(env = "server");

    companion object {

        fun fromString(name: String): Environment {
            return when (name) {
                "*" -> BOTH
                "client" -> CLIENT
                "server" -> SERVER
                else -> error("Unknown environment $name")
            }
        }

    }
}

internal object EnvironmentSerializer : KSerializer<Environment> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Environment", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Environment) {
        encoder.encodeString(value.env)
    }

    override fun deserialize(decoder: Decoder): Environment {
        val env = decoder.decodeString()
        return Environment.fromString(env)
    }

}