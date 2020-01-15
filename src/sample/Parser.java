package sample;

import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {


    public String checkRealFileExtension(String filename, String filepath) throws IOException {

        File file = new File(filepath + filename);
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        String mimeType = URLConnection.guessContentTypeFromStream(is);
        System.out.println("File type is - " + mimeType);
        return mimeType;
    }
    public void getListOfKeyValuePairs(String filepath, String filename) throws IOException {


        //<Keymap> List<Keymap
        List<Keymap> list = new ArrayList<>();
        Wini ini = new Wini(new File(filepath + filename));
        System.out.println("Ilosc grup w pliku to" + ini.size());

        for (String sectionName : ini.keySet()) {
            Profile.Section section = ini.get(sectionName);
            for (String optionKey : section.keySet()) {
                System.out.println(sectionName);
                System.out.println("\t" +" Key = "  +optionKey+"="+section.get(optionKey));
                //Keymap keymap = new Keymap(optionKey, section.get(optionKey));
                // list.add(keymap);
                // list.add((Keymap) new sample.Keymap(optionKey, section.get(optionKey)));

            }

        }

        // return list;
    }

    public  FileStructure getListOfKeyPairValues(String filepath, String filename) throws IOException {
      //  String filetype = checkRealFileExtension(filepath,filename);
        FileStructure singleFile = new FileStructure();
        Wini ini = new Wini(new File(filepath + filename));

        for (String sectionName : ini.keySet()) {
            singleFile.setGroupname(sectionName);
            Profile.Section section = ini.get(sectionName);
            for (String optionKey : section.keySet()) {
          //      System.out.println("option Key " +optionKey);
          //     System.out.println("key set" + section.get(optionKey));
                singleFile.addToKeyValuePairs(new Keymap(optionKey, section.get(optionKey)));
                singleFile.addFilename(filename);
            }
        }

        return singleFile;
    }


    public void writeToFile(String filename, String group, String key, String value) throws IOException {

        Wini ini = new Wini(new File(filename));
        ini.put(group, key, value);
//        ini.put("nowa", "klucz", "wartosc");
        Wini.Section section;
        section = ini.get(group);
        section.put(key, value);
        section.put("grupa", "wartosc");
        System.out.println("Group - " + group + " | key - " + key + " |value - " + value);
        ini.store();
    }

    public String getGroupName(String filename) throws IOException {
        Wini ini = new Wini(new File(filename));
        String group="";
        for (String sectionName : ini.keySet())
        {
            group = sectionName;
        }
        return group;

    }


}
