package com.github.viscube.greenhouse.deviceDetail.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface IMqttRepository {
    val messages: SharedFlow<Pair<String, String>>

    suspend fun connect(): Any
    suspend fun subscribe(topics: List<String>)
    suspend fun publish(topic: String, message: String)
    suspend fun disconnect(): Any
    fun observeTopic(topic: String): Flow<String>

}


