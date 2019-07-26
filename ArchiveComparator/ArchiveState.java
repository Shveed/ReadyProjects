import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ArchiveState {
    private List<FileElement> Files;
    private String Path;
    private String Name;


    public ArchiveState(String path, String name){
        this.Path = path;
        this.Name = name;
    }
    // Scanning files of current archive
    public void FillState() throws Exception{
        File file = new File(this.Path, this.Name);
        if(!file.exists() || !file.canRead()){
            System.out.println("Can't read file =(");
            return;
        }
        try{ // Checking if archive type is .zip
            if(this.Name.substring(this.Name.length() - 4).equals(".zip")) {
                Files = new ArrayList<>();
                ZipFile zip = new ZipFile(file);
                Enumeration entries = zip.entries();
                // Reading all files inside archive
                while (entries.hasMoreElements()) {
                    ZipEntry currFile = (ZipEntry) entries.nextElement();
                    FileElement fileObj = new FileElement(currFile.getName(), currFile.getSize());
                    Files.add(fileObj);
                }
                zip.close();
            } // Checking if archive type is .jar
            else if(this.Name.substring(this.Name.length() - 4).equals(".jar")){
                Files = new ArrayList<>();
                JarFile jar = new JarFile(file);
                Enumeration entries = jar.entries();
                // Reading all files inside archive
                while (entries.hasMoreElements()) {
                    JarEntry currFile = (JarEntry) entries.nextElement();
                    FileElement fileObj = new FileElement(currFile.getName(), currFile.getSize());
                    Files.add(fileObj);
                }
                jar.close();
            }
        }
        catch(IOException e){
            System.out.println("Error. Wrong data.");
            System.out.println(this.Name);
            //e.printStackTrace();
        }
    }

    public List<FileElement> getFiles() {
        if(this.Files == null){
            return null;
        }
        else{
            return this.Files;
        }
    }
}


