import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = PolymorphicItem.Serializer::class)
sealed interface PolymorphicItem {

    @Serializable
    data class Car(
        val polymorphicType: String,
        val carName: String,
        val carModel: Int,
        val carColor: String,
        val imageUrl: String,
    ) : PolymorphicItem
    @Serializable
    data class Person(
        val polymorphicType: String,
        val personName: String,
        val personAge: Int,
        val personAddress: String,
        val personGender: String,
        val imageUrl: String
    ) : PolymorphicItem

    object Serializer : KSerializer<PolymorphicItem> {
        private val json = Json {
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
            val output = encoder as? JsonEncoder
            val tree = when (value) {
                is Car -> output?.json?.encodeToJsonElement(Car.serializer(), value)
                is Person -> output?.json?.encodeToJsonElement(Person.serializer(), value)
                else -> throw SerializationException("Unknown itemType: $value")
            }
            if (tree != null) {
                output?.encodeJsonElement(tree)
            }
        }

        override fun deserialize(decoder: Decoder): PolymorphicItem {
            val jsonElement = (decoder as? JsonDecoder)?.decodeJsonElement()
            return when (val itemType = jsonElement?.jsonObject?.get("polymorphicType")?.jsonPrimitive?.content) {
                "CAR" -> json.decodeFromJsonElement(Car.serializer(), jsonElement)
                "PERSON" -> json.decodeFromJsonElement(Person.serializer(), jsonElement)
                else -> throw SerializationException("Unknown itemType: $itemType")
            }
        }
    }
}