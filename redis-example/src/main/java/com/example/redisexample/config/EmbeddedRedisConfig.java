package com.example.redisexample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Profile("local2")
@Configuration
public class EmbeddedRedisConfig {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        if (!isRunningPort(redisPort)) {
            redisServer = new RedisServer();
            redisServer.start();
            System.out.println("start redis");
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
            System.out.println("stop redis");
        }
    }

    public static boolean isRunningPort(int port) throws IOException {
        return isRunning(executeGrepProcessCommand(port));
    }

    private static boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("사용가능한 포트를 찾는 중 에러가 발생하였습니다.");
        }
        return StringUtils.hasLength(pidInfo.toString());
    }

    private static Process executeGrepProcessCommand(int port) throws IOException {
        // 윈도우일 경우
        if (isWindows()) {
            String command = String.format("netstat -nao | find \"LISTEN\" | find \"%d\"", port);
            String[] shell = {"cmd.exe", "/y", "/c", command};
            return Runtime.getRuntime().exec(shell);
        }
        String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    private static boolean isWindows() {
        return OS.contains("win");
    }
}
