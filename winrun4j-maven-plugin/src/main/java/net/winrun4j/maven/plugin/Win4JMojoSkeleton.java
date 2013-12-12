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

import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;

public abstract class Win4JMojoSkeleton extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.finalName}", property = "fileName")
    protected String fileName;
    @Parameter(property = "icon")
    protected File icon;
    @Parameter(property = "iniFile", required = true)
    protected File iniFile;
    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    protected File outputDirectory;
    @Parameter(defaultValue = "${project.build.directory}/${project.build.finalName}.jar", property = "targetJar", readonly = true)
    protected File targetJar;

    final public void execute() throws MojoExecutionException {
        File rcedit = new File(outputDirectory, "RCEDIT.exe");
        File target = new File(outputDirectory, fileName + ".exe");
        try {
            FileUtils.copyURLToFile(getClass().getResource("/" + getWinRun4JFileName()), target);
            FileUtils.copyURLToFile(getClass().getResource("/" + getRCEditFileName()), rcedit);
            execute(rcedit, target, "C", target.getName());
            execute(rcedit, target, "N", iniFile.getAbsolutePath());
            if (icon != null) {
                execute(rcedit, target, "I", icon.getAbsolutePath());
            }
            execute(rcedit, target, "J", targetJar.getName());
        } catch (Exception ex) {
            throw new MojoExecutionException(ex.getMessage(), ex);
        } finally {
            rcedit.delete();
        }
    }

    private void execute(File rcedit, File target, String commutator, String... optional) throws CommandLineException {
        Commandline cl = new Commandline(rcedit.getName());
        cl.setWorkingDirectory(outputDirectory);
        cl.addArguments(new String[]{"/" + commutator, target.getName()});
        cl.addArguments(optional);
        getLog().info(cl.toString());
        CommandLineUtils.executeCommandLine(cl, null, null);
    }

    protected abstract String getRCEditFileName();

    protected abstract String getWinRun4JFileName();
}
