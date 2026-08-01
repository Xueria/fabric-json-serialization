package xueria.serialization.fabric

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
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

@Serializable(with = Mixin.Serializer::class)
sealed class Mixin {

    internal object Serializer : JsonContentPolymorphicSerializer<Mixin>(Mixin::class) {

        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Mixin> {
            return when (element) {
                is JsonPrimitive -> MixinSimple.serializer()
                is JsonObject -> MixinDetail.serializer()
                else -> error("Unsupported Entrypoint: $element")
            }
        }

    }

}

@Serializable(with = MixinSimple.Serializer::class)
data class MixinSimple(val config: String) : Mixin() {

    internal object Serializer : KSerializer<MixinSimple> {

        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Mixin.MixinSimple", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: MixinSimple) {
            encoder.encodeString(value.config)
        }

        override fun deserialize(decoder: Decoder): MixinSimple {
            return MixinSimple(decoder.decodeString())
        }
    }

}

@Serializable
data class MixinDetail(
    val config: String,
    val environment: Environment
) : Mixin()