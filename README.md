# Dokkang Server

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![GithubAction Test & Build](https://github.com/de-alone/dokkang-server/actions/workflows/test-and-build.yml/badge.svg)
[![codecov](https://codecov.io/gh/de-alone/dokkang-server/branch/master/graph/badge.svg?token=C07Y129YPC)](https://codecov.io/gh/de-alone/dokkang-server)

This is backend server of dokkang-dokkang application.

## Build

```sh
$ ./gradlew build
BUILD SUCCESSFUL in 634ms
7 actionable tasks: 7 up-to-date

$ ls build/libs/dokkang-server.jar                 
build/libs/dokkang-server.jar

$ java -jar build/libs/dokkang-server.jar
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.4)

2022-10-07 01:16:06.817  INFO 94667 --- [           main] c.d.dokkang.DokkangServerApplication     : Starting DokkangServerApplication using Java 17.0.4.1 on PSJ-MacPro.local with PID 94667 (/Users/psj8252/dokkang-dokkang/dokkang-server/build/libs/dokkang-server.jar started by psj8252 in /Users/psj8252/dokkang-dokkang/dokkang-server)
2022-10-07 01:16:06.820  INFO 94667 --- [           main] c.d.dokkang.DokkangServerApplication     : No active profile set, falling back to 1 default profile: "default"
2022-10-07 01:16:07.614  INFO 94667 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2022-10-07 01:16:07.623  INFO 94667 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-10-07 01:16:07.623  INFO 94667 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.65]
2022-10-07 01:16:07.693  INFO 94667 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-10-07 01:16:07.693  INFO 94667 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 827 ms
2022-10-07 01:16:07.971  INFO 94667 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-10-07 01:16:07.979  INFO 94667 --- [           main] c.d.dokkang.DokkangServerApplication     : Started DokkangServerApplication in 1.446 seconds (JVM running for 1.759)
```

## Testing

```sh
$ ./gradlew test

BUILD SUCCESSFUL in 560ms
3 actionable tasks: 3 up-to-date
```

## Direct Run

```sh
$ ./gradlew run

> Task :app:run

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.4)

2022-10-07 01:16:35.518  INFO 94931 --- [           main] c.d.dokkang.DokkangServerApplication     : Starting DokkangServerApplication using Java 17.0.4.1 on PSJ-MacPro.local with PID 94931 (/Users/psj8252/dokkang-dokkang/dokkang-server/build/classes/java/main started by psj8252 in /Users/psj8252/dokkang-dokkang/dokkang-server/app)
2022-10-07 01:16:35.521  INFO 94931 --- [           main] c.d.dokkang.DokkangServerApplication     : No active profile set, falling back to 1 default profile: "default"
2022-10-07 01:16:36.111  INFO 94931 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2022-10-07 01:16:36.122  INFO 94931 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-10-07 01:16:36.122  INFO 94931 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.65]
2022-10-07 01:16:36.187  INFO 94931 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-10-07 01:16:36.188  INFO 94931 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 632 ms
2022-10-07 01:16:36.424  INFO 94931 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-10-07 01:16:36.433  INFO 94931 --- [           main] c.d.dokkang.DokkangServerApplication     : Started DokkangServerApplication in 1.157 seconds (JVM running for 1.42)
<=========----> 75% EXECUTING [6s]
> :app:run
```

## API Specification

### Auth

#### Create Auth Token (Login)

인증 토큰을 생성하여 로그인 합니다.

- **Endpoint**: /auth
- **Request**
    - Method: POST
    - Content-Type: application/json
    
    | Name | Type |
    | --- | --- |
    | username | string |
    | password | string |
- **Response**
    - Content-Type: json
    - Http Status:
        - Login Success: 201
        - user id doesn’t exist: 404
        - password not matched: 401
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |
    | token | string | this field exists only if the login trial is success |
    | user_id | long | unique user id (primary key) ※ 로그인 시 사용하는 id 아님 |

### User

#### Create User (Sign Up)

새 유저를 추가합니다.

- **Endpoint**: /user
- **Request**
    - Method: POST
    - Content-Type: json
    
    | Name | Type |
    | --- | --- |
    | username | string |
    | email | string |
    | password | string |
- **Response**
    - Content-Type: json
    - Http Status:
        - Login Success: 201
        - Login Failure: 401
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |

#### Read User Detail

유저의 세부정보를 반환합니다.

- **Endpoint**: /user/{user_id}
- **Request**
    - Method: GET
    - Authorization: Bearer [Token]
- **Response**
    - Content-Type: json
    - Http Status:
        - Success: 200
    
    | Name | Type |
    | --- | --- |
    | id | long |
    | username | string |
    | email | string |

#### Read User’s Lecture List

특정 유저가 선택한 모든 강의 목록을 반환합니다. (Return all lecture list)

- **Endpoint**: /user/{user_id}/lectures
- **Request**
    - Method: GET
    - Authorization: Bearer [Token]
- **Response**
    - Content-Type: json
    - Http Status:
        - Success: 200
        - Failure: 400
    
    **Response Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” according to result |
    | lectures | list of lecture | a list of lectures |
    
    **Lecture Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | id | long | 각각의 과목을 구별하기 위한 고유 id |
    | no | string | 학수번호 (e.g “SWE3053-41”) |
    | name | string | 과목명 (e.g “HCI개론”) |
    | professor | string | 교수 이름 (e.g “조재민”) |

#### Update User’s Lecture List

기존의 유저가 선택한 과목을 모두 없애고 다시 새로 추가합니다.

- **Endpoint**: /user/{user_id}/lectures
- **Request**
    - Method: PUT
    - Authorization: Bearer [Token]
    - Content-Type: json
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | lecture_ids | list of long | a list of lecture_ids e.g) [1, 4, 5] |
- **Response**
    - Content-Type: json
    - Http Status:
        - Update Success: 200
        - Unauthorized: 401
        - Bad Request: 400
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |

### Lecture

#### Get All Lectures

모든 강의 목록을 반환합니다. (Return all lecture list)

- **Endpoint**: /lectures
- **Request**
    - Method: GET
    - Authorization: Bearer [Token]
- **Response**
    - Content-Type: json
    - Http Status:
        - Success: 200
    
    **Response Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” according to result |
    | lectures | list of lecture | a list of lectures |
    
    **Lecture Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | id | long | 각각의 과목을 구별하기 위한 고유 id |
    | no | string | 학수번호 (e.g “SWE3053-41”) |
    | name | string | 과목명 (e.g “HCI개론”) |
    | professor | string | 교수 이름 (e.g “조재민”) |

### Post

#### Create a Post

게시판에 새 글을 작성합니다.

- **Endpoint**: /post
- **Request**
    - Method: POST
    - Authorization: Bearer [Token]
    - Content-Type: json
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | lecture_id | long | 과목의 id |
    | user_id | long | 작성한 유저의 id |
    | title | string | 글의 제목 |
    | content | string | 글의 내용 |
- **Response**
    - Content-Type: json
    - Http Status:
        - Create Success: 201
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |
    | post_id | long | 게시글의 고유 id |

#### Read a Post

특정 게시글 하나를 세부 정보와 함께 가져옵니다.

- **Endpoint**: /post/{post_id}
- **Request**
    - Method: GET
    - Authorization: Bearer [Token]
- **Response**
    - Content-Type: json
    - Http Status:
        - Success: 200
    
    **Response Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” according to result |
    | id | long | 각각의 글을 구별하기 위한 고유 id |
    | lecture_id | long | 과목 id |
    | user_id | long | 작성자의 고유 id |
    | username | string | 작성자의 유저네임(로그인 id) |
    | title | string | 게시글의 제목 |
    | content | string | 글의 내용 |
    | created_at | string | 글이 작성된 시간의 isoformat |
    | num_likes | int | 글의 좋아요 개수 |
    | comments | list of comment | 댓글들 |
    
    **Comment Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | id | long | 각각의 댓글을 구별하기 위한 고유 id |
    | user_id | long | 댓글을 작성한 사람의 id |
    | content | string | 댓글의 내용 |
    | created_at | string | 댓글이 작성된 시간의 isoformat |

#### Get Posts of Lecture

특정 강의의 게시판 게시글들을 가져옵니다.

- **Endpoint**: /lecture/{lecture_id}/posts
- **Request**
    - Method: GET
    - Authorization: Bearer [Token]
    - Query Parameter:
        
        
        | Name | Type | Comment |
        | --- | --- | --- |
        | limit | int | 최대 몇 개의 게시글을 불러올지 |
        | before | str(optional) | 없으면 가장 최신글을 불러옴. 있으면 이 시간 이전에 작성된 글만 가져옴. |
- **Response**
    - Content-Type: json
    - Http Status:
        - Success: 200
    
    **Response Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” according to result |
    | posts | list of post | a list of posts |
    | before | str | 다음에 이 값을 파라미터로 넘겨 요청하면 이 시간보다 더 이전에 생성된 글을 불러옴.  |
    
    **Post Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | id | long | 각각의 글을 구별하기 위한 고유 id |
    | lecture_id | long | 과목 id |
    | title | string | 게시글의 제목 |
    | num_likes | int | 글의 좋아요 개수 |
    | num_comments | int | 글의 댓글 개수 |
    | created_at | string | 글이 작성된 시간의 문자열 표현 |

### Like

#### Like a Post

글에 좋아요를 누릅니다.

- **Endpoint**: /like
- **Request**
    - Method: POST
    - Authorization: Bearer [Token]
    - Content-Type: json
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | post_id | long | 게시글의 id |
    | user_id | long | 작성한 유저의 id |
- **Response**
    - Content-Type: json
    - Http Status:
        - Create Success: 201
        - Already Exists: 409
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |

### Comment

#### Create comment on post

게시글에 새 댓글을 작성합니다.

- **Endpoint**: /comment
- **Request**
    - Method: POST
    - Authorization: Bearer [Token]
    - Content-Type: json
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | post_id | long | 과목의 id |
    | user_id | long | 작성한 유저의 id |
    | content | string | 댓글의 내용 |
- **Response**
    - Content-Type: json
    - Http Status:
        - Create Success: 201
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |
    | comment_id | long | 댓글의 고유 id |

### StudyGroup

#### Create a StudyGroup

새 스터디그룹을 생성합니다.

- **Endpoint**: /studygroup
- **Request**
    - Method: POST
    - Authorization: Bearer [Token]
    - Content-Type: json
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | lecture_id | long | 과목의 id |
    | user_id | long | 작성한 유저의 id |
    | title | string | 글의 제목 |
    | content | string | 글의 내용 |
    | studycapacity | int | 스터디 정원 |
    | studytime | string | 스터디 시간 |
    | studyplace | string | 스터디 장소 |
- **Response**
    - Content-Type: json
    - Http Status:
        - Create Success: 201
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |
    | studygroup_id | long | 스터디의 고유 id |

#### Read a StudyGroup

특정 스터디그룹 하나를 세부 정보와 함께 가져옵니다.

- **Endpoint**: /studygroup/{studygroup_id}
- **Request**
    - Method: GET
    - Authorization: Bearer [Token]
- **Response**
    - Content-Type: json
    - Http Status:
        - Success: 200
    
    **Response Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” according to result |
    | id | long | 각각의 스터디를 구별하기 위한 고유 id |
    | lecture_id | long | 과목 id |
    | user_id | long | 작성자의 고유 id |
    | username | string | 작성자의 유저네임(로그인 id) |
    | title | string | 게시글의 제목 |
    | content | string | 글의 내용 |
    | created_at | string | 글이 작성된 시간의 isoformat |
    | num_likes | int | 글의 좋아요 개수 |
    | comments | list of comment | 댓글들 |
    | participants | list of string | 참여자들의 username 리스트 (개설자 포함) |
    | studytime | string | 스터디 시간 |
    | studyplace | string | 스터디 장소 |
    | studycapacity | int | 스터디 정원 |
    
    **Comment Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | id | long | 각각의 댓글을 구별하기 위한 고유 id |
    | user_id | long | 댓글을 작성한 사람의 id |
    | content | string | 댓글의 내용 |
    | created_at | string | 댓글이 작성된 시간의 isoformat |

#### Get StudyGroups of Lecture

특정 강의의 스터디 그룹 게시글을 가져옵니다.

- **Endpoint**: /lecture/{lecture_id}/studygroups
- **Request**
    - Method: GET
    - Authorization: Bearer [Token]
    - Query Parameter:
        
        
        | Name | Type | Comment |
        | --- | --- | --- |
        | limit | int | 최대 몇 개의 스터디를 불러올지 |
        | before | str | 없으면 가장 최신 스터디를 불러옴. 있으면 이 시간 이전에 작성된 스터디만 가져옴. |
- **Response**
    - Content-Type: json
    - Http Status:
        - Success: 200
    
    **Response Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” according to result |
    | studygroups | list of studygroup | a list of studygroups |
    | before | str | 다음에 이 값을 파라미터로 넘겨 요청하면 이 시간보다 더 이전에 생성된 글을 불러옴.  |
    
     **Study Group Structure**
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | id | long | 각각의 스터디를 구별하기 위한 고유 id |
    | lecture_id | long | 과목 id |
    | title | string | 게시글의 제목 |
    | num_likes | int | 글의 좋아요 개수 |
    | num_comments | int | 글의 댓글 개수 |
    | created_at | string | 글이 작성된 시간의 문자열 표현 |
    | opened | bool | 스터디 그룹의 정원이 다 찼는지 여부 |

### StudyGroup Like

#### Like a StudyGroup

스터디에 좋아요를 누릅니다.

- **Endpoint**: /studygroup-like
- **Request**
    - Method: POST
    - Authorization: Bearer [Token]
    - Content-Type: json
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | studygroup_id | long | 스터디그룹의 id |
    | user_id | long | 작성한 유저의 id |
- **Response**
    - Content-Type: json
    - Http Status:
        - Create Success: 201
        - Already Exists: 409
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |

### StudyGroup Participants

#### Participate a StudyGroup

스터디에 참여합니다.

- **Endpoint**: /studygroup-participant
- **Request**
    - Method: POST
    - Authorization: Bearer [Token]
    - Content-Type: json
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | studygroup_id | long | 게시글의 id |
    | user_id | long | 작성한 유저의 id |
- **Response**
    - Content-Type: json
    - Http Status:
        - Create Success: 201
        - Already Exists: 409
        - 정원마감: 423
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |

### StudyGroup Comment

#### Create comment on studygroup

스터디에 새 댓글을 작성합니다.

- **Endpoint**: /studygroup-comment
- **Request**
    - Method: POST
    - Authorization: Bearer [Token]
    - Content-Type: json
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | studygroup_id | long | 스터디 그룹의 id |
    | user_id | long | 작성한 유저의 id |
    | content | string | 댓글의 내용 |
- **Response**
    - Content-Type: json
    - Http Status:
        - Create Success: 201
    
    | Name | Type | Comment |
    | --- | --- | --- |
    | status | string | “ok” or “error” |
    | comment_id | long | 댓글의 고유 id |
