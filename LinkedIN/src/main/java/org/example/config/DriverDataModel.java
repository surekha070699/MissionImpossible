package org.example.config;

import lombok.Getter;

import org.example.Helpers.FileReader;
import org.example.constants.ResourceName;

@Getter
public class DriverDataModel {

    private static DriverDataModel driverDataModel = (DriverDataModel) FileReader.loadDriverFile(ResourceName.DRIVER_CONFIGURATION, DriverDataModel.class);

    protected String[] chromeCapabilities;
    protected String[] fireFoxCapabilities;
    protected String defaultBrowser;

    private DriverDataModel() {}

    public static DriverDataModel getInstance() {
        return driverDataModel;
    }

}