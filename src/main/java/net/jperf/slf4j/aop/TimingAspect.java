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
package net.jperf.slf4j.aop;

import net.jperf.aop.ProfiledTimingAspect;
import org.aspectj.lang.annotation.Aspect;
import net.jperf.slf4j.Slf4JStopWatch;
import org.slf4j.LoggerFactory;

/**
 * This TimingAspect implementation uses a SLF4J Logger instance to persist StopWatch log messages.
 *
 * @author Alex Devine
 */
@Aspect
public class TimingAspect extends ProfiledTimingAspect {

    protected Slf4JStopWatch newStopWatch(String loggerName, String levelName) {
        int levelInt = Slf4JStopWatch.mapLevelName(levelName);
        return new Slf4JStopWatch(LoggerFactory.getLogger(loggerName), levelInt, levelInt);
    }

}