/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigdata;

import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * @author HP
 */
public class Controller {
    private ParserGUI pGui;

    public Controller(ParserGUI pGui) {
        this.pGui = pGui;
        this.pGui.setVisible(true);
        this.pGui.setActions(new BrowseAction(), new OutputAction(), new ParseAction(), new CloseAction());
    }

    //button action for selecting file to parse
    class BrowseAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser input = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            input.setDialogTitle("Select a tsv file");
            input.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.tsv files", "tsv");
            input.addChoosableFileFilter(filter);
            int result = input.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = input.getSelectedFile();
                pGui.updateInputPath(selectedFile.getPath());
            }
        }
    }

    //button action for selecting directory to export .csv file into
    class OutputAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser output = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            output.setDialogTitle("Choose a directory to save your file in:");
            output.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//            output.setAcceptAllFileFilterUsed(false);
            int result = output.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                pGui.updateOutputPath(output.getCurrentDirectory().getPath() + "/");
            }
        }
    }

    //button action to begin parsing file
    class ParseAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            pGui.addLog("---------------------------------------");
            File f = new File(pGui.getInputPath());
            FileInputStream fr;
            Scanner br;
            int linesToSkip = 0;
            String pattern;
            String[] header;
            String substitution;
            String result;
            String option = "test";
            String outpath;
            int[] substitutions;


            try {
                fr = new FileInputStream(f);
                br = new Scanner(fr);
                //Path path = Paths.get(pGui.getInputPath());
                //Stream<String> stream = Files.lines(path);
            } catch (IOException err) {
                pGui.addLog(err.toString());
                return;
            }

            switch (f.getName()) {
                case "name.basics.tsv": {
                    pattern = "(nm\\d*)(\\s+)(.*)(\\t+)((tt\\d{7})?(\\,)?(tt\\d{7})?(\\,)?(tt\\d{7})?(\\,)?(tt\\d{7})?)?|(\\\\N)";
                    substitution = "$6, $8, $10, $12";
                    header = new String[]{};
                    linesToSkip = 1;
                    option = "tabbed";
                    substitutions = new int[]{6, 8, 10, 12};
                    result = "name.basics.csv";
                    break;
                }
                default: {
                    pattern = "";
                    substitution = "";
                    header = new String[]{};
                    pGui.addLog("tsv not found.");
                    result = null;
                    option = null;
                    substitutions = null;
                    linesToSkip = 0;
                    break;
                }
            }
            outpath = pGui.getOutputPath() + result;
            new Parser(pattern, substitution, header, outpath, br, fr, linesToSkip, option, substitutions).execute();
        }
    }

    //de hele parser klasse staat hieronder en is ge-extend naar swingworker zodat de parse-operatie in een achtergroundthread gebeurt zodat
    //de UI niet tijdens de operatie vast loopt. Er wordt in de vorige switch-case gekeken wat voor lijst is geselecteerd en daarvan wordt de filepath (outpath)
    //doorgegeven aan de Parser.
    class Parser extends SwingWorker<Void, Void> {
        private String pattern;
        private String substitution;
        private String outpath;
        private Scanner br;
        private FileInputStream fr;
        private String nextLine;
        private String result;
        private Matcher matcher;
        private Pattern r;
        private int count;
        private int linesToSkip;
        private String option;
        private int[] subs;

        public Parser(String pattern, String substitution, String[] header, String outpath, Scanner br, FileInputStream fr, int linesToSkip, String option, int[] subs) {
            this.pattern = pattern;
            this.substitution = substitution;
            this.outpath = outpath;
            this.br = br;
            this.fr = fr;
            this.linesToSkip = linesToSkip;
            this.option = option;
            count = 0;
            this.subs = subs;
        }

//        public int countLines(String filename) throws IOException {
//            InputStream is = new BufferedInputStream(new FileInputStream(filename));
//            try {
//                byte[] c = new byte[1024];
//                int cnt = 0;
//                int readChars = 0;
//                boolean empty = true;
//                while ((readChars = is.read(c)) != -1) {
//                    empty = false;
//                    for (int i = 0; i < readChars; ++i) {
//                        if (c[i] == '\n') {
//                            ++cnt;
//                        }
//                    }
//                }
//                return (cnt == 0 && !empty) ? 1 : cnt;
//            } finally {
//                is.close();
//            }
//        }

        @Override
        protected Void doInBackground() throws IOException {
            int lineNumber = 0;
            int lts = this.linesToSkip;
//        pGui.setProgressBar(countLines(pGui.getInputPath()) - lts); //set progress bar length to amount of lines in file minus lts

            switch (option) {

                case "tabbed": {
                    String prevName = "";
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath))) {
                        r = Pattern.compile(pattern);
//                    int i = 0;
                        while (br.hasNextLine()) {
                            nextLine = br.nextLine();
//                        i++;
//                        System.out.println(i);
                            if (++lineNumber <= lts) continue;

                            System.out.println(nextLine);
                            matcher = r.matcher(nextLine);
                            List<String> rows = new ArrayList<>();

//                        if (matcher.matches()){
//                            matcher.reset();
                            if (matcher.find()) {
//                                System.out.println("I break while finding");
                                prevName = matcher.group(1);
                                for (int i = 6; i < matcher.groupCount(); i += 2) {
                                    rows.add(prevName);
                                    if (Objects.equals(matcher.group(i), null)) {
//                                        System.out.println("I break while equalling");

                                        rows.add(null);
                                    } else {
//                                        System.out.println("I break while else");

                                        rows.add("\t" + matcher.group(i));
                                    }
                                }
                            }
//                        }

                            StringBuilder lineString = new StringBuilder();

                            for (int j = 0; j < rows.size(); j += 2) {
                                lineString.append(rows.get(j)).append(";").append(rows.get(j + 1)).append("\n");
                            }


                            result = matcher.replaceAll(substitution);
//                        System.out.println(lineString);
                            if (lineString.length() != 0) {
//                            System.out.println("I break while writing");
                                count++; //update current line count
                                writer.write(lineString.toString().trim());
                                pGui.addLog(lineString.toString().trim()); //prints converted data to log
                                pGui.updateProgressBar(count); //update progress bar
                                writer.newLine();
//                            writer.flush();
//                            System.gc();
                            }
                            writer.flush();
                            System.gc();
//                        else continue;
                        }
                        writer.flush();
                        writer.close();
                        br.close();
                        System.gc();
                    } catch (IOException e) {
                        pGui.addLog(e.toString());
                    }
                    break;
                }
//            default: {
//                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath)))
//                {
//                    r = Pattern.compile(pattern);
//                    while ((nextLine = br.readLine()) != null) {
//                        if (++lineNumber <= lts) continue;
//
//                        //if (!nextLine.startsWith("\"")) {
////                    System.out.println(nextLine);
//                        matcher = r.matcher(nextLine);
//
//                        result = matcher.replaceAll(substitution);
//                        System.out.println(result);
//                        if (result.length() != 0) {
//                            count++;
//                            writer.write(result);
//                            writer.newLine();
//                            pGui.addLog(result);
//                            pGui.updateProgressBar(count);
//
//                        }
//                        //} else continue;
//                    }
//                    writer.close();
//                }
//                catch (IOException e)
//                {
//                    pGui.addLog(e.toString());
//                }
//                break;
//            }
//
            }
            return null;
        }

        @Override
        protected void done() {
            pGui.addLog("Conversion finished!");
            pGui.addLog("--------------------------------------");
        }
    }

    //button action to close the program
    class CloseAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            pGui.dispose();
        }
    }
}
