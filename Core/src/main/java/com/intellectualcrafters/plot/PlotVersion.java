package com.intellectualcrafters.plot;

public class PlotVersion {
    public final String versionStr;


    public PlotVersion(String version) {
        this.versionStr = version;
    }

    public static PlotVersion tryParse(String version) {
        return new PlotVersion(version);
    }

    public String versionString() {
        return versionStr;
    }

    @Override
    public String toString() {
        return "PlotSquared-" + versionStr;
    }
}