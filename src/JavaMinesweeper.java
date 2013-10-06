import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.List;


public class JavaMinesweeper {
    public static void noMines(MineButton[] btns, int location, int width, int height) {
        if (!btns[location].isMine && btns[location].NumofMines > 0) {
            btns[location].bt.setText(Integer.toString(btns[location].NumofMines));
            btns[location].bt.setEnabled(false);
            return;
        } else if (!btns[location].isMine && btns[location].NumofMines == 0) {
            System.out.println(" " + 1 + " " + location);
            btns[location].bt.setEnabled(false);
            if (0 <= location && location <= (width - 1)) {
                if (location > 0) {
                    noMines(btns, location - 1, width, height);
                    noMines(btns, location + width - 1, width, height);
                }
                if (location < width - 1) {
                    noMines(btns, location + 1, width, height);
                    noMines(btns, location + width + 1, width, height);
                }
                noMines(btns, location + width, width, height);
            } else if (location % width == 0 && location != 0 && location != width * (height - 1)) {
                noMines(btns, location - width, width, height);
                noMines(btns, location - width + 1, width, height);
                noMines(btns, location + 1, width, height);
                noMines(btns, location + width, width, height);
                noMines(btns, location + width + 1, width, height);
            } else if ((location + 1) % width == 0 && location != width - 1 && location != width * height - 1) {
                noMines(btns, location - width, width, height);
                noMines(btns, location - width - 1, width, height);
                noMines(btns, location - 1, width, height);
                noMines(btns, location + width, width, height);
                noMines(btns, location + width - 1, width, height);
            } else if (width * (height - 1) <= location && location <= width * height - 1) {
                if (location > width * (height - 1)) {
                    noMines(btns, location - 1, width, height);
                    noMines(btns, location - width - 1, width, height);
                }
                if (location < width * height - 1) {
                    noMines(btns, location + 1, width, height);
                    noMines(btns, location - width + 1, width, height);
                }
                noMines(btns, location - width, width, height);
            } else {
                noMines(btns, location - width - 1, width, height);
                noMines(btns, location - width, width, height);
                noMines(btns, location - width + 1, width, height);
                noMines(btns, location - 1, width, height);
                noMines(btns, location + 1, width, height);
                noMines(btns, location + width - 1, width, height);
                noMines(btns, location + width, width, height);
                noMines(btns, location + width + 1, width, height);
            }
        }
    }

