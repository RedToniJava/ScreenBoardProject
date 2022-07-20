import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Gui {
    private DBaseSet dBaseSet;
    private MainButton mainButton = new MainButton();
    private final JFrame startPage = new JFrame("GUI Project");
    private final Settings settings = new Settings();
    private final JLabel labelYA = new JLabel(new ImageIcon("C:\\Users\\user\\Desktop\\ProjectForYA\\logo.png"));
    private String textLable;
    private final int H = 300;
    private final int L = 800;
    private final int baseIndent = 10;
    private JPanel buttonsPanel = new JPanel();
    private int lableL = 300;
    private int lableH = 30;
    private int defaultButtonL = 100;
    private int defaultButtonH = 30;
    private int bZoneLength = L - baseIndent * 2;
    private int bZoneHeight = H - lableH * 2;
    private ArrayList<JButton> mainButtons = new ArrayList<>();
    private int countOfButtonsL = bZoneLength / (defaultButtonL + baseIndent);
    private int countOfButtonsH = bZoneHeight / (defaultButtonH * 2);


    public void start(){

        try {
            dBaseSet=  DBaseSet.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startPage.setLocation(200, 200);
        startPage.setSize(L, H);


        buttonsPanel.setBounds(baseIndent, lableH + baseIndent, bZoneLength, bZoneHeight);

        for(ButtonsSet buttonS : dBaseSet.getAllButtonsSet()){
                 mainButtons.add(mainButton.createMainButton(buttonS.getName(),buttonS.getDescription(),new ImageIcon(buttonS.getImagePath())));

       }
        int i=0;
        while (i<mainButtons.size()){
          buttonsPanel.add(mainButtons.get(i));
          i++;
        }

        startPage.add(buttonsPanel);
        startPage.add(settings.getLabelYA());
        startPage.add(settings.getSettings());

        startPage.setLayout(null);
        startPage.setVisible(true);

    }

    private class Settings {
        private final JLabel labelYA = new JLabel(new ImageIcon("C:\\Users\\user\\Desktop\\ProjectForYA\\logo.png"));
        private JButton settings = new JButton("Settings");
        private JButton addButton = new JButton("Добавить кнопку");
       // private MainButton mainButton = new MainButton();


        public JButton getSettings() {
            settings.setBounds(650, 0, 90, 30);
            JButton saveAll = new JButton("SaveAll");


            settings.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame settingsArea = new JFrame("Settings");

                    settingsArea.setSize(200, 300);
                    settingsArea.setLocation(100, 100);
                    addButton.setBounds(10, 10, 150, 30);
                    //добавление кнопки
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame addArea = new JFrame("Добавить кнопку");
                            AddImageButton addImageButton= new AddImageButton();
                            JTextField nameFiled = new JTextField();
                            nameFiled.setBounds(10, 10, 120, 30);
                            JButton saveButton = new JButton("Save");
                            saveButton.setBounds(480, 320, 90, 30);
                            JTextArea textArea = new JTextArea("Введите описание предмета");
                            textArea.setBounds(10, 50, 200, 250);
                            addImageButton.getAddImageButton().setBounds(140,10,90,30);

                            addArea.add(addImageButton.getAddImageButton());
                            addArea.add(textArea);
                            addArea.add(saveButton);
                            addArea.add(nameFiled);

                            //сохраняем изменения в кнопках
                            saveButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String pathToImage=addImageButton.getSelectedFile().getAbsolutePath();
                                    //создаём настройки для кнопки
                                    ButtonsSet buttonSet= new ButtonsSet(nameFiled.getText(),pathToImage,textArea.getText());
                                    //добавляем в базу данных
                                    dBaseSet.addProduct(buttonSet);
                                    //добавляем созданную кнопку в панель
                                    mainButtons.add(mainButton.createMainButton(buttonSet.getName(), buttonSet.getDescription(), new ImageIcon(buttonSet.getImagePath())));
                                    addArea.setVisible(false);

                                }
                            });
                            addArea.setSize(600, 400);
                            addArea.setLocation(150, 150);
                            addArea.setLayout(null);
                            addArea.setVisible(true);

                        }
                    });
                    saveAll.setBounds(10, 220, 90, 30);
                    saveAll.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            startPage.setVisible(false);
                            int countB = 0;
                           // int countOfB = 1;
                            int l = 0;
                            int h = 0;
                            for (int i = 0; i < mainButtons.size(); i++) {
                                if (countOfButtonsL != countB && h == 0) {
                                    mainButtons.get(i).setBounds(l, h, defaultButtonL, defaultButtonH);
                                    buttonsPanel.add(mainButtons.get(i));
                                    countOfButtonsL--;
                                    l += baseIndent + defaultButtonL;
                                    countB++;
                                    if (countB == countOfButtonsL) {
                                        h += defaultButtonH * 2;
                                      //  countOfB += 1;
                                    }
                                }

                            }
                            startPage.add(buttonsPanel);
                            startPage.setVisible(true);
                        }
                    });

                    settingsArea.add(saveAll);
                    settingsArea.add(addButton);
                    settingsArea.setLayout(null);
                    settingsArea.setVisible(true);


                }
            });
            return settings;
        }


        public JLabel getLabelYA() {
            labelYA.setBounds(0, 0, lableL, lableH);
            return labelYA;
        }

        public void setLabelYA(int a, int b, int c, int d) {
            labelYA.setBounds(0, 0, 300, 30);
        }
    }

    private class MainButton {


        private Page page = new Page();
        private ImageIcon image = new ImageIcon("C:\\Users\\user\\Desktop\\ProjectForYA\\IMG_20220404_151838.jpg");


        //создаём основные кнопки
        public JButton createMainButton(String nameButton, String textBySubject, ImageIcon icon) {
            JButton mainButton = new JButton(icon);
            mainButton.setBounds(50, 50, 100, 30);

            mainButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    createPage(textBySubject);

                    System.out.println("2");

                }
            });


            return mainButton;
        }

        //нужно для создания  новых страниц
        private void createPage(String textOfSubject) {

            page.createPage(labelYA, textOfSubject, image);

        }

    }

    public class Page {
        private JButton backButton = new JButton("Back");
        private JButton imageButton = new JButton();


        //страница основной кнопки
        public void createPage(JLabel lable, String text, ImageIcon image) {
            JFrame page = new JFrame();
            page.setLocation(220, 200);
            page.setSize(800, 300);
            backButton.setBounds(690, 10, 80, 20);
            imageButton.setIcon(image);
            imageButton.setBounds(500, 50, 180, 200);
            lable.setBounds(0, 0, lableL, lableH);

            JTextPane textLable = new JTextPane();
            textLable.setText(text);
            textLable.setBounds(20, 50, 200, 200);

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    page.setVisible(false);


                }
            });

            page.add(backButton);
            page.add(imageButton);
            page.add(textLable);
            page.add(lable);


            page.setLayout(null);
            page.setVisible(true);


        }
    }


}
