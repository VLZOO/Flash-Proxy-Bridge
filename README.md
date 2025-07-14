# Flash Proxy Bridge

A dual-plugin system enabling seamless communication between Spigot/Bukkit and Velocity proxy servers via plugin messaging. Built to empower advanced Minecraft server networks — especially those serving the Persian community 🇮🇷

## 🔌 Overview

This project contains two distinct plugins:

- **FlashBukkit**: A Spigot-side plugin that sends player-triggered commands to the Velocity proxy using custom plugin messaging.
- **FlashVelocity**: A Velocity-side plugin that receives commands from Bukkit and executes them either via player context (if online) or console context (fallback).

Together, they form a lightweight command relay bridge between server layers.

---

## 📁 Structure

Each folder contains a standalone Gradle-based Java project, ready to build and deploy.

---

## 🚀 Features

### 🧠 FlashBukkit
- Registers a custom plugin messaging channel: `flash:proxy`
- Provides commands `/flashcmd <command>` and alias `/flc`
- Sends:
  - Command type
  - Player name
  - Raw command string  
  …to the Velocity proxy

### ⚡ FlashVelocity
- Listens on `flash:proxy` channel using `PluginMessageEvent`
- Parses the incoming payload:
  - Message type: `flashcmd`
  - Player name
  - Target command
- Executes the command with player context if online, or console fallback
- Uses Velocity API's `CommandManager` for non-blocking execution

### 🔧 Requirements
- Java 17+
- Gradle 7+
- Compatible with:
- Velocity API 3.1.1+
- Bukkit / Spigot 1.8+

### 🙌 Credits
- Developed by VLZO
- Bridging gameplay between Minecraft layers with precision and simplicity

---

## 📋 Installation

### FlashBukkit
1. Place `FlashBukkit.jar` in your Spigot/Bukkit server's `plugins/` directory
2. Ensure `plugin.yml` includes:
    ```yaml
    channels:
      outgoing:
        - flash:proxy
    ```
3. Restart the server

### FlashVelocity
1. Place `FlashVelocity.jar` in your Velocity server's `plugins/` directory
2. Edit `velocity.toml` to allow the messaging channel:
    ```toml
    plugin-messaging-channels.allow = ["flash:proxy"]
    ```
3. Restart the proxy

---

## 🧪 Testing

On Bukkit:

```bash
/flashcmd say Hello via Velocity!
/flc list
/flashcmd [command]