    public static void main(String[] args) {
        //the setting window
        final JFrame setting = new JFrame("Setting");
        setting.setLayout(new FlowLayout());
        JLabel widthlb = new JLabel("Width(Larger than 3): ");
        final TextField widthtxt = new TextField();
        JLabel heightlb = new JLabel("Height (Larger than 3): ");
        final TextField heighttxt = new TextField();
        JLabel numlb = new JLabel("Number of mines: ");
        final TextField numtxt = new TextField();
        JButton start = new JButton("   Start!!   ");
        setting.add(widthlb);
        setting.add(widthtxt);
        setting.add(heightlb);
        setting.add(heighttxt);
        setting.add(numlb);
        setting.add(numtxt);
        setting.add(start);

        //get the width and height of the screen, and make the window stay at the center of the screen by using the two piece of data
        final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        setting.setLocation((screenWidth - 200) / 2, (screenHeight - 320) / 2);
        setting.setSize(250, 140);
        setting.setResizable(false);
        setting.setVisible(true);

        //when click start, show the game window
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int width = Integer.parseInt(widthtxt.getText());
                final int height = Integer.parseInt(heighttxt.getText());
                int num = Integer.parseInt(numtxt.getText());
                Random rd = new Random();
                final List mines = new ArrayList();

                if (width < 3 || height < 3) {
                    System.out.println("Illegal Input!!!");
                } else {
                    setting.dispose();        //close the setting window

                    final MineButton[] btnArr = new MineButton[width * height];
                    for (int i = 0; i < width * height; i++) {
                        btnArr[i] = new MineButton();          //initiate the space of the class array
                        btnArr[i].bt = new JButton();       //initiate the ram of the button
                    }
                    while (mines.size() < num) {
                        int tmp = rd.nextInt(width * height);
                        if (!mines.contains(tmp)) {
                            mines.add(tmp);
                            btnArr[tmp].isMine = true;
                        }
                    }

                    //calculate the number of mines around each button
                    for (int i = 0; i < width * height; i++) {
                        if (0 <= i && i <= (width - 1)) {
                            if (btnArr[i].isMine) {
                                if (i > 0) {
                                    btnArr[i - 1].NumofMines++;
                                    btnArr[i + width - 1].NumofMines++;
                                }
                                if (i < width - 1) {
                                    btnArr[i + 1].NumofMines++;
                                    btnArr[i + width + 1].NumofMines++;
                                }
                                btnArr[i + width].NumofMines++;
                            }
                        } else if (i % width == 0 && i != 0 && i != width * (height - 1)) {
                            if (btnArr[i].isMine) {
                                btnArr[i - width].NumofMines++;
                                btnArr[i - width + 1].NumofMines++;
                                btnArr[i + 1].NumofMines++;
                                btnArr[i + width].NumofMines++;
                                btnArr[i + width + 1].NumofMines++;
                            }
                        } else if ((i + 1) % width == 0 && i != width - 1 && i != width * height - 1) {
                            if (btnArr[i].isMine) {
                                btnArr[i - width].NumofMines++;
                                btnArr[i - width - 1].NumofMines++;
                                btnArr[i - 1].NumofMines++;
                                btnArr[i + width].NumofMines++;
                                btnArr[i + width - 1].NumofMines++;
                            }
                        } else if (width * (height - 1) <= i && i <= width * height - 1) {
                            if (btnArr[i].isMine) {
                                if (i > width * (height - 1)) {
                                    btnArr[i - 1].NumofMines++;
                                    btnArr[i - width - 1].NumofMines++;
                                }
                                if (i < width * height - 1) {
                                    btnArr[i + 1].NumofMines++;
                                    btnArr[i - width + 1].NumofMines++;
                                }
                                btnArr[i - width].NumofMines++;
                            }
                        } else {
                            if (btnArr[i].isMine) {
                                btnArr[i - width - 1].NumofMines++;
                                btnArr[i - width].NumofMines++;
                                btnArr[i - width + 1].NumofMines++;
                                btnArr[i - 1].NumofMines++;
                                btnArr[i + 1].NumofMines++;
                                btnArr[i + width - 1].NumofMines++;
                                btnArr[i + width].NumofMines++;
                                btnArr[i + width + 1].NumofMines++;
                            }
                        }
                    }

                    final JFrame minespace = new JFrame("MineSpace");
                    minespace.setLayout(new GridLayout(height, width));

                    final JFrame finish = new JFrame();
                    finish.setLayout(new FlowLayout());
                    JLabel restart = new JLabel("Quit??");
                    JButton restartY = new JButton("Yes");
                    restartY.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(1);
                        }
                    });
                    JButton restartN = new JButton("    Restart!!   ");
                    restartN.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            minespace.dispose();
                            finish.dispose();
                            setting.setVisible(true);
                        }
                    });
                    finish.add(restart);
                    finish.add(restartY);
                    finish.add(restartN);
                    finish.setSize(150, 95);
                    finish.setLocation((screenWidth - 150) / 2, (screenHeight - 295) / 2);
                    finish.setResizable(false);

                    for (int i = 0; i < width * height; i++) {
                        final int looptmp = i;
                        if (btnArr[i].isMine) {
                            btnArr[i].bt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    for (int j = 0; j < mines.size(); j++) {
                                        btnArr[Integer.parseInt(mines.get(j).toString())].bt.setText("⚙");
                                        btnArr[Integer.parseInt(mines.get(j).toString())].bt.setEnabled(false);
                                    }
                                    finish.setTitle("You LOST!!");
                                    finish.setVisible(true);
                                }
                            });
                        } else if (btnArr[i].NumofMines > 0) {
                            btnArr[i].bt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    btnArr[looptmp].bt.setText(Integer.toString(btnArr[looptmp].NumofMines));
                                    btnArr[looptmp].bt.setEnabled(false);
                                }
                            });
                        } else if (btnArr[i].NumofMines == 0) {
                            btnArr[i].bt.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    noMines(btnArr, looptmp, width, height);
                                }
                            });
                        }
                    }

                    //add the button array to the game window
                    for (int i = 0; i < height * width; i++) {
                        btnArr[i].bt.setSize(20, 20);
                        minespace.add(btnArr[i].bt);
                    }
                    minespace.setResizable(false);
                    minespace.setSize(width * 30, height * 30);
                    minespace.setLocation((screenWidth - width * 30) / 2, (screenHeight - height * 30 - 220) / 2);
                    minespace.setVisible(true);
                }
            }
        });
    }
}
