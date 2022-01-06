package platform.util;

import org.junit.jupiter.api.Test;
import platform.Code;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void getTimeDifference() throws InterruptedException {
        Util util = new Util();
        Code code = new Code();
        code.setDate(LocalDateTime.now());

        Thread.sleep(3000);

        assertEquals(1, util.getTimeDifference(code.getTime(), code.getDate()));
    }
}