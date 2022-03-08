
# 최적화 방식

## Query

```mysql
SELECT *
FROM employees e
WHERE e.emp_no IN (SELECT de.emp_no FROM dept_emp de WHERE de.dept_no = 'd009')

-- employees.emp_no: fk
-- dept_emp.emp_no: pk
```

## pullout 방식

### 정의
- semi join을 하위 query를 inner join으로 변경해서 사용하는 최적화 방식입니다
- subquery 최적화가 잘 안되어 있는 mysql 5.1~5.6에서 주로 사용 되었습니다

### Query

- 최적화 된 query
```mysql
select employees.*
from `employees`.`dept_emp` `de
    join `employees`.`employees` `e`
        where ( (`employees`.`e`.`emp_no` = `employees`.`de`.`emp_no`) and   (`employees`.`de`.`dept_no` = 'd009') );   
```

```shell
# 5.5 버전
> EXPLAIN SELECT * FROM employees e WHERE e.emp_no IN (SELECT de.emp_no FROM dept_emp de WHERE de.dept_no = 'd009');
+------+--------------------+-------+--------+---------+------------+--------+--------------------------+
| id   | select_type        | table | type   | key     | ref        | rows   | Extra                    |
+------+--------------------+-------+--------+---------+------------+--------+--------------------------+
|    1 | PRIMARY            | e     | ALL    | NULL    | NULL       | 300252 | Using where              |
|    2 | DEPENDENT SUBQUERY | de    | eq_ref | PRIMARY | const,func |      1 | Using where; Using index |
+------+--------------------+-------+--------+---------+------------+--------+--------------------------+

# 5.6 버전
+------+-------------+-------+--------+---------------------------+---------+---------+---------------------+-------+--------------------------+
| id   | select_type | table | type   | possible_keys             | key     | key_len | ref                 | rows  | Extra                    |
+------+-------------+-------+--------+---------------------------+---------+---------+---------------------+-------+--------------------------+
|    1 | PRIMARY     | de    | ref    | PRIMARY,ix_empno_fromdate | PRIMARY | 12      | const               | 46914 | Using where; Using index |
|    1 | PRIMARY     | e     | eq_ref | PRIMARY                   | PRIMARY | 4       | employees.de.emp_no |     1 |                          |
+------+-------------+-------+--------+---------------------------+---------+---------+---------------------+-------+--------------------------+
```
1.5.5 버전 이전에서는 subquery에 대해서 최적화가 많이 되지 않은 상태 입니다.
- id가 2개로 나눠져있기 때문에 join 없이 2개의 table을 조회했다고 볼 수 있습니다
- outer table을 outer table인 employees를 fullscan을 합니다
- outer table 결과를 얻은 후 drving table를 호출 해서 where 절 filter를 거치제 됩니다
- 2번의 select가 이뤄지기 때문에 id가 2개가 됩니다(outer table, inner table)

2. 5.6 버전 부터는 최적화가 진행 되어서 inner join 형태로 변경이 됩니다
- 이렇게 inner join으로 변경 되는 것을 pullout 방식 이라고 합니다
- drving table의 pk/unique key를 기반으로 합니다
- inner join형태로 변경 되므로 table 처리순서 변경에 유연하게 처리 합니다
- inner join으로 변경 되어서 select를 한번 하기 때문에 id 가 전부 1로 고정 됩니다


## FirstMatch 최적화

### 정의
- Main Query의 Filtering 효율성이 좋은 경우 사용되며, subquery에서 1건 검색하고 바로 실행 종료하는 semi join 최적화 기법 입니다

```shell
+------+-------------+-------+------+----------------------+--------------+---------+--------------------+------+-----------------------------------------+
| id   | select_type | table | type | possible_keys        | key          | key_len | ref                | rows | Extra                                   |
+------+-------------+-------+------+----------------------+--------------+---------+--------------------+------+-----------------------------------------+
|    1 | PRIMARY     | e     | ref  | PRIMARY,ix_firstname | ix_firstname | 44      | const              |  233 | Using index condition                   |
|    1 | PRIMARY     | t     | ref  | PRIMARY              | PRIMARY      | 4       | employees.e.emp_no |    1 | Using where; Using index; FirstMatch(e) |
+------+-------------+-------+------+----------------------+--------------+---------+--------------------+------+-----------------------------------------+
```

### IN-to-Exist 보다 FirstMatch가 더 좋은 장점
- 동등 조건 전파(Equality popagation)가 subquery에서만 가능하던게 outer table에서도 가능
- 최적화 방식을 옵티마이저가 선택해서 firtmatch를 사용할지 아님 다른 실행 게획을 실행할지 선택할 수 있습니다

### CF) 동등 전파(Equality popagation)
- 내부에서 동일한 변수에 대해서 치환할 수 있는 기능
- ex) where col1 = col2 AND col2 = 123 -> where col1 = 123 으로 동등 치환 가능

### FirstMatch 제약 사항 및 특징
- 1건 검색하고 멈추는 단축 실행 경로를 사용 합니다
- outer를 먼저 조회하고, subquery를 조회 합니다
- subquery에 group by가 있을 경우 최적화 불가능 -> 모든 groupping 을 하기때문에 first row 정책에 위배가 됩니다
- outer query의 독립적으로 인덱스를 적절히 사용할 수 있는 조건이 없다면, Semi-join Materializaion 최적화 방식을 사용합니다

### CF)
- 현재 저의 project 에서 사용하고 있는 최적화 방식입니다


## Materializaion 최적화

### 정의
- outer table에 적절한 index가 없어서 full scan해야 할 경우 사용하는 옵티마이저 방식
- subquery table을 구체화(Materializaion) 하여 join 형태로 문제를 해결하는 방식입니다

### 제약 사항 및 특징
- 서브쿼리는 상관서브쿼리가 아니어야 한다(비상관)
- subquery에 index가 선언되어있을 경우 Materializaion table에도 index가 생성되어 적용이 됩니다
- Group by 사용 가능
- 임시테이블 사용

### Cf)
- 상관 서브쿼리: outer query와 상관 관계가 있는 쿼리를 의미 합니다
- 비상관 서브쿼리: query 하나 자체 만으로 실행 가능한 쿼리를 의미 합니다, outer query와 독립된 query를 의미 합니다

```mysql
-- 상관 서브 쿼라

