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