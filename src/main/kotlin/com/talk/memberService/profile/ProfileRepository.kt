package com.talk.memberService.profile

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProfileRepository: CoroutineCrudRepository<Profile, String> {
    suspend fun countByUserId(userId: String): Int

    suspend fun countByEmail(email: String): Int
}