select * from food
    where food.food_id in (select food_tag.food_id from food_tag where food_tag.food_id = food.food_id;

-- 비상관 서브 쿼리
select * from food
    where food.food_id in (select food_tag.food_id from food_tag where food_tag.food_tag_id in (1, 2, 3));
```

### Materializaion 최적화 전략
- Scan: 구체화된 임시 테이블 full scan 하는 방식(index가 없을 경우)
- Lookup: 구체화된 임시 테이블에 distinct_key index를 사용하는 경우

### LooseScan 최적화
1. 정의: semi join에서 모든 레코드를 읽지 않고, 조건에 사용된 index를 유니크한 값만 읽어오는 방법
  - subquery쪽 index에서 unique 한 값을 추출 후 main query table을 조인 처리하는 것

```shell
mysql> explain SELECT tb_a.* FROM tb_a WHERE id IN (SELECT id FROM tb_c);
+----+-------------+-------+--------+---------------+---------+---------+--------------+-------+------------------------+
| id | select_type | table | type   | possible_keys | key     | key_len | ref          | rows  | Extra                  |
+----+-------------+-------+--------+---------------+---------+---------+--------------+-------+------------------------+
|  1 | SIMPLE      | tb_c  | index  | idx_id        | idx_id  | 4       | NULL         | 10157 | Using index; LooseScan |
|  1 | SIMPLE      | tb_a  | eq_ref | PRIMARY       | PRIMARY | 4       | test.tb_c.id |     1 | NULL                   |
+----+-------------+-------+--------+---------------+---------+---------+--------------+-------+------------------------+
```


### MySQL5.6 부터 Subquery 최적화 된 케이스
- IN(subquery) 또는 = ANY(subquery) 형태
- UNION 없는 단일 SELECT (Materializaion 등으로 최적화 가능)
- 집계함수 와 HAVING 절을 가지지 않는 subquery
- 서브쿼리의 WHERE 조건이 외부쿼리의 다른 조건과 AND 로 연결된 케이스