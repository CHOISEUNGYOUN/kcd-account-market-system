package com.group.kcd.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L

  @CreatedDate
  private var createdDateTime: LocalDateTime = LocalDateTime.now()

  @LastModifiedDate
  private var modifiedDateTime: LocalDateTime = LocalDateTime.now()

}