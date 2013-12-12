/*
 * Copyright 2001-2005 The Apache Software Foundation.
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
package net.winrun4j.maven.plugin;

import org.apache.maven.plugins.annotations.*;

@Mojo(name = "win32", defaultPhase = LifecyclePhase.PACKAGE)
public class Win32Mojo extends Win4JMojoSkeleton {

    @Override
    protected String getRCEditFileName() {
        return "RCEDIT32.exe";
    }

    @Override
    protected String getWinRun4JFileName() {
        return "WinRun4J32.exe";
    }
}
