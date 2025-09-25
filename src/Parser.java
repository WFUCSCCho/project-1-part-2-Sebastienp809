/*******************************************************
 * @file: Parser.java
 * @description: Reads commands from an input file and
 *               executes operations on a BST of Movie
 *               objects. Commands supported include
 *               insert, search, remove, and print.
 *               Output is written to result.txt.
 * @author: Sebastien Pierre
 * @date: September 25, 2025
 *******************************************************/

import java.io.*;
import java.util.*;

public class Parser {

    // BST now stores Movie objects (your dataset type)
    private BST<Movie> mybst = new BST<>();

    public Parser(String filename) throws FileNotFoundException {
        process(new File(filename));
    }

    // Read the command file line-by-line
    public void process(File input) throws FileNotFoundException {
        Scanner sc = new Scanner(input);
        while (sc.hasNextLine()) {
            String raw = sc.nextLine();
            if (raw == null) continue;
            String line = raw.trim();
            if (line.isEmpty()) continue;                // skip blank lines
            if (line.startsWith("#")) continue;          // allow comments

            // Expect comma-separated commands (e.g., insert,Titanic,1997,7.8,1100000)
            String[] parts = splitAndTrim(line);

            operate_BST(parts);
        }
        sc.close();
    }

    // Split by commas and trim each token
    private String[] splitAndTrim(String line) {
        String[] toks = line.split(",", -1);            // keep empty fields if any
        for (int i = 0; i < toks.length; i++) {
            toks[i] = toks[i].trim();
        }
        return toks;
    }

    // Determine the command and act on the BST
    public void operate_BST(String[] command) {
        if (command.length == 0 || command[0].isEmpty()) {
            writeToFile("Invalid Command", "./result.txt");
            return;
        }

        String op = command[0].toLowerCase();

        switch (op) {
            case "insert":
                // insert,title,year,rating,votes
                if (command.length == 5) {
                    String title = command[1];
                    Integer year = parseInt(command[2]);
                    Double rating = parseDouble(command[3]);
                    Integer votes = parseInt(command[4]);
                    if (title != null && !title.isEmpty()
                            && year != null && rating != null && votes != null) {
                        Movie m = new Movie(title, year, rating, votes);
                        mybst.insert(m);
                        writeToFile("insert " + m.toString(), "./result.txt");
                        break;
                    }
                }
                writeToFile("Invalid Command", "./result.txt");
                break;

            case "search":
                // search,title,year
                if (command.length == 3) {
                    String title = command[1];
                    Integer year = parseInt(command[2]);
                    if (title != null && !title.isEmpty() && year != null) {
                        Movie probe = new Movie(title, year, 0.0, 0);
                        Movie found = mybst.search(probe);   // BST.search returns element or null
                        if (found != null) {
                            writeToFile("found " + found.toString(), "./result.txt");
                        } else {
                            writeToFile("search failed", "./result.txt");
                        }
                        break;
                    }
                }
                writeToFile("Invalid Command", "./result.txt");
                break;

            case "remove":
                // remove,title,year
                if (command.length == 3) {
                    String title = command[1];
                    Integer year = parseInt(command[2]);
                    if (title != null && !title.isEmpty() && year != null) {
                        Movie probe = new Movie(title, year, 0.0, 0);
                        Movie removed = mybst.remove(probe);
                        if (removed != null) {
                            writeToFile("removed " + removed.toString(), "./result.txt");
                        } else {
                            writeToFile("remove failed", "./result.txt");
                        }
                        break;
                    }
                }
                writeToFile("Invalid Command", "./result.txt");
                break;

            case "print":
                // just print the whole tree (in-order)
                if (command.length == 1) {
                    String out = mybst.inorder();          // uses Movie.toString() for each node
                    writeToFile(out, "./result.txt");
                    break;
                }
                writeToFile("Invalid Command", "./result.txt");
                break;

            default:
                writeToFile("Invalid Command", "./result.txt");
        }
    }

    // Append one line (or multiple if content has \n) to result file
    public void writeToFile(String content, String filePath) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(filePath, true)); // append mode
            out.println(content);
        } catch (IOException e) {
            System.err.println("Error writing: " + e.getMessage());
        } finally {
            if (out != null) out.close();
        }
    }

    // tiny helpers
    private Integer parseInt(String s) {
        try { return Integer.valueOf(s); }
        catch (Exception e) { return null; }
    }

    private Double parseDouble(String s) {
        try { return Double.valueOf(s); }
        catch (Exception e) { return null; }
    }
}
