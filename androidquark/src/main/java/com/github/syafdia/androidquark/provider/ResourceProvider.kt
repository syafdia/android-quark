package com.github.syafdia.androidquark.provider

import android.content.res.Resources

interface ResourceProvider {

    fun getString(resId: Int): String

    fun getResources(): Resources
}