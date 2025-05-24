package com.github.viscube.greenhouse.deviceDetail.data.repository

import com.github.viscube.greenhouse.deviceDetail.domain.repository.IMqttRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class MqttRepository(
    private val mqttService: MqttService
) : IMqttRepository {

    override val messages: SharedFlow<Pair<String, String>>
        get() = mqttService.messages

    override suspend fun connect() =
        mqttService.connect()

    override suspend fun subscribe(topics: List<String>) =
        mqttService.subscribe(topics)

    override suspend fun disconnect() = mqttService.disconnect()

    override suspend fun publish(topic: String, message: String) =
        mqttService.publish(topic, message)

    override fun observeTopic(topic: String): Flow<String> = messages
        .filter { (t, _) -> t == topic }
        .map { (_, value) -> value }
}