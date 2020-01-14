package sample;

public class MainSettings {
    static String user;
    static String password;
    static String localPath;
    static String remotePath;

    static String getUser() { return user; }
    public static void setUser(String u) { user = u; }

    public static String getPassword() { return password; }
    public static void setPassword(String p) {password = p; }

    public static String getLocalPath() {return localPath;}
    public static void setLocalPath(String l) {localPath = l;}

    static String getRemotePath() {return remotePath;}
    public static void setRemotePath(String r) {remotePath = r;}
}
