package org.quevedo.quevedovirtualclassrooms.data.models.common


enum class ResourceType(val value: Int, val stringValue: String) {
    VIDEO(1, "VIDEO"), IMAGE(2, "IMAGE"), URL(3, "URL");

    companion object {
        fun getTypeByString(inputString: String?): ResourceType? {
            val toReturn: ResourceType? = when (inputString) {
                "VIDEO" -> VIDEO
                "IMAGE" -> IMAGE
                "URL" -> URL
                else -> null
            }
            return toReturn
        }

        fun getTypeByInt(inputInt: Int): ResourceType? {
            return when (inputInt) {
                1 -> VIDEO
                2 -> IMAGE
                3 -> URL
                else -> null
            }
        }
    }
}