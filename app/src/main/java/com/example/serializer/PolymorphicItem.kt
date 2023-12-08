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
    data class Car(
        val carName: String,
        val carModel: Int,
        val carColor: String,
        val imageUrl: String
    ) : PolymorphicItem {
        override val polymorphicType: String
            get() = "CAR"
    }
    @Serializable
    data class Person(
        val personName: String,
        val personAge: Int,
        val personAddress: String,
        val personGender: String,
        val imageUrl: String
    ) : PolymorphicItem {
        override val polymorphicType: String
            get() = "PERSON"
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
                is Car -> encoder.encodeSerializableValue(Car.serializer(), value)
                is Person -> encoder.encodeSerializableValue(Person.serializer(), value)
                else -> throw SerializationException("Unknown itemType: $value")
            }
        }

        override fun deserialize(decoder: Decoder): PolymorphicItem {
            val jsonElement = (decoder as JsonDecoder).decodeJsonElement()
            return when (val itemType = jsonElement.jsonObject["polymorphicType"]?.jsonPrimitive?.content) {
                "CAR" -> json.decodeFromJsonElement(Car.serializer(), jsonElement)
                "PERSON" -> json.decodeFromJsonElement(Person.serializer(), jsonElement)
                else -> throw SerializationException("Unknown itemType: $itemType")
            }
        }
    }
}