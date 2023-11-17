package kr.co.anna.domain._common


class EnumMapper {
    private val factory: MutableMap<String, List<EnumValue>> = HashMap<String, List<EnumValue>>()

    private fun toEnumValues(e: Class<out EnumModel>): List<EnumValue> {
        return e.enumConstants.map { EnumValue(it) }
    }

    fun put(key: String, e: Class<out EnumModel>) {
        factory[key] = toEnumValues(e)
    }

    operator fun get(keys: String): Map<String, List<EnumValue>?> {
        return keys.split(",").toTypedArray().associateWith { key: String -> factory[key] }

    }
}
