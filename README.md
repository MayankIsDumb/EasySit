# Easy Sit

A lightweight Fabric mod that lets you sit on slabs and stairs, or lay down anywhere in the world. Supports multiple Minecraft versions.

## Features

- Right-click any bottom slab or stair to sit down
- `/sit` command to sit on the block beneath you
- `/lay` command to lay down on any block
- Configurable block reach distance via ModMenu
- Auto-ejects you when the block beneath is broken
- Works across 8 Minecraft versions

## Supported Versions

- Minecraft 1.21.1
- Minecraft 1.21.6
- Minecraft 1.21.11
- Minecraft 26.1
- Minecraft 26.1.1
- Minecraft 26.1.2
- Minecraft 26.2
- Minecraft 26.3

## Requirements

- Fabric Loader 0.16+
- Fabric API
- Cloth Config (for settings GUI)
- Java 21+

## Installation

1. Install Fabric Loader for your Minecraft version
2. Download the Easy Sit jar for your version
3. Place it in your `.minecraft/mods/` folder
4. Launch the game

## Server Setup (Dedicated Fabric Servers)

The mod runs on dedicated servers — install the same jar plus its required dependencies on the server:

1. Install Fabric Loader, Fabric API, and Cloth Config on the server
   (ModMenu is only needed on clients, for the config GUI)
2. Every player must also have the mod installed on their client —
   sitting uses custom seat entities that vanilla clients don't know
3. Server settings live in `config/sit.json5` (e.g. `blockReachDistance`,
   which is enforced server-side)
4. `/sit` and `/lay` are available to all players, no operator status needed

## Usage

- **Sit on a block** — Right-click a bottom slab or stair
- **`/sit`** — Sit on the block directly beneath you
- **`/lay`** — Lay flat on the block beneath you
- **Shift** — Dismount and stand back up

## Configuration

Open ModMenu (Mods button on main menu) and click the config icon for Easy Sit. You can adjust the maximum distance for right-click sitting.

## License

MIT
