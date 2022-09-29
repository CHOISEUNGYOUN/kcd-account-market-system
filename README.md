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

# Step 3
1. 사용자는 물건을 주문할 수 있다.
   - 어떤 물건을 몇 개 주문할지 선택할 수 있다.
   - 물건의 종류는 1개만 들어온다고 가정한다.

# Step 4
1. 사용자는 매출 혹은 비용을 입력 / 수정 / 삭제할 수 있다.
   - 특정 일자의 순수익이 50만원을 넘으면 자동으로 찜 했던 물건 중 하나를 주문한다.
   - 만약 55만원을 매출을 입력했다, 삭제하면 최종적으로 순수익은 0원이므로 주문이 제거되어야 한다.
   - 한 사용자로부터의 요청은 동시에 1개만 요청됨을 보장할 수 있다.
2. 어드민은 매출 혹은 비용을 조회할 수 있다. 이때 다음과 같은 조건을 만족해야 한다.
   - 특정 일자 내의 모든 거래 건을 확인할 수 있다. 전체를 볼 수도 있다.
   - 매출 or 비용만을 골라 확인할 수 있다. 전체를 볼 수도 있다.
   - 특정 사용자를 골라 확인할 수 있다. 전체를 볼 수도 있다.
   - 어떤 조건을 선택하건 Paging이 되어야 한다.

# Step 5
1. 사용자는 특정 월에 대해 매출과 비용 집계를 확인할 수 있다.
   - 예를 들어, 2022년 1월이라고 한다면, 2022-01-01 부터 2022-01-31까지 매출합계와 비용합계가 화면에 노출되어야 한다.
   - 월별로 장부 데이터가 수십만건씩 쌓이는 경우부터 수천만건씩 쌓이는 경우까지 대응해보자.