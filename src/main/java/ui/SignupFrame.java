package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rules.MainController;

public class SignupFrame extends JFrame {
        private MainController mainController;
        JButton login_button, sign_up;
        JLabel usernamelabel, password_label;
        JTextField usernamefield;
        JPasswordField password_field;
        JPanel panel;

        public SignupFrame(MainController mainController) {
                this.mainController = mainController;
                initSwingComponents();
        }

        public SignupFrame() {
                initSwingComponents();
        }

        private void initSwingComponents() {
                setTitle("Sign Up");
                setBounds(100, 100, 1440, 900);
                setResizable(false);
                setLayout(null);

                JLabel background = new JLabel(new ImageIcon("src/main/images/coverApp.jpg"));
                setContentPane(background);

                usernamelabel = new JLabel("Username : ");
                usernamelabel.setBounds(460, 480, 100, 40);

                usernamefield = new JTextField();
                usernamefield.setBounds(580, 480, 260, 40);

                password_label = new JLabel("Password : ");
                password_label.setBounds(460, 530, 100, 40);

                password_field = new JPasswordField();
                password_field.setBounds(580, 530, 260, 40);

                sign_up = new JButton("Sign up");
                sign_up.setBounds(630, 600, 100, 30);
                sign_up.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                                try {
                                        mainController.createUser(usernamefield.getText(), password_field.getText());
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                }
                        }
                });
                add(usernamelabel);
                add(usernamefield);
                add(password_label);
                add(password_field);
                add(sign_up);
                setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

}
