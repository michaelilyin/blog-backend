package ru.michaelilyin.blog.welcome.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.welcome.dto.CommonInfo
import ru.michaelilyin.blog.welcome.dto.CommonRecord
import ru.michaelilyin.blog.welcome.dto.InfoType
import ru.michaelilyin.blog.welcome.services.CommonInfoService

@Component
class CommonInfoResolver @Autowired() constructor(
  private val commonInfoService: CommonInfoService
) : GraphQLResolver<CommonInfo> {
  fun applicationName(info: CommonInfo) = commonInfoService.find("applicationName")
  fun primaryTitle(info: CommonInfo) = commonInfoService.find("primaryTitle")
}