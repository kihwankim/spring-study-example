## 복구할 수 있는 예외와 프로그래밍 오류

### 예외 종류

- 검사 예외
- 런타임 예외
- 에러

## 검사 예외

### 정의 
- API를 호출하는 쪽에서 복구할 수 있을 경우 검사 예외를 사용합니다
- 예외를 catch문으로 잡아서 처리합니다
- catch를 사용하지 않을 경우 method 에 붙이는 throws keyword를 사용해서 더 외부로 보내기도합니다

### 사용 목적
- 개발자가 의미있는 조치를 취할 수 있는 경우(이미지 업로드 실패)
- API를 잘 상요해서 발생할 수 있는 경우에 사용 됩니다

### 검사 예외 회피 방법
- 예외 케이스에 대해서 옵션널에 싸서 반환 하는 방식이 좋을 수 있습니다
- 예외 부가 정보를 담을 수 없는 단점이 있지만 검사 예외를 try catch 를 통해서 처리하지 않아도 처리할 수 있는 장점이 있습니다

## 비검사 Throwable

- Runtime 예외, Error로 나뉘어 집니다
- 정의: 프로그램에서 잡을 필요가 없거나, 통상적으로 catch문으로 에러를 처리하지 않는케이스에 사용됩니다

## Runtime 예외

- 비검사 Throwable의 일종입니다
- 프로그래밍 오류가 발생할 경우 주로 사용하는 예외 입니다
- 대부분 API의 제약 조건을 지키지 않을떄 발생합니다

## Error
- JVM이 자원이 부족한 경우, 불변식 깨질 경우 사용 합니다
- 