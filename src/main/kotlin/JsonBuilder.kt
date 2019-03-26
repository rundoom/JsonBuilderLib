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
