package com.example.dubizzletask.core

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

internal fun MockWebServer.enqueueResponse(code: Int, fileName: String? = null) {

    val jsonResponse = fileName?.let {
        javaClass.classLoader
            ?.getResourceAsStream("api-responses/$fileName")
            ?.source()
            ?.buffer()
            ?.readString(StandardCharsets.UTF_8)
    }

    val mockResponse = MockResponse().setResponseCode(code).apply {
        if (jsonResponse != null) setBody(jsonResponse)
    }
    enqueue(mockResponse)
}

internal inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    crossinline action: Fragment.() -> Unit = {}
) {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            MainActivity::class.java
        )
    )

    ActivityScenario.launch<MainActivity>(startActivityIntent).onActivity { activity ->
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, fragment, "")
            .commitNow()

        fragment.action()
    }
}