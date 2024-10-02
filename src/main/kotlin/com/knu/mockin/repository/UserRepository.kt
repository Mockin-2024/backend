package com.knu.mockin.repository

import com.knu.mockin.model.entity.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface UserRepository:ReactiveCrudRepository<User, Long> {
}