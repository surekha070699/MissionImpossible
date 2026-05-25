package org.example.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class TimeOut {

    public static final int NANO_TIMEOUT_IN_SEC = 4;
    public static final int MIN_TIMEOUT_IN_SEC = 30;
    public static final int AVERAGE_TIMEOUT_IN_SEC = 60;

    /*We have increased time to 4min as there is a delay
    in getting Correlation ID from CDAX.*/
    public static final int MAX_TIMEOUT_IN_SEC = 360;
    public static final int CASE_DISPATCH_TIMEOUT_IN_SEC = 360;
    public static final int DEFAULT_NAVIGATION_TIMEOUT_SEC = 120;
    public static final int DEFAULT_POLL_TIMEOUT_MS = 200;
    public static final int MAXIMUM_POLL_TIMEOUT_MS = 6000;
    public static final int NANO_POLL_TIMEOUT_MS = 2000;

    public static final int MIN_TIMEOUT_IN_MS = 3000;
    public static final int AVERAGE_TIMEOUT_IN_MS = 5000;
    public static final int MAX_TIMEOUT_IN_MS = 7000;

}