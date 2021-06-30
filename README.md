# RankReminder

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Dependencies](#dependencies)
* [Plans](#plans)

## General info
Simple rank reminder plugin for minecraft servers which use LuckPerms. In addition it provides /rankexpiration (/ranga) command which displays remaining time of the rank with the highest priority (now it only supports two groups: vip and mvip. I will add custom group system). You can use this plugin on your spigo server (and forks of spigot like paper, tuinity etc.)

## Technologies
Project is created with:
* Java 11
* Spigot API
* LuckPerms API 5.3

## Setup
Just dowload a JAR file from releases and drop it down to your plugin folder. If you want, you can compile it by yourself. Download repository from git, add spigot API and [LP API](https://repo1.maven.org/maven2/net/luckperms/api/). You have to use Java 11. The final step is to compile it with Maven and use your JAR file as written above.

## Dependencies
This plugin REQUIRES [LuckPerms](https://luckperms.net/) plugin installed on your minecraft server!

## Plans:
* add support of different languages
