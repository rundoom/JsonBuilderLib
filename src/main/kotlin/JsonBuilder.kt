import org.json.JSONObject
import java.util.*

object buildJson {
    operator fun invoke(build: JsonObjectBuilder.() -> Unit): JSONObject {
        return JsonObjectBuilder().json(build)
    }

    operator fun get(vararg members: JsonObjectBuilder.() -> Unit): List<JSONObject> {
        return members.map {
            buildJson(it)
        }.toList()
    }
}

class JsonObjectBuilder {
    private val deque: Deque<JSONObject> = ArrayDeque()

    fun json(build: JsonObjectBuilder.() -> Unit): JSONObject {
        deque.push(JSONObject())
        this.build()
        return deque.pop()
    }

    operator fun String.invoke(value: String?) = if (value != null) {
        deque.peek().put(this, value)
    } else deque.peek().put(this, JSONObject.NULL)

    operator fun String.invoke(value: Boolean) = deque.peek().put(this, value)
    operator fun String.invoke(value: Number) = deque.peek().put(this, value)
    operator fun String.invoke(build: JsonObjectBuilder.() -> Unit) = deque.peek().put(this, json(build))

    operator fun String.get(vararg members: Comparable<*>) {
        deque.peek().put(this, members)
    }

    operator fun String.get(vararg members: JsonObjectBuilder.() -> Unit) {
        val list = members.map {
            buildJson(it)
        }.toList()
        deque.peek().put(this, list)
    }
}

fun main() {
    val jsonObject =
        buildJson {
            "name"("ilkin")
            "age"(37)
            "male"(true)
            "oouuee"[1, 2, 5, "kwefjew"]
            "jefwjef"[{
                "ddq"(5)
                "kfjkwf"("jfejfk")
            }, {
                "Something"(55)
                "lil"(true)
            }, {
                "kk"("ok")
                "vv"{
                    "oioi"(9)
                }
            }]
            "hdwedw"(null)
            "contact" {
                "city"("istanbul")
                "something"{
                    "number"(9)
                    "text"("It is a text")
                }
                "email"("xxx@yyy.com")
            }
        }
    println(jsonObject)

    val jsonArray = buildJson[
            {
                "name"("it is a name")
            },
            {
                "age"("it is an age")
                "obj"{
                    "an obj"(8)
                    "a list"[1, 2, 3, 4, 5, 6]
                }
            }
    ]

    println(jsonArray)

    val sim = buildJson {
        "glossary"{
            "title"("example glossary")
            "GlossDiv" {
                "title"("S")
                "GlossList" {
                    "GlossEntry" {
                        "ID"("SGML")
                        "SortAs"("SGML")
                        "GlossTerm"("Standard Generalized Markup Language")
                        "Acronym"("SGML")
                        "Abbrev"("ISO 8879:1986")
                        "GlossDef" {
                            "para"("A meta-markup language, used to create markup languages such as DocBook.")
                            "GlossSeeAlso"["GML", "XML"]
                        }
                        "GlossSee"("markup")
                    }
                }
            }
            "woosh"[1, 2]
        }
    }

    println(sim)
    listOf(1, 2, 3)

}
