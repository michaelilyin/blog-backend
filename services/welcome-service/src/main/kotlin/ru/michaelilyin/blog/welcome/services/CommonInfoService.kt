package ru.michaelilyin.blog.welcome.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.michaelilyin.blog.welcome.dto.CommonRecord
import ru.michaelilyin.blog.welcome.repository.CommonInfoRepository

@Service
class CommonInfoService @Autowired constructor(
  private val commonInfoRepository: CommonInfoRepository
) {
  fun find(key: String): CommonRecord {
    return commonInfoRepository.find(key)
  }

  @Transactional("welcomeTX")
  fun update(record: CommonRecord): CommonRecord {
    commonInfoRepository.update(record)
    return record
  }
}