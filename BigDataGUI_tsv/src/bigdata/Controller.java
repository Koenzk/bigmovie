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
                    option = "name_basics";
                    substitutions = new int[]{6, 8, 10, 12};
                    result = "name.basics.csv";
                    break;
                }
                case "title.principals.tsv": {
                    pattern = "(tt\\d{7})(\\t)(.*)";
                    substitution = "$3,";
                    header = new String[]{};
                    linesToSkip = 1;
                    option = "title_principals";
                    substitutions = new int[]{3};
                    result = "title.principals.csv";
                    break;
                }
                case "title.crew.tsv": {
                    pattern = "(tt\\d{7})(\\t)(.*)(\\t)(.*)";
                    substitution = "$3, $5";
                    header = new String[]{};
                    linesToSkip = 1;
                    option = "title_crew";
                    substitutions = new int[]{3, 5};
                    result = "title.crew";
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

        public int countLines(String filename) throws IOException {
            InputStream is = new BufferedInputStream(new FileInputStream(filename));
            try {
                byte[] c = new byte[1024];
                int cnt = 0;
                int readChars = 0;
                boolean empty = true;
                while ((readChars = is.read(c)) != -1) {
                    empty = false;
                    for (int i = 0; i < readChars; ++i) {
                        if (c[i] == '\n') {
                            ++cnt;
                        }
                    }
                }
                return (cnt == 0 && !empty) ? 1 : cnt;
            } finally {
                is.close();
            }
        }

        @Override
        protected Void doInBackground() throws IOException {
            int lineNumber = 0;
            int lts = this.linesToSkip;
            pGui.setProgressBar(countLines(pGui.getInputPath()) - lts); //set progress bar length to amount of lines in file minus lts
            switch (option) {
                case "name_basics": {
                    String prevName = "";
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath))) {
                        r = Pattern.compile(pattern);
                        while (br.hasNextLine()) {
                            nextLine = br.nextLine();
                            if (++lineNumber <= lts) continue;

                            matcher = r.matcher(nextLine);
                            StringBuilder txt = new StringBuilder();

                            if (matcher.find()) {
                                prevName = matcher.group(1);
                                for (int i = 6; i < matcher.groupCount(); i += 2) {
                                    if (Objects.equals(matcher.group(i), null)) {
                                    } else {
                                        txt.append(prevName).append(";").append(matcher.group(i)).append("\n");
                                    }
                                }
                                count++;
                                if (txt.toString().trim().length() != 0) {
                                    writer.write(txt.toString().trim());
                                    writer.newLine();
                                }

                                if (lineNumber % 5000 == 0) {
                                    if (txt.toString().trim().length() != 0) {
                                        pGui.addLog(txt.toString().trim()); //prints converted data to log
                                    }
                                    pGui.updateProgressBar(count); //update progress bar
                                    System.gc();
                                }
                            }

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
                // /Users/MustiDarsh/Downloads/title.principals.tsv
                case "title_principals": {
                    String prevName = "";
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath))) {
                        r = Pattern.compile(pattern);
                        while (br.hasNextLine()) {
                            nextLine = br.nextLine();
                            if (++lineNumber <= lts) continue;

                            matcher = r.matcher(nextLine);
                            StringBuilder txt = new StringBuilder();

                            if (matcher.find()) {
                                prevName = matcher.group(1);
                                for (String x : matcher.group(3).split(",")) {
                                    txt.append(prevName).append(";").append(x).append("\n");
                                }
                                count++;
                                if (txt.toString().trim().length() != 0) {
                                    writer.write(txt.toString().trim());
                                    writer.newLine();
                                }

                                if (lineNumber % 5000 == 0) {
                                    if (txt.toString().trim().length() != 0) {
                                        pGui.addLog(txt.toString().trim()); //prints converted data to log
                                    }
                                    pGui.updateProgressBar(count); //update progress bar
                                    System.gc();
                                }
                            }
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
                case "title_crew": {
                    String prevName = "";
                    try (BufferedWriter directors = new BufferedWriter(new FileWriter(outpath + ".directors.csv"));
                         BufferedWriter writers = new BufferedWriter(new FileWriter(outpath + ".writers.csv"))
                    ) {
                        r = Pattern.compile(pattern);
                        while (br.hasNextLine()) {
                            nextLine = br.nextLine();
                            if (++lineNumber <= lts) continue;

                            matcher = r.matcher(nextLine);
                            StringBuilder director = new StringBuilder();
                            StringBuilder writer = new StringBuilder();

                            if (matcher.find()) {
                                prevName = matcher.group(1);
                                for (String x : matcher.group(3).split(",")) {
                                    if (!matcher.group(3).startsWith("\\N"))
                                        director.append(prevName).append(";").append(x).append(";").append("director").append("\n");
                                }
                                for (String y : matcher.group(5).split(",")) {
                                    if (!matcher.group(5).startsWith("\\N"))
                                        writer.append(prevName).append(";").append(y).append(";").append("writer").append("\n");
                                }
                                count++;
                                if (director.toString().trim().length() != 0) {
                                    directors.write(director.toString().trim());
                                    directors.newLine();
                                }

                                if (writer.toString().trim().length() != 0) {
                                    writers.write(writer.toString().trim());
                                    writers.newLine();
                                }

                                if (lineNumber % 5000 == 0) {
                                    if (director.toString().trim().length() != 0) {
                                        pGui.addLog(director.toString().trim()); //prints converted data to log
                                    }
                                    if (writer.toString().trim().length() != 0) {
                                        pGui.addLog(writer.toString().trim()); //prints converted data to log
                                    }
                                    pGui.updateProgressBar(count); //update progress bar
                                    System.gc();
                                }
                            }
                        }
                        directors.flush();
                        directors.close();
                        writers.flush();
                        writers.close();
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
