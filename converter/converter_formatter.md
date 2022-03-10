
## 과거의 방식
### PropertyEditor
- spring 초창기에 사용했는 type변환기 입니다
- String to Java class로 변환 하는 기능을 제시했습니다
- 동시성 문제가 있기때문에 Spring 3.0 부터는 다른 것이 사용되었습니다

### 변경
- Spring 3.0부터 범용 타입 변환 시스템도입 되었습니다
- String java type변환과 POJO간 변환을 지원하는 기능이 들어있는 Converter가 추가 되었습니다 

### 타입 변경 적용 범위
- Spring MVC 파라미터: @RequestParam, @ModelAttribute, @PathVariable
- @Value등으로 yml 파일 읽기
- view rendering

## Converter

### 정의
- String to Java 객체, POJO간의 변환에 도움을 주는 기능 입니다
- thread safe합니다

### 등록
- WebMvcConfigurer의 메서드를 통해서 converterService에 등록이 됩니다
- ConverterService는 실제 Converter에 등록한 data변경 로직을 호출하는 서비스 로직이 들어있는 곳 입니다
- ConverterService에 FormatterRegistry를 상속받은 하나의 구현체 입니다 그렇기 때문에 아래와 같이 등록하면 자동으로 converter service에 등록이 됩니다

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override // coverter service에 등록
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConvereter());
    }
}


public class LocalDateConvereter implements Converter<String, LocalDate> {
  private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);

  @Override
  public LocalDate convert(String source) {
    return LocalDate.parse(source, DATE_FORMAT);
  }
}
```

### ApplicationConverterService
- ConverterService와 ConverterRegistery를 둘다 상속받은 구현체입니다(ISP 원리 -> 인터페이스 분리 원칙에 의해서 2가지로 분리해서 구현되어있습니다)
  - ConverterService: converter 사용에 초첨
  - ConverterRegistery: converter 등록에 초점
- Spring에서 개발자가 직접 개발한 Converter는 Spring에서 지원하는 Converter에 비해서 우선순위가 낮습니다
- Spring MVC에서는 Handler의 ArgumentResolver의 구현체들이 호출 되게 되면 내부에서 ConverterService를 호출해서 필요한 type으로 변환합니다

## Formatter

### 정의
- 객체를 특정한 format에 맞춰 문자로 출력, 또는 문자를 객체로 변환 하는 기능을 제공합니다
- locale 기능을 적용할 수 있습니다
  - ex) NumberFormat: locale 정보를 통해서 나라별 다른 숫자 format을 만들어 줍니다

### 등록
- WebMvcConfigurer의 메서드를 통해서 formatterService에등록합니다
- 현재 Spring에서는 ConverterService와 FromatterService를 모두 상속받는 `ApplicationConversionService`를 사용합니다 그렇기 때문에 Converter등록과 동일하게 formatter를 등록 할 수 있습니다

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new LocalDateConvereter()); // converter 등록
    registry.addFormatter(new LocalDateTimeFormatter()); // formatter 등록
  }
}

// formatter 생성
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
  private static final String DEFAULT_LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(DEFAULT_LOCAL_DATE_TIME_PATTERN);

  @Override
  public LocalDateTime parse(String text, Locale locale) throws ParseException { // formatter를 이용해서 타입 변경
    return LocalDateTime.parse(text, DATETIME_FORMAT);
  }

  @Override
  public String print(LocalDateTime object, Locale locale) { // 출력 형태
    return object.toString();
  }
}
```

## 차이점
- Converter: 범용(객체 -> 객체)
- Formatter: 문자에 특화(객체 -> 문자, 문자) -> 객체 + Locale
