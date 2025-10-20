package com.example.networkguardian

import android.content.Context

object RuleStorage {
    private const val PREF_NAME = "connection_rules"

    fun saveList(context: Context, key: String, list: Set<String>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putStringSet(key, list).apply()
    }

    fun loadList(context: Context, key: String): MutableSet<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(key, emptySet())?.toMutableSet() ?: mutableSetOf()
    }
}
