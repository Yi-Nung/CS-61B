package byow.Core;

import java.time.LocalDateTime;

public class DisplayTime {

    public static String displayTime() {
        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
        return dateTime.toString();
    }
}