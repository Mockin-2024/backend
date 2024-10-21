package com.knu.mockin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.Properties

@Configuration
class EmailConfig {

    @Value("\${spring.mail.username}")
    private lateinit var username: String

    @Value("\${spring.mail.password}")
    private lateinit var password: String

    @Bean
    fun mailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl().apply {
            host = "smtp.gmail.com"
            port = 587 // TLS port
            this.username = this@EmailConfig.username
            this.password = this@EmailConfig.password
        }

        // Use Properties Object to set JavaMailProperties
        val javaMailProperties = Properties().apply {
            put("mail.transport.protocol", "smtp")
            put("mail.smtp.auth", "true")
            put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            put("mail.smtp.starttls.enable", "true")
            put("mail.debug", "true")
            put("mail.smtp.ssl.trust", "smtp.gmail.com")
            put("mail.smtp.ssl.protocols", "TLSv1.3") // TLS v1.3 사용
        }

        mailSender.javaMailProperties = javaMailProperties

        return mailSender
    }
}
