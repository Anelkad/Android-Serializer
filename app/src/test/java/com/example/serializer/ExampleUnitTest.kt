package com.example.serializer

import PolymorphicItem
import kotlinx.serialization.json.Json
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testCarSerialization() {
        val car = PolymorphicItem.Car(
            polymorphicType = "CAR",
            carName = "Toyota Camry",
            carModel = 2023,
            carColor = "Blue",
            imageUrl = ""
        )
        val json = Json.encodeToString(PolymorphicItem.serializer(), car)
        val parsedItem = Json.decodeFromString<PolymorphicItem>(json)

        assertEquals(car, parsedItem)
    }

    @Test
    fun testPersonSerialization() {
        val person = PolymorphicItem.Person(
            polymorphicType = "PERSON",
            personName = "John Doe",
            personAge = 30,
            personAddress = "123 Main St",
            personGender = "Male",
            imageUrl = ""
        )
        val json = Json.encodeToString(PolymorphicItem.serializer(), person)
        val parsedItem = Json.decodeFromString<PolymorphicItem>(json)

        assertEquals(person, parsedItem)
    }
}