# 使用包含 Maven 的官方 OpenJDK 镜像
FROM maven:3.8.4-openjdk-17 AS build

# 设置工作目录
WORKDIR /workspace/app

# 复制项目文件
COPY pom.xml /workspace/app/pom.xml
COPY src /workspace/app/src

# 预下载 Maven 依赖项以缓存
RUN mvn dependency:go-offline

# 构建项目并跳过测试
RUN mvn package -DskipTests

# 使用新的清洁镜像运行构建好的应用
FROM openjdk:17-slim

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 jar 文件
COPY --from=build /workspace/app/target/*.jar agent.jar

EXPOSE 8088

# 设置容器启动时执行 Java 应用
ENTRYPOINT ["java","-jar","agent.jar"]
