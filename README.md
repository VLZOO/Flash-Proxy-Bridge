# ⚡ FlashBukkit

**FlashBukkit** is a lightweight Bukkit/Spigot plugin that allows you to send commands from your Minecraft server directly to a **Velocity Proxy**.  
It supports commands `/flc` and `/flashcmd` and works for **both players and console**.

---

## ✨ Features

- 🚀 Send commands to Velocity safely and quickly  
- 🎮 Works for both **players** and **console**  
- 🔒 Permission support: `flash.use` for players  
- ⚡ Supports `/flc` and `/flashcmd` commands simultaneously  
- 📜 Detailed logs for errors and communication with Velocity  

---

## 🛠 Requirements

- Java 8 or higher  
- Spigot or PaperMC 1.7.3+  
- Velocity proxy server  

---

## 📥 Installation

1. Place the plugin JAR file into your server's `plugins` folder.  
2. Start your server to enable the plugin and register the messaging channel.  
3. Ensure the commands are defined in `plugin.yml`:

```yml
commands:
  flc:
    description: Send command to Velocity
    usage: /<command> <command>
  flashcmd:
    description: Send command to Velocity
    usage: /<command> <command>
```

4. Assign the permission `flash.use` to players who should use the plugin.

---

## 🎮 Usage

### In-game (Player)
```
/flc <command>
/flashcmd <command>
```
- `<command>`: The command you want to send to Velocity.

### Console
```
flashcmd <command>
```
- If no player is online, the command will be logged in the server console.

---

## 🔑 Permissions

| Permission       | Description                  |
|-----------------|------------------------------|
| `flash.use`      | Allows a player to use `/flc` or `/flashcmd` |

---

## 📝 Examples

**From a player in-game:**
```
/flc say Hello from the player! 👋
```

**From console:**
```
flashcmd say Hello from the console! ⚡
```

---

## 📜 License

This project is licensed under the **MIT License**. You are free to use, modify, and distribute it.

