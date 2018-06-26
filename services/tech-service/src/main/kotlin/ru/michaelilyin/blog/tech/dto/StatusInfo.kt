package ru.michaelilyin.blog.tech.dto;

data class StatusInfo(
  val version: String,
  val buildTime: String,
  val javaVersion: String,
  val uptime: Long
)