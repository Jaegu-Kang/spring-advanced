package org.example.expert.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class PasswordEncoderTest {

    @InjectMocks
    private PasswordEncoder passwordEncoder;

    @Test
    void matches_메서드가_정상적으로_동작한다() {
        // given: 비밀번호와 이를 암호화한 비밀번호가 주어짐
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // when: 암호화된 비밀번호와 원본이 서로 일치 하는지 검증
        // 잘못된 입력순서 수정
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);

        // then: 결과가 true여야 함
        assertTrue(matches);
    }
}
