# ---- Stage 1: Build fat JAR ----
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q


# ---- Stage 2: Extract layered JAR (Spring Boot 3.x) ----
FROM eclipse-temurin:21-jdk-jammy AS extractor

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract


# ---- Stage 3: Build minimal custom JRE with jlink ----
FROM eclipse-temurin:21-jdk-jammy AS jre-builder

COPY --from=builder /app/target/*.jar app.jar
RUN jar xf app.jar && \
    DEPS=$(jdeps \
      --ignore-missing-deps \
      --print-module-deps \
      --multi-release 21 \
      --recursive \
      --class-path 'BOOT-INF/lib/*' \
      app.jar 2>/dev/null) && \
    echo "Detected modules: $DEPS" && \
    $JAVA_HOME/bin/jlink \
      --add-modules "$DEPS" \
      --strip-debug \
      --no-man-pages \
      --no-header-files \
      --compress=2 \
      --output /opt/jre


# ---- Stage 4: Final distroless image ----
FROM gcr.io/distroless/base-debian12

COPY --from=jre-builder /opt/jre /opt/jre

WORKDIR /app
COPY --from=extractor /app/dependencies/ ./
COPY --from=extractor /app/spring-boot-loader/ ./
COPY --from=extractor /app/snapshot-dependencies/ ./
COPY --from=extractor /app/application/ ./

EXPOSE 8080

ENTRYPOINT ["/opt/jre/bin/java", "org.springframework.boot.loader.launch.JarLauncher"]