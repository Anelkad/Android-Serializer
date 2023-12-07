import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = PolymorphicItem.Serializer::class)
sealed interface PolymorphicItem {
    val polymorphicType: String

    @Serializable
    data class CarDTO(
        val carName: String,
        val carModel: Int,
        val carColor: String,
    ) : PolymorphicItem {
        override val polymorphicType: String
            get() = "CAR"
        fun asDomainModel() {}
    }
    @Serializable
    data class PersonDTO(
        val personName: String,
        val personAge: Int,
        val personAddress: String,
        val personGender: String
    ) : PolymorphicItem {
        override val polymorphicType: String
            get() = "PERSON"
        fun asDomainModel() {}
    }

    object Serializer : KSerializer<PolymorphicItem> {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
            useArrayPolymorphism = true
        }
        override val descriptor: SerialDescriptor
            get() = PolymorphicSerializer(PolymorphicItem::class).descriptor
        override fun serialize(encoder: Encoder, value: PolymorphicItem) {
            when (value) {
                is CarDTO -> encoder.encodeSerializableValue(CarDTO.serializer(), value)
                is PersonDTO -> encoder.encodeSerializableValue(PersonDTO.serializer(), value)
                else -> {}
            }
        }

        override fun deserialize(decoder: Decoder): PolymorphicItem {
            val jsonElement = json.parseToJsonElement(decoder.decodeString())
            return when (val itemType = jsonElement.jsonObject["polymorphicType"]?.jsonPrimitive?.content) {
                "CAR" -> json.decodeFromJsonElement(CarDTO.serializer(), jsonElement)
                "PARSON" -> json.decodeFromJsonElement(PersonDTO.serializer(), jsonElement)
                else -> throw SerializationException("Unknown itemType: $itemType")
            }
        }
    }
}