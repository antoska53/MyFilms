package ru.myacademyhomework.myfilms.data

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json       = Json(JsonConfiguration.Stable)
// val actorsInfo = json.parse(ActorsInfo.serializer(), jsonString)


import kotlinx.serialization.*

@Serializable
data class ActorsInfo(
    val id: Int,
    val cast: List<Actor>,

    )



