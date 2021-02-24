# Debug Drawer for Jetpack Compose

Composable Debug Drawer for Jetpack Compose apps

<img width="250" src="art/drawer-v0.1.0-beta-02.png" />

## Install

```groovy
allprojects {
    repositories {
        //...
        mavenCentral()
    }
}
```

Add dependencies:

```groovy
implementation 'com.github.alorma.Compose-Debug-Drawer:drawer-base:1.0.0-beta01'
implementation 'com.github.alorma.Compose-Debug-Drawer:drawer-modules:1.0.0-beta01'
```

## Setup

Wrap your content with `DebugDrawerLayout`:

```kotlin
DebugDrawerLayout(
  debug = { BuildConfig.DEBUG },
  drawerModules = {
    TODO()
  }

) {
  // TODO Add your APP Content here
}
```

This library handles the debug / release state, so no need to remove the drawer on Release builds

## Modules

Add modules as a list of `DebugModule`s

```kotlin
DebugDrawerLayout(
  debug = { BuildConfig.DEBUG },
  drawerModules = {
    DeviceModule()
    BuildModule()
  }
) {
  // TODO Add your APP Content here
}
```

#### Actions Module

This module receive a `List<DebugDrawerAction>`

<img width="160" src="art/actions_module.png" />

*Actions*

* ButtonAction: Shows a `Button` with given text, and register a lambda to receive it's click

* SwitchAction: Shows a `Switch` and register a lambda to receive it's changes

#### Build Module

Shows information about the app: Version code, Version name and Package

<img width="160" src="art/build_module.png" />

#### Device Module

Shows information about device running the app such as Device, and manufacturer

<img width="160" src="art/device_module.png" />

#### Custom Module
Debug drawer can show any `@Composable` function.

If you want to provide a custom module that looks like the ones provided by the library:

```kotlin
@Composable
fun InfoModule(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: String,
    items: List<Pair<String, String>>
) {
    DebugDrawerModule(
        modifier = modifier,
        icon = icon,
        title = title
    ) {
        // Module content
    }
}
```

## Theming && Customization

Use `drawerColors` to customize drawer theme colors

```kotlin
DebugDrawerLayout(
     drawerColors = YourColorScheme, // darkColors() or lightColors()
)
```

### Modules list UI

Update module UI by pass `Modifier`

```kotlin
DebugDrawerLayout(
    drawerModules = {
        val modulesModifier = Modifier
            .padding(4.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colors.surface)
        DeviceModule(modulesModifier)
        BuildModule(modulesModifier)
    }
)
```
