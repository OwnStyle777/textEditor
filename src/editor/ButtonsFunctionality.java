package editor;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ButtonsFunctionality {

    public void saveFunction(JTextField fieldNameArea, JTextArea textArea) {
        String fileName = fieldNameArea.getText();
        String text = textArea.getText();

        JFileChooser fileChooser = new JFileChooser();

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(textArea.getText());
                fieldNameArea.setText(fileName);
                textArea.setText(text);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    //C:\Users\DELL\Desktop\allLines.txt
    public void openFunction(JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                String content = Files.readString(selectedFile.toPath());
                textArea.setText(content);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error, cannot read this file");

            }
        }
    }

    public void exitMenuFunction(JMenuItem exitMenuItem) {
        System.exit(0);
    }

    public void searchButtonFunction(JTextArea textArea, JTextField textField, List<String> foundText, List<Integer> startIndex, JCheckBox checkBox) {
        foundText.clear();
        startIndex.clear();
        String searchText = textField.getText();
        Pattern pattern;

        if (!checkBox.isSelected()) {
            pattern = Pattern.compile(Pattern.quote(searchText), Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(textArea.getText());

            try {
                while (matcher.find()) {
                    foundText.add(matcher.group());
                    startIndex.add(matcher.start());

                    textArea.setCaretPosition(startIndex.get(0) + matcher.group().length());
                    textArea.select(startIndex.get(0), startIndex.get(0) + matcher.group().length());
                    textArea.grabFocus();

                }

            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }


        } else if (checkBox.isSelected()) {

            pattern = Pattern.compile(searchText, Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(textArea.getText());

            try {
                while (matcher.find()) {
                    foundText.add(matcher.group());
                    startIndex.add(matcher.start());

                    textArea.setCaretPosition(startIndex.get(0) + matcher.group().length());
                    textArea.select(startIndex.get(0), startIndex.get(0) + matcher.group().length());
                    textArea.grabFocus();

                }

            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }


        }

    }

    public int previousButtonFunction(List<String> foundText, JTextArea textArea, int currentIndex, List<Integer> startIndex, boolean firstSearch, boolean forward) {
        if (foundText.isEmpty()) {
            return currentIndex;
        }

        if (!firstSearch) {
            currentIndex = foundText.size() - 1;

        }
        if (forward) {
            currentIndex -= 2;

        }
        if (currentIndex == -1 && firstSearch) {
            currentIndex = foundText.size() - 1;
        }
        if (currentIndex >= 0) {
            String currentWord = foundText.get(currentIndex);
            int currentStartIndex = startIndex.get(currentIndex);

            textArea.setCaretPosition(currentStartIndex + currentWord.length());
            textArea.select(currentStartIndex, currentStartIndex + currentWord.length());
            textArea.grabFocus();

            currentIndex--;

        }

        return currentIndex;
    }

    public int nextButtonFunction(List<String> foundText, JTextArea textArea, int currentIndex, List<Integer> startIndex, boolean firstNext, boolean forward) {

        if (foundText.isEmpty()) {
            return currentIndex;
        }
        if (!firstNext) {
            currentIndex += 1;
        }

        if (!forward && firstNext) {
            if (currentIndex == foundText.size()) {
                currentIndex = 0;
            } else {
                currentIndex += 2;
            }

        } else if (currentIndex == startIndex.size()) {
            currentIndex = 0;
        } else if (currentIndex == -1) {
            currentIndex = 1;
        }

        if (currentIndex == foundText.size()) {
            currentIndex = 0;
        }
        String currentWord = foundText.get(currentIndex);

        if (currentIndex >= 0) {
            int currentStartIndex = startIndex.get(currentIndex);

            textArea.setCaretPosition(currentStartIndex + currentWord.length());
            textArea.select(currentStartIndex, currentStartIndex + currentWord.length());
            textArea.grabFocus();

            currentIndex++;
        }

        return currentIndex;
    }

}


