/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigdata;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            
            int result = output.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                pGui.updateOutputPath(output.getSelectedFile().getPath());
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
            String pattern;
            String[] header;
            String substitution;
            String result;
            String outpath;
            
            try {
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                //Path path = Paths.get(pGui.getInputPath());
                //Stream<String> stream = Files.lines(path);
                pGui.setProgressBar(1000);
            } catch (IOException err) {
                pGui.addLog(err.toString());
                return;
            }
            
            switch (f.getName()) {
                case "movies.list": {
                    pattern = "";
                    substitution = "";
                    header = new String[]{};
                    result = "\\movies.csv";
                    break;
                }
                case "running-times.list": {
                    pattern = "(?:\")(.*)(?:\") \\((\\d{4}|[?]{4})\\W(?:.*\\{|.*\\))?(.*\\))?(?:.*\\t|.*:)((\\d)?(\\d))(?:.*)";
                    substitution = "\\1, \\2, \\3, \\4";
                    header = new String[]{};
                    result = "\\running-times.csv";
                    break;
                }
                default: {
                    pattern = "";
                    substitution = "";
                    header = new String[]{};
                    pGui.addLog("List not found.");
                    result = null;
                    break;
                }
            }
            outpath = pGui.getOutputPath() + result;
            new Parser(pattern, substitution, header, outpath, br, fr).execute();
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
        
        
        public Parser(String pattern, String substitution, String[] header, String outpath, BufferedReader br, FileReader fr) {
            this.pattern = pattern;
            this. substitution = substitution;
            this.header = header;
            this.outpath = outpath;
            this.br = br;
            this.fr = fr;
            count = 0;
            current = "";
        }
        
        protected void process() {
            pGui.updateProgressBar(count); //werkt niet?
        }
        
        @Override
        protected Void doInBackground() throws IOException{
            //parser logic
            count = 0;
            pGui.updateProgressBar(count); //update progress bar
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outpath)))
            {
                r = Pattern.compile(pattern);
                while ((nextLine = br.readLine()) != null)
                {
                    matcher = r.matcher(nextLine);

                    result = matcher.replaceAll(substitution);

                    if(result.length() != 0){
                        count++;
                        writer.write(result);
                        pGui.addLog(result); //laat lijn in kwestie in de log zien
                        writer.newLine();
                    }
                }
                writer.close();
                System.out.println(count);
            }
            catch (IOException e)
            {
                pGui.addLog(e.toString());
            }
                return null;
            }
        
        @Override
        protected void done() {
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
