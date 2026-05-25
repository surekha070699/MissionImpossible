package org.example.constants;

public enum FileType {
    CSV(".csv"),
    JSON(".json"),
    TXT(".txt"),
    XLS(".xls"),
    XLSM(".xlsm"),
    XLSX(".xlsx"),
    XML(".xml"),
    YAML(".yml");

    private String name;

    FileType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
