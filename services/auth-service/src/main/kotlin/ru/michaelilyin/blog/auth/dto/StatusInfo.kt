package ru.michaelilyin.blog.auth.dto

data class StatusInfo(
  val version: String,
  val buildTime: String,
  val javaVersion: String,
  val uptime: Long
)