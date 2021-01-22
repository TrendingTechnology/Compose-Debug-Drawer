package com.alorma.composedrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alorma.composedrawer.modules.DemoActionsModule
import com.alorma.drawer_base.DebugDrawerLayout
import com.alorma.drawer_base.DebugDrawerState
import com.alorma.drawer_base.DebugDrawerValue
import com.alorma.drawer_base.rememberDebugDrawerState
import com.alorma.drawer_modules.BuildModule
import com.alorma.drawer_modules.DeviceModule

@Composable
fun HomeScreen() {
    val debugDrawerState = rememberDebugDrawerState(initialValueDebug = DebugDrawerValue.Open)
    DebugDrawerLayout(
        isDebug = { BuildConfig.DEBUG },
        debugDrawerState = debugDrawerState,
        drawerModules = {
            val modulesModifier = Modifier
                .padding(4.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colors.surface)
            DemoActionsModule(modulesModifier)
            BuildModule(modulesModifier)
            DeviceModule(modulesModifier)
        }
    ) { AppContent(debugDrawerState) }
}

@Composable
private fun AppContent(drawerState: DebugDrawerState) {
    Scaffold(
        topBar = {
            val title = stringResource(id = R.string.app_name)
            TopAppBar(
                elevation = 0.dp,
                title = { Text(text = title) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            drawerButton(drawerState)
        }

    }
}

@Composable
private fun drawerButton(drawerState: DebugDrawerState) {
    Button(
        onClick = {
            if (drawerState.isOpen) {
                drawerState.close()
            } else if (drawerState.isClosed) {
                drawerState.open()
            }
        }) {
        if (drawerState.isOpen) {
            Text(text = "Close DRAWER")
        } else {
            Text(text = "Open DRAWER")
        }
    }
}
