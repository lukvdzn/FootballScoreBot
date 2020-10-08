package model

inline fun <reified T : Enum<T>> contains(name: String): Boolean {
    return enumValues<T>().any { it.name == name}
}