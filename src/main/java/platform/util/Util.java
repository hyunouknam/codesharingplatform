package platform.util;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class Util {

    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }

    public int getTimeDifference(int originalTimeLeft, LocalDateTime originalDate) {
        Duration d = (Duration.between(originalDate, getCurrentDate()));
        return originalTimeLeft - (int) d.getSeconds();
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }
}
