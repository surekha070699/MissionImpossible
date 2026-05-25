package org.example.constants;

public enum ResourceName {
    DRIVER_CONFIGURATION("DriverConfiguration");

    private final String fileName;

    ResourceName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return this.fileName;
    }
}
