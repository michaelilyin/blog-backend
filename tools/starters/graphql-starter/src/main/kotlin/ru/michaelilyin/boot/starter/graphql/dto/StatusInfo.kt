package ru.michaelilyin.boot.starter.graphql.dto;

import java.time.LocalDateTime

data class StatusInfo(
  val name: String,
  val version: String,
  val buildTime: LocalDateTime,
  val javaVersion: String,
  val startTime: LocalDateTime,
  val uptime: Long,
  val springVersion: String,
  val springBootVersion: String
)