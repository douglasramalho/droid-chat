package com.example.droidchat.data.manager

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object CryptoManager {

    // Criptografa dados sensíveis usando o EncryptedSharedPreferences
    fun encryptData(context: Context, key: String, data: String): String {
        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_prefs",  // Nome do arquivo SharedPreferences criptografado
            getMasterKey(context), // Chave mestra gerenciada pelo Keystore
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // Salva o dado criptografado
        sharedPreferences.edit().putString(key, data).apply()

        // Retorna o dado criptografado (já armazenado)
        return sharedPreferences.getString(key, null) ?: ""
    }

    // Descriptografa os dados armazenados
    fun decryptData(context: Context, key: String): String {
        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_prefs",
            getMasterKey(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // Retorna o dado descriptografado
        return sharedPreferences.getString(key, null) ?: ""
    }

    // Cria uma chave mestra no Keystore
    private fun getMasterKey(context: Context): MasterKey {
        return MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM) // Algoritmo de criptografia AES com 256 bits
            .build()
    }
}