package com.talk.memberService.profile

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import oauth2.Oauth2Constants
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOpaqueToken
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest
@AutoConfigureWebTestClient
class ProfileControllerTests(
        private val webClient: WebTestClient,
        private val profileRepository: ProfileRepository
) : BehaviorSpec({

    fun request(payload: ProfileCreationPayload) = webClient
            .mutateWith(
                    mockOpaqueToken().attributes {
                        it["sub"] = Oauth2Constants.SUBJECT
                    }.authorities(Oauth2Constants.ROLES)
            )
            .post().uri("/member-service/profiles").bodyValue(payload)

    Given("프로필 생성") {
        When("성공한 경우") {
            beforeEach {
                profileRepository.deleteAll()
            }
            val payload = ProfileCreationPayload(
                    "test@test",
                    "kkk"
            )
            Then("status 200") {
                request(payload).exchange().expectStatus().isOk
            }
            Then("프로필 데이터가 존재한다") {
                request(payload).exchange()
                profileRepository.countByEmail(payload.email) shouldBe 1
                profileRepository.count() shouldBe 1
            }
        }
    }
})