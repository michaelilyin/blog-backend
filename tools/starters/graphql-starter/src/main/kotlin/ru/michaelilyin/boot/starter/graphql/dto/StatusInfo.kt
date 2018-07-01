package ru.michaelilyin.boot.starter.graphql.dto;

import java.time.LocalDateTime

data class StatusInfo(
  val version: String,
  val buildTime: String,
  val javaVersion: String,
  val startTime: LocalDateTime,
  val uptime: Long,
  val springVersion: String,
  val springBootVersion: String
)