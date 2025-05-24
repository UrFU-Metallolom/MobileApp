package com.github.viscube.greenhouse.deviceDetail.data.bleManager

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import java.util.UUID


class BleManager(private val context: Context) {

    private var bluetoothGatt: BluetoothGatt? = null
    private var characteristic: BluetoothGattCharacteristic? = null
    private var buffer = StringBuilder()
    private var isConnected = false

    private var onLineReceived: ((String) -> Unit)? = null

    @SuppressLint("MissingPermission")
    fun connectAndListen(address: String, onLineReceived: (String) -> Unit) {
        this.onLineReceived = onLineReceived
        val adapter =
            (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        val device = adapter.getRemoteDevice(address)

        val gattCallback = object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    gatt.discoverServices()
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    isConnected = false
                    gatt.close()
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                val service = gatt.getService(SERVICE_UUID)
                val char = service?.getCharacteristic(CHARACTERISTIC_UUID)
                if (char != null) {
                    characteristic = char
                    bluetoothGatt = gatt
                    isConnected = true
                    gatt.setCharacteristicNotification(char, true)

                    val descriptor = char.getDescriptor(CLIENT_CONFIG_UUID)
                    descriptor?.let {
                        it.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                        gatt.writeDescriptor(it)
                    }
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onCharacteristicChanged(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic
            ) {
                gatt.readCharacteristic(characteristic)
                val value = characteristic.value.decodeToString()
                buffer.append(value)
                val lines = buffer.split("\r\n")
                for (i in 0..<lines.size - 1) {
                    val line = lines[i].trim()
                    if (line.isNotEmpty()) {
                        onLineReceived.invoke(line)
                    }
                }
                buffer = StringBuilder(lines.last())
            }
        }

        bluetoothGatt = device.connectGatt(context, false, gattCallback)
    }

    @SuppressLint("MissingPermission")
    fun sendCommand(command: String) {
        if (!isConnected || bluetoothGatt == null || characteristic == null) return

        characteristic?.let { char ->
            char.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
            char.value = command.toByteArray()
            bluetoothGatt?.writeCharacteristic(char)
        }
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
        characteristic = null
        isConnected = false
    }

    companion object {
        val SERVICE_UUID: UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
        val CHARACTERISTIC_UUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
        val CLIENT_CONFIG_UUID: UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
    }
}
