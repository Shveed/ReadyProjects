public class FileElement {
    private String Name;
    private long Memory;


    public FileElement(String name, long memory){
        this.Name = name;
        this.Memory = memory;
    }

    public String getName() {
        return Name;
    }

    public long getMemory() {
        return Memory;
    }

    // Compares two files. Returns index of file in first archive
    // if it has similar one or -1 of it hasn't.
    public int HasSimilar(ArchiveState newArchive){
        int count = 0;
        for(FileElement elem : newArchive.getFiles()){
            if(elem.Memory == this.Memory && elem.Name.equals(this.Name)){
                return count;
            }
            count++;
        }
        return -1;
    }
    // Compares two files. Returns index of file inside
    // first archive if second file isn't same memory
    // but has same name and -1 if no files correlate
    // with this condition
    public int WasChanged(ArchiveState newArchive){
        int count = 0;
        for(FileElement elem : newArchive.getFiles()){
            if(elem.getName().equals(this.getName()) && (elem.getMemory() != this.getMemory() )){
                return count;
            }
            count++;
        }
        return -1;
    }
    // Compares two files. Returns index of file int second
    // archive if it's name was probably changed and -1 if it wasn't
    public int WasRenamed(ArchiveState newArchive) {
        int count = 0;
        for (FileElement elem : newArchive.getFiles()) {
            if (elem.getMemory() == this.getMemory() && !elem.getName().equals(this.getName())) {
                return count;
            }
            count++;
        }
        return -1;
    }


}
