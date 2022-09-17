# STEP 1
1. 물건을 입력 / 수정 / 삭제할 수 있다.
    - 물건의 필수 데이터 : 항목, 가격, 재고
2. 회원가입이 가능하다.
    - 비밀번호 암호화 방식은 SHA-256, Bcrypt 등 자유롭게 사용해도 무관한다.
    - Bcrypt 의존성은 아래 존재한다.
```groovy
dependencies {
    implementation "org.mindrot:jbcrypt:0.4"
}
```

# Step 2
1. 사용자는 특정 물건에 대해 '찜'을 할 수 있다.
   - 원래는 Login을 통해 발급받은 토큰을 사용해야 하지만,
   - 요구사항을 간소화하기 위해 사용자는 email을 Authorization Header로 보낸다.
   - 이를 통해 어떤 사용자가 API를 호출하였는지 식별할 수 있다.
   - 추후에 토큰을 사용할 수 있다.
   