# Dokkang Server

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![GithubAction Test & Build](https://github.com/de-alone/dokkang-server/actions/workflows/test-and-build.yml/badge.svg)

This is backend server of dokkang-dokkang application.

## Build

```sh
$ ./gradlew build
BUILD SUCCESSFUL in 634ms
7 actionable tasks: 7 up-to-date

$ ls app/build/libs/app.jar                 
app/build/libs/app.jar

$ java -jar app/build/libs/app.jar
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.4)

2022-10-07 01:16:06.817  INFO 94667 --- [           main] c.d.dokkang.DokkangServerApplication     : Starting DokkangServerApplication using Java 17.0.4.1 on PSJ-MacPro.local with PID 94667 (/Users/psj8252/dokkang-dokkang/dokkang-server/app/build/libs/app.jar started by psj8252 in /Users/psj8252/dokkang-dokkang/dokkang-server)
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

2022-10-07 01:16:35.518  INFO 94931 --- [           main] c.d.dokkang.DokkangServerApplication     : Starting DokkangServerApplication using Java 17.0.4.1 on PSJ-MacPro.local with PID 94931 (/Users/psj8252/dokkang-dokkang/dokkang-server/app/build/classes/java/main started by psj8252 in /Users/psj8252/dokkang-dokkang/dokkang-server/app)
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
