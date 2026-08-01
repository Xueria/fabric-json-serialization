package xueria.serialization.fabric

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@Serializable(with = Entrypoint.Serializer::class)
sealed class Entrypoint {

    internal object Serializer : JsonContentPolymorphicSerializer<Entrypoint>(Entrypoint::class) {

        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Entrypoint> {
            return when (element) {
                is JsonPrimitive -> EntrypointClassName.serializer()
                is JsonObject -> EntrypointAdapter.serializer()
                else -> error("Unsupported Entrypoint: $element")
            }
        }

    }

}

@Serializable(with = EntrypointClassName.Serializer::class)
data class EntrypointClassName(val className: String) : Entrypoint() {

    internal object Serializer : KSerializer<EntrypointClassName> {

        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Entrypoint.ClassName", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: EntrypointClassName) {
            encoder.encodeString(value.className)
        }

        override fun deserialize(decoder: Decoder): EntrypointClassName {
            return EntrypointClassName(decoder.decodeString())
        }
    }

}

@Serializable
data class EntrypointAdapter(
    val adapter: String,
    @SerialName("value")
    val className: String
) : Entrypoint()