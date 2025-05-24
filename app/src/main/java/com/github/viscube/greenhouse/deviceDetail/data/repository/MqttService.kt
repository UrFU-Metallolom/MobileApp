package com.github.viscube.greenhouse.deviceDetail.data.repository

import android.content.Context
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

class MqttService(context: Context, config: MqttConfig) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val client = MqttAndroidClient(context, config.serverUri, config.clientId)

    private val mutableMessages = MutableSharedFlow<Pair<String, String>>()
    val messages = mutableMessages.asSharedFlow()


    private val connectionOptions: MqttConnectOptions by lazy {
        MqttConnectOptions().apply {
            isCleanSession = config.cleanSession
            maxReconnectDelay = config.reconnectDelay
            connectionTimeout = config.connectionTimeout
            config.username?.let { userName = it }
            config.password?.let { password = it.toCharArray() }
        }
    }

    init {
        setupCallbacks()
    }

    private fun setupCallbacks() {
        client.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String, message: MqttMessage) {
                handleMessage(topic, String(message.payload))
            }

            override fun connectionLost(cause: Throwable?) {
                coroutineScope.launch {
                    mutableMessages.emit("system" to "Connection lost")
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
            }
        })
    }

    suspend fun connect() = withContext(Dispatchers.IO) {
        try {
            client.connect(connectionOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {

                }
            }).waitForCompletion(30000)
        } catch (_: Exception) {
        }
    }

    fun subscribe(topics: List<String>) = topics.forEach { client.subscribe(it, 1) }

    fun publish(topic: String, message: String, qos: Int = 1) {
        client.publish(topic, MqttMessage(message.toByteArray()).apply {
            this.qos = qos
        })
    }

    fun disconnect() {
        try {
            client.disconnect().waitForCompletion()
            coroutineScope.coroutineContext.cancel()
        } catch (_: Exception) {

        }
    }

    private fun handleMessage(topic: String, payload: String) {
        coroutineScope.launch {
            mutableMessages.emit(topic to payload)
        }
    }
}

data class MqttConfig(
    val serverUri: String,
    val clientId: String = "android-client-${MqttClient.generateClientId()}",
    val cleanSession: Boolean = true,
    val autoReconnect: Boolean = true,
    val reconnectDelay: Int = 1000,
    val connectionTimeout: Int = 30,
    val retainMessages: Boolean = false,
    val username: String? = null,
    val password: String? = null
)