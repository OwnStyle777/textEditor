package editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class TextEditor extends JFrame {
    JTextField fieldNameArea = new JTextField("");
     JButton buttonSave = new JButton(new ImageIcon("editor/icons/diskette.png"));
    JButton buttonOpen = new JButton(new ImageIcon("editor/icons/folder.png"));
    JButton buttonPrevious = new JButton(new ImageIcon("editor/icons/left.png"));
    JButton buttonNext = new JButton(new ImageIcon("editor/icons/right-arrow.png"));
    JButton buttonSearch = new JButton(new ImageIcon("editor/icons/search.png"));
    JCheckBox regexCheckBox = new JCheckBox();
    JLabel regexLabel = new JLabel();
    JTextArea textArea = new JTextArea("");
    JMenuBar fileMenuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem saveMenuItem = new JMenuItem("Save");
    JMenuItem openMenuItem = new JMenuItem("Load");
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    JMenu searchMenu = new JMenu("Search");
    JMenuItem searchMenuItem = new JMenuItem("Start search");
    JMenuItem prevSearchMenuItem = new JMenuItem("Previous search");
    JMenuItem nextSearchMenuItem = new JMenuItem("Next match");
    JMenuItem useRegExMenuItem = new JMenuItem("Use regular expressions");
    private JFileChooser fileChooser = new JFileChooser();

    ButtonsFunctionality functionality = new ButtonsFunctionality();
    List<String> foundExpression = new ArrayList<>();
    List<Integer> startIndex = new ArrayList<>();
    private int currentIndex = 0;
    private boolean firstSearch = false;
    private boolean searchNext = false;
    private boolean forward = false;


    public TextEditor() {

        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        textArea.setName("TextArea");
        fieldNameArea.setName("SearchField");
        buttonSave.setName("SaveButton");
        buttonOpen.setName("OpenButton");
        buttonSearch.setName("StartSearchButton");
        buttonPrevious.setName("PreviousMatchButton");
        buttonNext.setName("NextMatchButton");
        regexCheckBox.setName("UseRegExCheckbox");
        fileMenuBar.setName("MenuFile");
        fileMenu.setName("MenuFile");
        saveMenuItem.setName("MenuSave");
        openMenuItem.setName("MenuOpen");
        exitMenuItem.setName("MenuExit");
        searchMenu.setName("MenuSearch");
        searchMenuItem.setName("MenuStartSearch");
        nextSearchMenuItem.setName("MenuNextMatch");
        prevSearchMenuItem.setName("MenuPreviousMatch");
        useRegExMenuItem.setName("MenuUseRegExp");
        fileChooser.setName("FileChooser");
        add(fileChooser);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 25, 25, 20));
        add(mainPanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 5, 0));

        fileMenuBar.add(fileMenu);
        fileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                functionality.saveFunction(fieldNameArea, textArea);
            }
        });
        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser.setName("FileChooser");
                functionality.openFunction(textArea);
            }
        });
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                functionality.exitMenuFunction(exitMenuItem);
            }
        });


        fileMenuBar.add(searchMenu);
        searchMenu.add(searchMenuItem);
        searchMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                functionality.searchButtonFunction(textArea, fieldNameArea, foundExpression, startIndex, regexCheckBox);
                firstSearch = true;
                searchNext = false;
                currentIndex = 0;
            }
        });
        searchMenu.add(prevSearchMenuItem);
        prevSearchMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentIndex = functionality.previousButtonFunction(foundExpression, textArea, currentIndex, startIndex, firstSearch, forward);
                forward = false;
            }
        });
        searchMenu.add(nextSearchMenuItem);
        nextSearchMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentIndex = functionality.nextButtonFunction(foundExpression, textArea, currentIndex, startIndex, searchNext, forward);
                searchNext = true;
                forward = true;
            }
        });
        searchMenu.add(useRegExMenuItem);
        useRegExMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                regexCheckBox.setSelected(true);
            }
        });
        setJMenuBar(fileMenuBar);


        buttonSave.setPreferredSize(new Dimension(25, 25));
        buttonSave.setBorderPainted(false);
        buttonSave.setContentAreaFilled(false);
        buttonsPanel.add(buttonSave);
        buttonsPanel.add(Box.createHorizontalStrut(5));

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                functionality.saveFunction(fieldNameArea, textArea);

            }
        });


        buttonOpen.setPreferredSize(new Dimension(25, 25));
        buttonOpen.setContentAreaFilled(false);
        buttonOpen.setBorderPainted(false);
        buttonsPanel.add(buttonOpen);
        buttonsPanel.add(Box.createHorizontalStrut(10));

        buttonOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser.setName("FileChooser");
                functionality.openFunction(textArea);
            }
        });

        fieldNameArea.setPreferredSize(new Dimension(255, 25));
        buttonsPanel.add(fieldNameArea);
        buttonsPanel.add(Box.createHorizontalStrut(10));

        buttonSearch.setPreferredSize(new Dimension(25, 25));
        buttonSearch.setBorderPainted(false);
        buttonSearch.setContentAreaFilled(false);
        buttonsPanel.add(buttonSearch);
        buttonsPanel.add(Box.createHorizontalStrut(5));
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                functionality.searchButtonFunction(textArea, fieldNameArea, foundExpression, startIndex, regexCheckBox);
                firstSearch = true;
                searchNext = false;
                currentIndex = 0;
            }
        });

        buttonPrevious.setPreferredSize(new Dimension(25, 25));
        buttonPrevious.setContentAreaFilled(false);
        buttonPrevious.setBorderPainted(false);
        buttonsPanel.add(buttonPrevious);
        buttonsPanel.add(Box.createHorizontalStrut(5));
        buttonPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentIndex = functionality.previousButtonFunction(foundExpression, textArea, currentIndex, startIndex, firstSearch, forward);
                forward = false;
            }
        });

        buttonNext.setPreferredSize(new Dimension(25, 25));
        buttonNext.setBorderPainted(false);
        buttonNext.setContentAreaFilled(false);
        buttonsPanel.add(buttonNext);
        buttonsPanel.add(Box.createHorizontalStrut(5));
        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentIndex = functionality.nextButtonFunction(foundExpression, textArea, currentIndex, startIndex, searchNext, forward);
                searchNext = true;
                forward = true;
            }
        });


        buttonsPanel.add(regexCheckBox);

        regexLabel.setPreferredSize(new Dimension(100, 25));
        regexLabel.setText("Use regex");
        buttonsPanel.add(regexLabel);


        JScrollPane mainScrollPane = new JScrollPane(textArea);
        mainScrollPane.setName("ScrollPane");
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        mainPanel.add(mainScrollPane, BorderLayout.CENTER);
        setVisible(true);

    }

}
