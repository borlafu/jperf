/*
 * Copyright (c) 2008-2015 JPerf
 * All rights reserved.  http://www.jperf.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.jperf.aop;

import org.aspectj.lang.annotation.Aspect;
import org.apache.log4j.Level;
import net.jperf.LoggingStopWatch;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is used by the AOP tests to check when the aspect was called
 */
@Aspect
public class InMemoryTimingAspect extends ProfiledTimingAspect {
    public static List<String> logStrings = Collections.synchronizedList(new ArrayList<String>());

    protected LoggingStopWatch newStopWatch(final String loggerName, final String levelName) {
        return new LoggingStopWatch() {
            private static final long serialVersionUID = -1537100122960737661L;

            public boolean isLogging() {
                return Level.toLevel(levelName).toInt() >= Level.INFO_INT;
            }

            protected void log(String stopWatchAsString, Throwable exception) {
                InMemoryTimingAspect.logStrings.add(stopWatchAsString);
            }
        };
    }

    public static String getLastLoggedString() {
        if (logStrings.size() > 0) {
            return logStrings.get(logStrings.size() - 1);
        } else {
            return null;
        }
    }
}
