package sample;

import java.io.File;
import java.io.IOException;

public class DirectoryHelper {
    public void createFolder() throws IOException {
        File file = new File("DownloadedFiles");
        boolean bool = file.mkdir();
        if (bool)
            System.out.println("Stworzono folder");
        else
            System.out.println("Nie udalo sie stworzyc lub folder juz istnieje");
        if (!file.exists())
            return;
    }

    public String getPathdirectory() throws IOException {

        String path = new File(".").getCanonicalPath();
        System.out.println(path + "/");
        return path + "/";
    }

    public String getDownloadFilesDirectory() throws IOException {
        return getPathdirectory() + "DownloadedFiles/";
    }

    public void setUpStuff() throws IOException {
        MainSettings.setUser("radek");
        MainSettings.setPassword("radek2");
        MainSettings.setLocalPath(getDownloadFilesDirectory());
        MainSettings.setRemotePath("/home/radek/Wideo/");
    }
}
