package com.github.viscube.greenhouse.util.bleScanner

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import com.github.viscube.greenhouse.deviceList.domain.entity.BLEEntity

class BleScanner(
    context: Context
) {
    private val bluetoothAdapter: BluetoothAdapter? =
        (context.getSystemService(BLUETOOTH_SERVICE) as? BluetoothManager)?.adapter
    private val scanner = bluetoothAdapter?.bluetoothLeScanner

    private var onDeviceFound: ((BLEEntity) -> Unit)? = null

    private val scanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            val entity = BLEEntity(
                name = device.name ?: "Unknown",
                address = device.address
            )
            onDeviceFound?.invoke(entity)
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan(onDeviceFound: (BLEEntity) -> Unit) {
        this.onDeviceFound = onDeviceFound
        scanner?.startScan(scanCallback)
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        scanner?.stopScan(scanCallback)
    }
}
