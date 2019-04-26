import javax.swing.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ACMain {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        List<String> Output = new ArrayList<>();
        List<String> InputArray = new ArrayList<>();
        int SPACES = 25;
        String Path = "";
        String FirstName = "";
        String SecondName = "";
        JFileChooser Chooser = new JFileChooser();
        Chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Choosing path with JFileChooser
        if (Chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            Path = Chooser.getSelectedFile().getPath();
        }
        // Entering data here
        System.out.println("Enter archives data");
        String Input = sc.nextLine();
        // Parsing input string and changing path if it's entered again
        try {
            for (String elem : Input.split(" ")) {
                InputArray.add(elem);
            }
            if (InputArray.size() == 3) {
                Path = InputArray.get(0);
                FirstName = InputArray.get(1);
                SecondName = InputArray.get(2);
            } else {
                FirstName = InputArray.get(0);
                SecondName = InputArray.get(1);
            }
        } catch (Exception e) {
            System.out.println("Error. Wrong data.");
            //e.printStackTrace();
        }
        try {
            // Strings to be printed
            List<FileElement> Checked = new ArrayList<>();
            // Files that were not checked inside first loop
            ArchiveState FirstArchive = new ArchiveState(Path, FirstName);
            ArchiveState SecondArchive = new ArchiveState(Path, SecondName);    // Objects with archive data
            FirstArchive.FillState();
            SecondArchive.FillState();

            Output.add("----------------------------+----------------------------");
            // Loops to check all of the files inside first archive
                for (FileElement file : FirstArchive.getFiles()) {
                    String Spaces = "";
                    // Making line of spaces for alignment
                    for (int i = 0; i < SPACES - file.getName().length(); i++) {
                        Spaces += " ";
                    }
                    // Calling method WasChanged to check if file have different data
                    if (file.WasChanged(SecondArchive) != -1) {
                        Output.add("* " + file.getName() + Spaces + " | * "
                                + SecondArchive.getFiles().get(file.WasChanged(SecondArchive)).getName());
                        Checked.add(SecondArchive.getFiles().get(file.WasChanged(SecondArchive)));
                        // Calling method WasRenamed to check if file have similar memory and different names
                    } else if (file.WasRenamed(SecondArchive) != -1) {
                        Output.add("? " + file.getName() + Spaces + " | ? "
                                + SecondArchive.getFiles().get(file.WasRenamed(SecondArchive)).getName());
                        Checked.add(SecondArchive.getFiles().get(file.WasRenamed(SecondArchive)));
                        // Calling method hasSimilar to check if both files are similar
                    } else if (file.HasSimilar(SecondArchive) != -1) {
                        Checked.add(SecondArchive.getFiles().get(file.HasSimilar(SecondArchive)));
                    } else {
                        // Check if file of first archive doesn't exist in second
                        // and show it as 'deleted'
                        Output.add("- " + file.getName() + Spaces + " |");
                    }
                }
            // Loop to find files of second archive that were not checked
            // and add them as 'added'
            for (FileElement file : SecondArchive.getFiles()) {
                if (!Checked.contains(file)) {
                    Output.add("                            | + " + file.getName());
                }
            }

            for (String str : Output) {
                System.out.println(str);
            }

            FileWriter Writer = new FileWriter("ArchiveComparatorOutput.txt", false);
            Writer.write("'-' - deleted");
            Writer.write("\r\n" + "'+' - added");
            Writer.write("\r\n" + "'*' - changed");
            Writer.write("\r\n" + "'?' - renamed(probably)");
            for (String str : Output) {
                Writer.write("\r\n" + str);
                Writer.flush();
            }
        } catch (Exception e) {
            System.out.println("Error. Wrong data.");
            e.printStackTrace();
        }
    }
}
