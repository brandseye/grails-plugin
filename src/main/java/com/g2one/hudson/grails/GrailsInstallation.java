package com.g2one.hudson.grails;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.EnvVars;
import hudson.model.EnvironmentSpecific;
import hudson.model.TaskListener;
import hudson.model.Node;
import hudson.slaves.NodeSpecific;
import hudson.tools.ToolProperty;
import hudson.tools.ToolInstallation;

import java.io.File;
import java.io.IOException;
import java.util.List;


public final class GrailsInstallation extends ToolInstallation implements EnvironmentSpecific<GrailsInstallation>, NodeSpecific<GrailsInstallation> {

    public GrailsInstallation(String name, String home) {
        super(name, home);
    }

    @DataBoundConstructor
    public GrailsInstallation(String name, String home, List<? extends ToolProperty<?>> properties) {
        super(name, home, properties);
    }

    public String getGrailsHome() {
        return getHome();
    }

    public File getExecutable() {
        String execName;
        if (File.separatorChar == '\\')
            execName = "grails.bat";
        else
            execName = "grails";

        return new File(getGrailsHome(), "bin/" + execName);
    }

    public boolean getExists() {
        return getExecutable().exists();
    }

    public GrailsInstallation forNode(Node node, TaskListener log) throws IOException, InterruptedException {
        return new GrailsInstallation(getName(), translateFor(node, log), getProperties().toList());
    }

    public GrailsInstallation forEnvironment(EnvVars environment) {
        return new GrailsInstallation(getName(), environment.expand(getHome()), getProperties().toList());
    }
}
