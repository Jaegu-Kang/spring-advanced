# SPRING ADVANCED

##  프로젝트 개요

기존에 작성된 시스템의 버그를 수정하고, 아키텍처의 비효율성을 개선하며, 깨진 테스트 코드를 정상화하는 코드 품질 향상 및 트러블슈팅입니다.

---

## 주요 구현 및 리팩토링 사항

### 1. 환경 설정 및 프레임워크 연동 오류 해결

* **JWT Security Key 규격 적용:** JJWT 라이브러리의 `HS256` 알고리즘 요구 사항에 맞춰, Secret Key를 Base64 인코딩 문자열로 작성하여 런타임 예외 해결.
* **Custom Argument Resolver 등록:** `@Auth` 어노테이션 처리를 위한 `AuthUserArgumentResolver`를 구현하고, 누락되어 있던 `WebMvcConfigurer` 구현체(`WebConfig`)를 생성하여 빈(Bean) 등록.

### 2. 비즈니스 로직 가독성 및 아키텍처 개선

* **Early Return 패턴 적용 (성능 최적화):** `AuthService`의 회원가입 로직에서 예외 검증(이메일 중복 검사)을 메서드 최상단으로 이동. 
  * 무거운 암호화 연산이 불필요하게 수행되는 리소스 낭비 방지.
* **불필요한 Else 블록 제거:** `WeatherClient` 내 API 응답 처리 시, 불필요한 else를 제거 코드 가독성 향상.
* **Validation 책임 분리:** `UserService` 내부에 하드코딩 되어있던 비밀번호 길이 및 정규식 검증 로직을 DTO와 Controller로 위임. 
  * 서비스 레이어는 순수 비즈니스 로직에만 집중하도록 응집도 향상.

### 3. N+1 문제 해결

* **`@EntityGraph` 도입:** `TodoRepository` 조회 시 발생하는 N+1 문제를 해결하기 위해, 하드코딩된 FETCH JOIN 구문을 제거하고 `@EntityGraph`를 적용.

### 4. 테스트 코드 수정

* **불일치 수정:** `ManagerServiceTest`, `CommentServiceTest`에서 실제 서비스 로직이 던지는 예외(`InvalidRequestException`)와 테스트 코드가 대기하는 예외(`ServerException`, `NPE`) 간의 불일치 파악 및 검증 로직 수정.
* **파라미터 오류 수정:** `PasswordEncoder.matches()` 호출 시 파라미터 간의 순서 엇갈림으로 인한 검증 실패 수정.
* **방어 로직(Null Check) 추가:** 엔티티 연관관계 그래프 탐색(`todo.getUser().getId()`) 중 발생할 수 있는 `NullPointerException`을 위해 객체 접근 전 null 여부를 먼저 확인하는 로직을 서비스에 추가.