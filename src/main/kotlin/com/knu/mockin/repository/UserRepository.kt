package com.knu.mockin.repository

import com.knu.mockin.model.entity.User
import com.knu.mockin.model.entity.UserInfo
import com.knu.mockin.model.entity.UserWithKeyPair
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveCrudRepository<User, String> {
    @Query("insert into user (email, password, name) " +
            "values (:#{#user.email}, :#{#user.password}, :#{#user.name})")
    fun save(user: User): Mono<User>

    @Query("update user set account_number = :accountNumber where email=:email")
    fun updateByEmail(email: String, accountNumber: String): Mono<User>
    fun findByEmail(email: String): Mono<User>

    @Query(
        "select u.email, u.account_number, m.app_key, m.app_secret " +
            "from user as u " +
            "inner join mock_key as m on u.email = m.email " +
            "where u.email = :email")
    fun findByEmailWithMockKey(email: String): Mono<UserWithKeyPair>

    @Query(
        "select u.email, u.account_number, r.app_key, r.app_secret " +
            "from user as u " +
            "inner join real_key as r on u.email = r.email " +
            "where u.email = :email")
    fun findByEmailWithRealKey(email: String): Mono<UserWithKeyPair>

    @Query(
        """
        SELECT 
            u.email, 
            u.password,
            u.account_number,
            m.app_key,
            r.app_secret
        FROM 
            (SELECT email, password, account_number FROM user WHERE email = :email) AS u
        LEFT OUTER JOIN 
            mock_key AS m ON m.email = u.email
        LEFT OUTER JOIN 
            real_key AS r ON r.email = u.email
        """
    )
    fun findUserInfoByEmail(email: String): Mono<UserInfo>
}
