# README.md

오명운 님이 옮긴 [스프링 부트 실전 활용 마스터](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9791189909307) 책을 보면서 직접 코딩한 프로젝트입니다.

## 공부 목표

- 리액티브 프로그래밍에 대해 이해한다.
- Spring Webflux 를 경험한다.
- 리액터 방식의 프로그래밍에서 테스트 방식을 경험한다.

## 히스토리

### 2021.07.03 책 시작

- 1장 스프링 부트 웹 애플리케이션 만들기
  - 스프링 부트란 무엇인가
  - 리액티브 프로그래밍 소개
  - 스프링 웹 플럭스의 등장
  - 스프링 부트로 이커머스 플랫폼 만들기
  - 첫 코드
  - 템플릿 적용
  - 정리
    
### 2021.07.08 둘째날

- 2장 스프링 부트를 활용한 데이터 액세스
  - 리액티브 데이터 스토어 요건
  - 이커머스 어플리케이션 도메인 정의
  - 리포지토리 만들기
  - 테스트 데이터 로딩
  - 장바구니 보여주기
  - 장바구니에 상품 담기
  - 서비스 추출
  - 데이터베이스 쿼리
  - 쿼리문 자동 생성 메소드로 충분하지 않을 때
  - Example 쿼리
  - 평문형 연산
  - 트레이드 오프
  
### 2021.07.13 셋째날

- 3장 부트 개발자 도구
  - 애플리케이션 시작 시간 단축
  - 개발자 도구
  - 리액터 개발자 도구
  
> `BlockHound`, `devtools`, 리액티브 스트림 `log()`

### 2021.07.14 넷째날

- 4 장 스프링 부트 테스트
  - 리액티브 단위 테스트 작성
  - 스프링 부트의 내장 컨테이너 테스트 기능
  - 단위 테스트와 통합 테스트의 중간에 위치하는 슬라이스 테스트 활용
  - 테스트 도구를 활용한 블로킹 검출

> `StepVerifier`, 시그널 검증, 슬라이스 테스트, 블로킹 검출

### 2021.07.16 다섯째날

- 5장
  - 애플리케이션 배포
    > 우버 JAR(bootJar), Dockerfile 이미지, gradle 이미지(bootBuildImage) 배포
  - 운영 애플리케이션 관리
    > actuator -> loggers 의 경우 POST 요청으로 RESTful 하게 레벨을 변경할 수 있다!
  - 다양한 운영 데이터 확인
    > heap dump 는 jhat 명령 or visual vm 으로 확인
  - 관리 서비스 경로 수정
  - 정리
    - 실행 가능한 JAR 파일 생성
    - 계층 기반 `Dockfil` 작성 및 컨테이너 생성
    - `Dockerfile` 없이 컨테이너 생성
    - 스프링 부트 액추에이터 추가
    - 필요한 관리 서비스만 노출

### 2021.07.17 ~ 19

- 6장
  - HTTP 웹 서비스 구축
  - API 포털 생성
    > `preprocessResponse` -> `OperationPreprocessor` 인터페이스로 구현
  - API 진화 반영
  - 하이퍼미디어 기반 웹 서비스 구축
  - 하이퍼미디어의 가치
    > [진부함을 넘어선 REST](https://speakerdeck.com/olivergierke/rest-beyond-the-obvious-api-design-for-ever-evolving-systems) 과 [영상](https://www.youtube.com/watch?v=x_9OJKAv-ic&ab_channel=Devoxx)
  - API 에 행동 유동성 추가
    > `@EnableHyperMediaSupport` - `HAL`, `HAL_FORMS`
  
### 2021.07.20

- 7장
  > - 스프링부트에서 지원하는 다양한 메시징 솔루션
  > - 스프링 부트에서 직접 지원하지는 않지만, 스프링 포트폴리오에서 지원하는 다양한 메시징 솔루션
  > - AMQP 를 자세히 알아보고, 스프링 AMQP 와 프로젝트 리액터를 활용해 웹 게층과 백엔드의 결합 관계 해소
  - 메시징 솔루션
  - 익숙한 패턴을 사용한 문제해결
  - 손쉬운 테스트
  - 테스트 컨테이너 사용 테스트
    > `testcontainers` 라이브러리
  - 스케줄러를 사용해서 블로킹 API 감싸기
    > `Schedulers` 스레드 종류
  