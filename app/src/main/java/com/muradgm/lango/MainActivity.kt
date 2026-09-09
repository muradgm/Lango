package com.muradgm.lango

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private enum class Screen { Welcome, Goal, Today }
private data class LearnerProfile(val language: String = "German", val level: String = "B1", val goal: String = "Build confident everyday German", val minutes: Int = 15)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { LangoApp() } }
}

@Composable private fun LangoApp() {
    var screen by rememberSaveable { mutableStateOf(Screen.Welcome) }
    var profile by remember { mutableStateOf(LearnerProfile()) }
    MaterialTheme { Surface(Modifier.fillMaxSize()) {
        when (screen) {
            Screen.Welcome -> WelcomeScreen { screen = Screen.Goal }
            Screen.Goal -> GoalScreen(profile, { profile = it }) { screen = Screen.Today }
            Screen.Today -> TodayScreen(profile)
        }
    }}
}

@Composable private fun WelcomeScreen(onStart: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text("Lango", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp)); Text("Learn the language you need for the life you actually live.")
        Spacer(Modifier.height(32.dp)); Button(onClick = onStart, Modifier.fillMaxWidth()) { Text("Start") }
    }
}

@Composable private fun GoalScreen(profile: LearnerProfile, onChange: (LearnerProfile) -> Unit, onContinue: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp)) {
        Text("Let's make this yours.", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp)); Text("What language are you learning?"); Spacer(Modifier.height(8.dp)); Text("German", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(24.dp)); Text("What describes you best?"); Spacer(Modifier.height(8.dp))
        listOf("I'm following a level path", "I need German for real life", "I have something specific I need help with").forEach { option ->
            OutlinedButton(onClick = { onChange(profile.copy(goal = option)) }, Modifier.fillMaxWidth().padding(vertical = 4.dp)) { Text(option) }
        }
        Spacer(Modifier.height(24.dp)); Text("How much time do you have today?")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(5, 10, 15, 20).forEach { minutes -> FilterChip(selected = profile.minutes == minutes, onClick = { onChange(profile.copy(minutes = minutes)) }, label = { Text(minutes.toString() + "m") }) }
        }
        Spacer(Modifier.height(32.dp)); Button(onClick = onContinue, Modifier.fillMaxWidth()) { Text("Build my first session") }
    }
}

@Composable private fun TodayScreen(profile: LearnerProfile) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Today", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("German · " + profile.level)
        Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Your first adaptive session", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            Text(profile.minutes.toString() + " minutes · everyday communication")
            Text("We'll start with a short listening check, then practice one useful conversation and finish with retrieval.")
        }}
        Button(onClick = { }, Modifier.fillMaxWidth()) { Text("Start session") }
    }
}
