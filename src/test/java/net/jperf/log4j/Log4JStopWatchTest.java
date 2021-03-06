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
package net.jperf.log4j;

import junit.framework.Assert;
import net.jperf.LoggingStopWatch;
import net.jperf.LoggingStopWatchTest;
import net.jperf.StopWatch;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;

/**
 * Tests the Log4JStopWatch. See the superclass for the test method that is run.
 */
public class Log4JStopWatchTest extends LoggingStopWatchTest {

    protected void setUp() throws Exception {
        super.setUp();

        Logger defaultLogger = Logger.getLogger(StopWatch.DEFAULT_LOGGER_NAME);
        ConsoleAppender stdErrAppender = new ConsoleAppender(new SimpleLayout(), ConsoleAppender.SYSTEM_ERR);
        stdErrAppender.setName("log4jStdErrAppender");
        stdErrAppender.activateOptions();
        defaultLogger.addAppender(stdErrAppender);
    }

    protected void tearDown() throws Exception {
        Logger defaultLogger = Logger.getLogger(StopWatch.DEFAULT_LOGGER_NAME);
        defaultLogger.removeAppender("log4jStdErrAppender");
        super.tearDown();
    }

    protected LoggingStopWatch createStopWatch(String loggerName,
                                               String normalPriorityName,
                                               String exceptionPriorityName,
                                               String tag,
                                               String message) {
        if (loggerName == null && normalPriorityName == null && exceptionPriorityName == null
            && tag == null && message == null)  {
            return new Log4JStopWatch();
        }
        if (loggerName == null && normalPriorityName == null && exceptionPriorityName == null && message == null)  {
            return new Log4JStopWatch(tag);
        }
        if (loggerName == null && normalPriorityName == null && exceptionPriorityName == null)  {
            return new Log4JStopWatch(tag, message);
        }
        if (normalPriorityName == null && exceptionPriorityName == null && tag == null && message == null)  {
            return new Log4JStopWatch(Logger.getLogger(loggerName));
        }
        if (exceptionPriorityName == null && tag == null && message == null)  {
            return new Log4JStopWatch(Logger.getLogger(loggerName), Level.toLevel(normalPriorityName));
        }
        if (tag == null && message == null)  {
            return new Log4JStopWatch(Logger.getLogger(loggerName),
                                      Level.toLevel(normalPriorityName), Level.toLevel(exceptionPriorityName));
        }
        if (normalPriorityName == null && exceptionPriorityName == null && message == null)  {
            return new Log4JStopWatch(tag, Logger.getLogger(loggerName));
        }
        if (exceptionPriorityName == null && message == null)  {
            return new Log4JStopWatch(tag, Logger.getLogger(loggerName), Level.toLevel(normalPriorityName));
        }
        if (message == null)  {
            return new Log4JStopWatch(tag, Logger.getLogger(loggerName),
                                      Level.toLevel(normalPriorityName), Level.toLevel(exceptionPriorityName));
        }
        if (normalPriorityName == null && exceptionPriorityName == null)  {
            return new Log4JStopWatch(tag, message, Logger.getLogger(loggerName));
        }
        if (exceptionPriorityName == null)  {
            return new Log4JStopWatch(tag, message, Logger.getLogger(loggerName), Level.toLevel(normalPriorityName));
        }
        return new Log4JStopWatch(tag, message, Logger.getLogger(loggerName),
                                  Level.toLevel(normalPriorityName), Level.toLevel(exceptionPriorityName));
    }

    protected void customTests() {
        Log4JStopWatch stopWatch = new Log4JStopWatch(0L, 1000L, "tag", "message",
                                                      Logger.getLogger("net.jperf"), Level.TRACE, Level.INFO);
        Assert.assertEquals(0L, stopWatch.getStartTime());
        Assert.assertEquals(1000L, stopWatch.getElapsedTime());
        Assert.assertEquals("tag", stopWatch.getTag());
        Assert.assertEquals("message", stopWatch.getMessage());
        assertEquals("net.jperf", stopWatch.getLogger().getName());
        assertEquals(Level.TRACE, stopWatch.getNormalPriority());
        assertEquals(Level.INFO, stopWatch.getExceptionPriority());

        stopWatch.setLogger(Logger.getLogger("net.jperf.AnotherTestLogger"));
        assertEquals("net.jperf.AnotherTestLogger", stopWatch.getLogger().getName());
        stopWatch.setNormalPriority(Level.DEBUG);
        assertEquals(Level.DEBUG, stopWatch.getNormalPriority());
        stopWatch.setExceptionPriority(Level.WARN);
        assertEquals(Level.WARN, stopWatch.getExceptionPriority());
        Logger.getLogger("net.jperf.AnotherTestLogger").setLevel(Level.DEBUG);
        assertTrue(stopWatch.isLogging());
        Logger.getLogger("net.jperf.AnotherTestLogger").setLevel(Level.WARN);
        assertFalse(stopWatch.isLogging());
    }

    protected void checkProperties(LoggingStopWatch stopWatch,
                                   String expectedLoggerName,
                                   String expectedNormalPriority,
                                   String expectedExceptionPriority,
                                   String expectedTag,
                                   String expectedMessage) {
        super.checkProperties(stopWatch, expectedLoggerName, expectedNormalPriority, expectedExceptionPriority,
                              expectedTag, expectedMessage);
        Log4JStopWatch log4JStopWatch = (Log4JStopWatch) stopWatch;
        assertEquals(expectedLoggerName, log4JStopWatch.getLogger().getName());
        assertEquals(expectedNormalPriority, log4JStopWatch.getNormalPriority().toString());
        assertEquals(expectedExceptionPriority, log4JStopWatch.getExceptionPriority().toString());
    }
}
