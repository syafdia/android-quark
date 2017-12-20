package com.github.syafdia.androidquark

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonArray
import org.junit.Assert
import org.junit.Test


class JsonTest {

    private class Person internal constructor(var firstName: String, var lastName: String)

    private val personJsonSc = "{\"first_name\":\"Badu\",\"last_name\":\"Budi\"}"

    private val personJsonCc = "{\"firstName\":\"Badu\",\"lastName\":\"Budi\"}"

    @Test
    fun jsonParse_ObjectCreatedSuccessfully() {
        val person1 = Person("Badu", "Budi")
        val person2 = Json.parseAs(Person::class.java, personJsonSc)

        Assert.assertEquals(person1.firstName, person2!!.firstName)
        Assert.assertEquals(person1.lastName, person2!!.lastName)
    }

    @Test
    fun jsonParse_ObjectNotCreatedBecauseJsonIsNotSnakeCase() {
        val person1 = Json.parseAs(Person::class.java, personJsonCc)

        Assert.assertEquals(null, person1!!.firstName)
        Assert.assertEquals(null, person1!!.lastName)
    }

    @Test
    fun jsonParse_ObjectIsNullBecauseJsonIsNotValid() {
        val person1 = Json.parseAs(Person::class.java, "Not Json")

        Assert.assertEquals(null, person1)
    }

    @Test
    fun asJsonObject_DataFetchSuccessfully() {
        val jsonFoo = """
            {
                "foo": {
                    "bar": {
                        "baz": 1,
                        "tar": "qux",
                        "lols": [1, 2, 3, 4]
                    }
                }
            }
            """

        val objFoo = Json.parse(jsonFoo)

        Assert.assertEquals(jsonArray(1, 2, 3, 4), objFoo.get("foo")?.get("bar")?.get("lols")?.asJsonArray)
        Assert.assertEquals(1, objFoo.get("foo")?.get("bar")?.get("baz")?.asInt)
        Assert.assertEquals("qux", objFoo.get("foo")?.get("bar")?.get("tar")?.asString)
        Assert.assertEquals(null, objFoo.get("qux"))
    }
}