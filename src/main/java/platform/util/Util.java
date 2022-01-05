package platform.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class Util {

    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }
}
