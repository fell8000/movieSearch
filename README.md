# movieSearch

with Java, Python, MYSQL, DataBase with Movielens

JAVA에서 특정 조건들을 가지고 PYTHON으로 SQL 서버에 업로딩한 데이터들을 검색하는 리뷰 검색 엔진.

여러 검색 방식(특정 단어 포함, 서브쿼리) 등을 SQL 쿼리문을 통해 다양하게 구현해보고 Join 등의 기능들을 테스팅 해보는 

영화 리뷰 검색 엔진 개발 프로젝트



### PYTHON (MovieDataUploader)

: 크기가 큰 tsv 파일 SQL 테이블에 일괄 업로드.
  
  테이블까지 파이썬에서 짜는 방식과 미리 테이블을 작성하여 데이터 삽입하는 방식이 있는데 후자를 선택하였음


### JAVA (MovieSearchTool)

: 조건을 가지고 movie에 관련된 정보로 검색을 구현한 자바 코드

검색옵션 (선택 / 비선택 가능)

+ 장르

+ 리뷰어 직업

+ 평균평점의 최소 최대값

+ 정렬 옵션 (종류, 제목, 평점, VOTE)

### SQL (MYSQL 파일)

: 특정 sql 문들을 통해 정보 업로딩을 위한 테이블을 구성하는 코드


