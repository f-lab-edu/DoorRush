[![coverage](../badges/jacoco.svg)](https://github.com/f-lab-edu/DoorRush/actions/workflows/gradle.yml)
# DoorRush

---
<div align="center">
   <img width="600" alt="README 이미지_2" src="https://user-images.githubusercontent.com/56250078/151295612-86d6cf93-49ea-4c92-ba8a-d3c7c55d2400.png">

### "Restaurnts and more, delivered to your door!"
###  Door Dash, 배달의 민족을 모티브로 만든 배달 플랫폼 API 서버 프로젝트입니다.
</div>

---

## 📌 목표

- 배달의 민족와 같은 배달 앱 서비스를 구현해 내는 것을 목표로 하였습니다.
- 객체지향적인 설게와 이론을 바탕으로 **확장성이 좋고 유연한 클린 코드**를 만들고자 합니다.  
- **대용량 트래픽 처리**까지 고려한 기능을 구현하는 것이 목표입니다.
- 안정적인 서비스를 위해 **테스트 코드를 작성**합니다. 
   - Code Coverage 70%를 유지하고 있습니다.
   - 작성한 코드에 대해 꼼꼼하게 테스트 함으로써 기능의 안정성을 보장하고, 추후 리팩토링도 과감하게 할 수 있게끔 하는 것을 목표로 합니다.  
- CI/CD를 통한 자동화를 구현하여 쉽게 협업이 가능한 프로젝트를 목표로 합니다.
- Github, Slack을 통한 활발한 소통을 바탕으로 효율적인 협업을 추구합니다.

---
## 📌 **기술 스택**
 * <a href="https://docs.oracle.com/en/java/javase/11/">Java 11</a>
 * <a href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/">Spring Boot 2.5.x</a>
 * <a href="https://junit.org/junit5/docs/current/user-guide/">JUnit5</a>
 * <a href="https://mybatis.org/mybatis-3/">MyBatis</a>
 * <a href="https://gradle.org/">gradle</a>
 * <a href="https://docs.github.com/en/actions">Github Action</a>
 * <a href="https://www.navercloudcorp.com/">Naver Cloud</a>
 * <a href="https://dev.mysql.com/">MySQL</a>
 
---
## 📌 **ERD**

![image](https://user-images.githubusercontent.com/56250078/152634236-79887af9-5b27-4265-ba99-e737e57c6016.png)

---
## 📌 **주요 기능 시퀀스 다이어그램**

###  로그인 기능
![로그인시퀀스다이어그램](https://user-images.githubusercontent.com/56250078/152634301-141280e0-93ec-4b59-87e6-1f5c4f229603.png)

---
## 📌 **기술적 이슈와 해결 과정**

- [외부  API가 응답 코드조차 보낼 수 없는 장애가 발생했다면? Circuit Breaker Pattern로 해결하기](https://github.com/f-lab-edu/DoorRush/issues/67) <br>
- [인증 관련 보안 이슈](https://github.com/ypr821/TIL/blob/main/2022_01/%EC%9E%90%EB%8F%99%EB%A1%9C%EA%B7%B8%EC%9D%B8%EA%B8%B0%EB%8A%A5_%EB%B3%B4%EC%95%88%EC%9D%B4%EC%8A%88_%EA%B3%A0%EB%AF%BC.md) <br>
- [로그인 체크는 어떻게 하면 좋을까?](https://dev-promise.tistory.com/entry/%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%B2%B4%ED%81%AC%EB%A5%BC-%EC%9C%84%ED%95%9C-%EA%B8%B0%EC%88%A0%EC%A0%81-%EA%B3%A0%EB%AF%BC)
- <a href="https://yeoonjae.tistory.com/entry/Project-Github-action%EC%9D%84-%ED%99%9C%EC%9A%A9%ED%95%98%EC%97%AC-PR%EC%8B%9C-%EC%9E%90%EB%8F%99-Build-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0-CI%EC%84%A4%EC%A0%95?category=1023285">Github Action을 통해 CI 설정하기</a>
- <a href="https://yeoonjae.tistory.com/entry/%EB%8B%A4%EC%A4%91-%EC%84%9C%EB%B2%84%EC%97%90%EC%84%9C%EC%9D%98-Session-%EB%B6%88%EC%9D%BC%EC%B9%98-%ED%98%84%EC%83%81%EA%B3%BC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95">다중 서버에서 session 불일치 현상 해결 방법</a>
---
## 📌 **ETC**
* <a href="https://github.com/f-lab-edu/DoorRush/wiki/3.-Project-Convention">프로젝트 컨벤션</a>
* <a href="https://github.com/f-lab-edu/DoorRush/wiki/2.-Use-Case">Use Case 보러가기</a>
* 서버 구조도 보러가기
---

