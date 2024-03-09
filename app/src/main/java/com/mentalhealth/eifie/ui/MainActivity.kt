package com.mentalhealth.eifie.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.mentalhealth.eifie.data.preferences.dataStore
import com.mentalhealth.eifie.ui.navigation.AuthNavigation
import com.mentalhealth.eifie.ui.theme.EifieTheme
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.tokenPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validateSession()
        initLogin()
    }

    private fun initLogin() {
        setContent {
            EifieTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthNavigation(navigateToHome = navigateToHome)
                }
            }
        }
    }

    private fun validateSession() = lifecycleScope.launch(dispatcher) {
        when(getSession()) {
            null, emptyString() -> Unit
            else -> navigateToHome.invoke()
        }
    }

    private suspend fun getSession(): String? {
        return this.dataStore.data.map { preferences ->
            preferences[tokenPreferences]
        }.firstOrNull()
    }

    private val navigateToHome: () -> Unit =  {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}