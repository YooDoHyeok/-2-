package project1;

public class logDTO {
    private String errorCode;
    private String keyValue;
    private String pathFind;
    private String browser;
    private String hour;
    
    public logDTO() {}

    public logDTO(String errorCode, String keyValue, String pathFind, String browser, String hour) {
        this.errorCode = errorCode;
        this.keyValue = keyValue;
        this.pathFind = pathFind;
        this.browser = browser;
        this.hour = hour;
    }

    public String getErrorCode() { return errorCode; }
    public String getKeyValue() { return keyValue; }
    public String getPathFind() { return pathFind; }
    public String getBrowser() { return browser; }
    public String getHour() { return hour; }
}