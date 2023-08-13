## 📌 과제 설명
(기본) 바우처 관리 애플리케이션


- [x]  Maven / Gradle 로 프로젝트를 실제로 구성하고 이때 Spring Boot CLI를 개발PC에 설치해서 명령어들을 사용해보고 프로젝트를 만든다. 그리고 IDE (IntelliJ)에서 실행시켜 본다.
- [x]  바우처 관리 Command-line Application을 만들어본다.
    - [x]  스프링부트 애플리케이션으로 만든다. (Web기능이 없이 만듭니다. 즉, 서버가 띄지 않고 커맨드라인 애플리케이션으로 동작해야한다.)
    - [x]  프로그램이 시작하면 다음과 같이 지원가능한 명령어를 알려준다.

-

  ```bash
  === Voucher Program ===
  Type **exit** to exit the program.
  Type **create** to create a new voucher.
  Type **list** to list all vouchers.
  ```

    - [x]  create / list 커맨드를 지원한다.
        - create 커맨드를 통해 바우처를 생성할수 있다. (FixedAmountVoucher, PercentDiscountVoucher)
        - list 커맨드를 통해 만들어진 바우처를 조회할 수 있다.
        - 바우처 정보를 매모리에 관리한다.
- [x]  적절한 로그를 기록하고 `logback` 설정을해서 에러는 파일로 기록된다.
- [x]  실행가능한 `jar` 파일을 생성한다.

## 👩‍💻 요구 사항과 구현 내용

- [x] Voucher를 공통 기능을 사용하기 위해 추상 클래스로 구현하였습니다.
- 추후 다른 할인 정책 추가 가능

- [x] VoucherType을 추상화하여 Enum 타입으로 구현하였습니다.

- [x] VoucherFactory를 객체를 생성하는 팩토리(Factory) 클래스로 구현하였습니다.
-  객체를 생성하는 작업을 별도의 클래스로 분리하여 객체 생성의 유연성과 확장성 증가

- [x] VoucherRepository를 인터페이스로 구현하였습니다.

- [x] DTO를 request와 response로 분리하여 구현하였습니다.
- 데이터 전송을 위한 객체


**실행결과**


<img width="1575" alt="image" src="https://github.com/seongHyun-Min/springboot-basic/assets/112048126/b43ea840-fe41-44ea-8eeb-06e36177a3a5">


## ✅ PR 포인트 & 궁금한 점
- 학기 졸업 논문으로 인해 시간이 부족하였지만 미흡하게 과제를 완료하기 보다는 기본과제 만이라도 열심히 구현하려고 노력하였습니다.
- 추후 주말에 심화과제도 구현하겠습니다.
- 프로젝트 구조에 맞게 역할과 책임을 최대한 고려하며 구현하였으나, 제 구현이 적절했는지 궁굼합니다.
- 코드의 가독성과 유지보수성을 개선하기 위한 추가적인 개선 사항이 있는지 궁굼합니다.
- 멘토님의 코드리뷰를 통해 리팩토링한 코드가 적절한지 궁굼합니다.


## 📌 과제 설명 2주차
###  바우처 관리 Application
- week 2

  ```bash
  === Voucher Program ===
  Type **exit** to exit the program.
  Type **create** to create a new voucher.
  Type **list** to list all vouchers.

  === Service Program ===
  Type 'create' to Create a customer
  Type 'assign' to Delete a customer
  Type 'list' to View all customers
  Type 'findOne' to Find customers by type
  Type 'update' to Find customers by type
  Type 'delete' to Delete a customer
  Type 'exit' to Find customers by type

## 👩‍💻 요구 사항과 구현 내용
## 도메인
### Voucher (JDBC MYSQL 연동)

- [x] 바우처 생성/저장 기능 구현
- [x]  바우처 조회 기능 구현
     - [x] 전체 조회
   


### Customer (JDBC MYSQL 연동)

- [x]  고객 생성/저장 기능 구현
- [x]  고객에게 바우처 할당 기능 구현
- [x]  고객 조회 기능 구현
     - [x]  전체 고객 조회
     - 타입별 고객 조회 (고객 ID, 고객 이름, 보유한 바우처 ID)
- [x]  고객 이름 수정 기능 구현
- [x] 고객 삭제 기능 구현
     - [x] ID로 단건 삭제 가능
     - [x] 전체 삭제 가능
  


## ✅ PR 포인트 & 궁금한 점
- JDBC를 사용해서 CRUD를 구현하는데 익숙하지 않지만 최대한 노력하였습니다.
- 바우처와 고객이 적절하게 매핑이 되었는지 정확하게 구현했는지 궁굼합니다.
- MYSQL에서 UUID 타입을 지원하지 않아 VARCHAR 타입으로 테이블을 생성하여
- UUID인 id값을 String.valueOf를 통해 데이터베이스에 저장하였는데 이로인한 문제가 생기는지 궁굼합니다.
- 전체적으로 코드의 일관성과 가독성이 괜찮은지 여쭤보고 싶습니다.


