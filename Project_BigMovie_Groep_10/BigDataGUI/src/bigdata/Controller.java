/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigdata;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
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
            input.setDialogTitle("Select a list file");
            input.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".list files", "list");
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
        public void actionPerformed(ActionEvent e){
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
            FileReader fr;
            BufferedReader br;
            int linesToSkip = 0;
            String pattern;
            String[] header;
            String substitution;
            String result;
            String option = "test";
            String outpath;
            int[] substitutions;

            
            try {
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                //Path path = Paths.get(pGui.getInputPath());
                //Stream<String> stream = Files.lines(path);
            } catch (IOException err) {
                pGui.addLog(err.toString());
                return;
            }
            
            switch (f.getName()) {
                case "countries.list": {
                    pattern = "\"?(.*?)\"?\\s\\((.{4,7}|\\?\\?\\?\\?|\\d{4}\\/.*)\\)\\s*(\\((.*)\\))?\\s*(\\{([^\\{}]*)\\})?\\s(\\{(SUSPENDED)\\})?\\s*(.*)";
                    substitution = "$1; $2; $6; $9";
                    header = new String[]{};
                    linesToSkip = 14;
                    substitutions = null;
                    result = "countries.csv";
                    break;
                }
                case "movies.list": {
                    pattern = "\\s?([^\"].*[^\"])\\s(?:\\((\\d{4}|\\?{4})(?:\\/([IVXCM]+))?\\))\\s(\\((.{1,2})\\))?\\s*(\\{\\{(.*?)\\}\\})?\\s*(\\d{4}|\\?{4})";
                    substitution = "$1; $2; $8";
                    header = new String[]{};
                    linesToSkip = 14;
                    substitutions = null;
                    result = "movies.csv";
                    break;
                }
                case "series.list": {
                    pattern = "\\\"(.*?)\\\"\\s\\((.*?)\\)\\s(\\{([^\\{].*?[^\\}])\\})?\\s*(\\{(.*?)\\})?\\s*(.{4,9})";
                    substitution = "$1; $2; $4; $7";
                    header = new String[]{};
                    linesToSkip = 15;
                    substitutions = null;
                    result = "series.csv";
                    break;
                }
                case "actors.list": {
                    pattern = "(.*?)(\\t{1,3})(.+?(?=\\())(\\s+)?(\\((.+?(?=\\)))\\))(\\s)(\\{(.+)\\})?( +)?(\\((\\w{1})\\))?( +)?(\\((.*)\\))?( +)?(\\[(.+)\\])?( +)?(\\<(.*)\\>)?";
                    substitution = "$1; $3; $6; $9; $12; $15; $18";
                    header = new String[]{};
                    linesToSkip = 239;
                    option = "tabbed";
                    substitutions = new int[]{3, 6, 9, 12, 15, 18};
                    result = "actors.csv";
                    break;
                }
                case "actresses.list": {
                    pattern = "(.*?)(\\t{1,3})(.+?(?=\\())(\\s+)?(\\((.+?(?=\\)))\\))(\\s)(\\{(.+)\\})?( +)?(\\((\\w{1})\\))?( +)?(\\((.*)\\))?( +)?(\\[(.+)\\])?( +)?(\\<(.*)\\>)?";
                    substitution = "$1; $3; $6; $9; $12; $15; $18";
                    header = new String[]{};
                    linesToSkip = 241;
                    option = "tabbed";
                    substitutions = new int[]{3, 6, 9, 12, 15, 18};
                    result = "actresses.csv";
                    break;
                }
                case "directors.list": {
                    pattern = "(.*?)(\\t{1,3})(.+?(?=\\())(\\s+)?(\\((.+?(?=\\)))\\))(\\s)(\\{(.+)\\})?( +)?(\\((\\w{1})\\))?( +)?(\\((.*)\\))?( +)?(\\[(.+)\\])?( +)?(\\<(.*)\\>)?";
                    substitution = "$1; $3; $6; $9; $11; $15";
                    header = new String[]{};
                    linesToSkip = 235;
                    option = "tabbed";
                    substitutions = new int[]{3, 6, 9, 11, 15};
                    result = "directors.csv";
                    break;
                }
                case "producers.list": {
                    pattern = "(.*?)(\\t{1,3})(.+?(?=\\())(\\s+)?(\\((.+?(?=\\)))\\))(\\s)(\\{(.+)\\})?( +)?(\\((\\w{1})\\))?( +)?(\\((.*)\\))?( +)?(\\[(.+)\\])?( +)?(\\<(.*)\\>)?";
                    substitution = "$1; $3; $6; $9; $11; $15";
                    header = new String[]{};
                    linesToSkip = 219;
                    option = "tabbed";
                    substitutions = new int[]{3, 6, 9, 11, 15};
                    result = "producers.csv";
                    break;
                }
                case "ratings.list": {
                    pattern = "(.{20}) ([0-9]\\.[0-9])  (.+) (?:\\((\\d{4}|\\?{4})(?:\\/([IVXCM]+))?\\)) ?(\\{(.+)\\}?)?";
                    substitution = "$2; $3; $4; $7";
                    header = new String[]{};
                    linesToSkip = 28;
                    substitutions = null;
                    result = "ratings.csv";
                    break;
                }
                case "running-times.list": {
                    pattern = "(?:\")(.*)(?:\") \\((\\d{4}|[?]{4})\\W(?:.*\\{|.*\\))?(.*\\))?(?:.*\\t|.*:)((\\d)?(\\d))(?:.*)";
                    substitution = "$1, $2, $3, $4";
                    header = new String[]{};
                    linesToSkip = 14;
                    substitutions = null;
                    result = "running-times.csv";
                    break;
                }
                default: {
                    pattern = "";
                    substitution = "";
                    header = new String[]{};
                    pGui.addLog("List not found.");
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
        private String[] header;
        private String outpath;
        private BufferedReader br;
        private FileReader fr;
        private String nextLine;
        private String result;
        private Matcher matcher;
        private Pattern r;
        private int count;
        private String current;
        private int linesToSkip;
        private String option;
        private int[] subs;

        public Parser(String pattern, String substitution, String[] header, String outpath, BufferedReader br, FileReader fr, int linesToSkip, String option, int[] subs) {
            this.pattern = pattern;
            this.substitution = substitution;
            this.header = header;
            this.outpath = outpath;
            this.br = br;
            this.fr = fr;
            this.linesToSkip = linesToSkip;
            this.option = option;
            count = 0;
            current = "";
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
        protected Void doInBackground() throws IOException{
        int lineNumber = 0;
        int lts = this.linesToSkip;
        pGui.setProgressBar(countLines(pGui.getInputPath()) - lts); //set progress bar length to amount of lines in file minus lts

        switch(option){

            case "tabbed": {
                String prevName = "";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath)))
                {
                    r = Pattern.compile(pattern);
                    while ((nextLine = br.readLine()) != null) {
                        if (++lineNumber <= lts) continue;

                        matcher = r.matcher(nextLine);
                        List<String> rows = new ArrayList<>();

                        if (matcher.find()) {
                            if (Objects.equals("", matcher.group(1))) {
                                rows.add(prevName);
                                for (int i : subs) {
                                    rows.add("\t" + matcher.group(i));
                                }
                            } else {
                                rows.add(matcher.group(1));
                                for (int i : subs) {
                                    rows.add("\t" + matcher.group(i));
                                }
                                prevName = matcher.group(1);
                            }
                        }
                        //if (!nextLine.startsWith("\"")) {
//                    System.out.println(nextLine);
                        StringBuilder lineString = new StringBuilder();
                        for (String row : rows) {
                            if (row != null) {
                                row = row.trim();
                            }
//                System.out.println(lineString);
                            assert row != null;
                            lineString.append(row.trim()).append(";\t");
                        }

                        result = matcher.replaceAll(substitution);
                        System.out.println(result);
                        if (lineString.length() != 0) {
                            count++; //update current line count
                            writer.write(lineString.toString());
                            pGui.addLog(result); //prints converted data to log
                            pGui.updateProgressBar(count); //update progress bar
                            writer.newLine();

                        }
                        //} else continue;
                    }
                    writer.close();
                }
                catch (IOException e)
                {
                    pGui.addLog(e.toString());
                }
                break;
            }
            default: {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath)))
                {
                    r = Pattern.compile(pattern);
                    while ((nextLine = br.readLine()) != null) {
                        if (++lineNumber <= lts) continue;

                        //if (!nextLine.startsWith("\"")) {
//                    System.out.println(nextLine);
                        matcher = r.matcher(nextLine);

                        result = matcher.replaceAll(substitution);
                        System.out.println(result);
                        if (result.length() != 0) {
                            count++;
                            writer.write(result);
                            writer.newLine();
                            pGui.addLog(result);
                            pGui.updateProgressBar(count);

                        }
                        //} else continue;
                    }
                    writer.close();
                }
                catch (IOException e)
                {
                    pGui.addLog(e.toString());
                } 
                break;
            }
                
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
