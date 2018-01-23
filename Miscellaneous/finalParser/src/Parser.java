//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Parser
//{
//    // Class variables
//    private String pattern;
//    private String substitution;
//    private String[] header;
//    private String IMDBpath;
//    private String choice;
//    private BufferedReader br;
//    private FileReader fr;
//    private String nextLine;
//    private String result;
//    private Matcher matcher;
//    private Pattern r;
//    private int linesToSkip;
//    private String option;
//    // Constructor
//    public Parser(String pattern, String substitution, String[] header, String IMDBpath, String choice, BufferedReader br, FileReader fr, int linesToSkip, String option)
//    {
//        this.pattern = pattern;
//        this.substitution = substitution;
//        this.header = header;
//        this.IMDBpath = IMDBpath;
//        this.choice = choice;
//        this.br = br;
//        this.fr = fr;
//        this.linesToSkip = linesToSkip;
//        this.option = option;
//    }
//
//    // Method to actually parse the plaintext file into .CSV
//    public void parse() throws IOException
//    {
//        int lineNumber = 0;
//        count = 0;
//        int lts = this.linesToSkip;
//
//        switch(option){
//
//            case "tabbed":
//                String prevName = "";
//                try (BufferedWriter writer = new BufferedWriter(new FileWriter(IMDBpath + "CSV/" + choice.toLowerCase() + ".csv")))
//                {
//                    r = Pattern.compile(pattern);
//                    while ((nextLine = br.readLine()) != null) {
//                        if (++lineNumber <= lts) continue;
//
//                        matcher = r.matcher(nextLine);
//                        List<String> rows = new ArrayList<>();
//
//                        if (matcher.find()) {
//                            if (Objects.equals("", matcher.group(1))) {
//                                rows.add(prevName);
//                                rows.add("\t" + matcher.group(3));
//                            } else {
//                                rows.add(matcher.group(1));
//                                rows.add("\t" + matcher.group(3));
//                                prevName = matcher.group(1);
//                            }
//                        }
//                        //if (!nextLine.startsWith("\"")) {
////                    System.out.println(nextLine);
//                        StringBuilder lineString = new StringBuilder();
//                        for (String row : rows) {
//                            if (row != null) {
//                                row = row.trim();
//                            }
////                System.out.println(lineString);
//                            assert row != null;
//                            lineString.append(row.trim()).append(";\t");
//                        }
//
//                        result = matcher.replaceAll(substitution);
//                        System.out.println(result);
//                        if (lineString.length() != 0) {
//                            count++;
//                            writer.write(lineString.toString());
//                            pGui.addLog(result);
//                            pGui.updateProgressBar(count);
//                            writer.newLine();
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
//            default:
//                try (BufferedWriter writer = new BufferedWriter(new FileWriter(IMDBpath + "CSV/" + choice.toLowerCase() + ".csv")))
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
//    }
//    }
//
//}
