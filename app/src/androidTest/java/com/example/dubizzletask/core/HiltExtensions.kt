package com.example.dubizzletask.core

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions

inline fun <reified T : Fragment> launchFragmentInHiltContainer(
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