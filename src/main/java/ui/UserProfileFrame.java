package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.Artist;
import core.Song;
import core.User;
import rules.MainController;

public class UserProfileFrame extends JFrame {
        private MainController mainController;
        private User loggedUser;
        private JTabbedPane tabbedPane;
        private JPanel Mainpanel, searchPanel, recommendedSongPane, recommendedArtistPane, allSongPane, allArtistPane, likedSongPane, likedArtistPane;
        private JScrollPane rSongScrollPane, rArtistScrollPane, lSongScrollPane, lArtistScrollPane, aSongScrollPane, aArtistScrollPane, rSearchSongScrollPanel;
        private JList rSongList, rArtistList, lSongList, lArtistList, aSongList, aArtistList, rSearchSongList;
        private JButton logOut, rLikeSongButton, rRefreshSongsButton, rLikeArtistButton, rRefreshArtistsButton;
        private JButton lRefreshSongsButton, lRefreshArtistsButton;
        private JButton aLikeSongButton, aLikeArtistButton, searchMusicButton;
        private JComboBox comboBox;
        private JTextField textField;
        private JLabel searchFor;

        DefaultListModel rSongListModel, rArtistListModel, lSongListModel, lArtistListModel, aSongListModel, aArtistListModel, rSearchSongModel;
        private JLabel name;

        public UserProfileFrame(User user, MainController mainController) {
                this.mainController = mainController;
                this.loggedUser = user;
                initSwingComponents();
        }

        public UserProfileFrame() {
                initSwingComponents();
        }

        @SuppressWarnings({ "rawtypes", "unchecked", "unchecked", "serial", "unchecked" }) private void displayRecommendedSongs(JPanel panel) {
                rSongListModel = new DefaultListModel();
                rSongList = new JList(rSongListModel);
                rSongList.setCellRenderer(new SongListCellRenderer(rSongList));
                rSongScrollPane = new JScrollPane(rSongList);
                rRefreshSongsButton = new JButton("Refresh");
                rLikeSongButton = new JButton("Like");
                rLikeSongButton.setPreferredSize(new Dimension(80, 40));
                rRefreshSongsButton.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                try {
                                        JOptionPane.showMessageDialog(UserProfileFrame.this, "Entries Refreshed!", "Operation Successful",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        refreshRecommendedSongList();
                                } catch (SQLException | IOException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                }
                        }
                });
                rLikeSongButton.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                try {
                                        if (rSongList.getSelectedValue() != null) {
                                                if (mainController.isLiked(loggedUser, (Song) rSongList.getSelectedValue())) JOptionPane
                                                        .showMessageDialog(UserProfileFrame.this, "Entry previously liked!", "Message",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                else {
                                                        JOptionPane.showMessageDialog(UserProfileFrame.this, "Entry liked!", "Operation Successful",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                }
                                                likeSong(rSongList.getSelectedValue());
                                        } else {
                                                JOptionPane.showMessageDialog(UserProfileFrame.this, "Select an entry, then press like!", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                } catch (SQLException e1) {
                                        e1.printStackTrace();
                                }
                        }
                });
                panel.add(rSongScrollPane, BorderLayout.CENTER);
                panel.add(rLikeSongButton, BorderLayout.NORTH);
                panel.add(rRefreshSongsButton, BorderLayout.AFTER_LAST_LINE);
                try {
                        refreshRecommendedSongList();
                } catch (SQLException | IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }
        }
        private void displaySearchPanel(JPanel panel) {
                /*setTitle("AutoCompleteComboBox");
                String[] countries = new String[] {"india", "australia", "newzealand", "england", "germany",
                        "france", "ireland", "southafrica", "bangladesh", "holland", "america"};
                comboBox = new AutoCompleteComboBox(countries);
                add(comboBox, BorderLayout.NORTH);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setVisible(true);*/
                //panel.add(comboBox);
                rSearchSongModel = new DefaultListModel();
                rSearchSongList = new JList(rSearchSongModel);
                rSearchSongList.setCellRenderer(new SongListCellRenderer(rSearchSongList));
                rSearchSongScrollPanel = new JScrollPane(rSearchSongList);
                textField = new JTextField(16);
                textField.setToolTipText("SEARCH");
                textField.setText("SEARCH FOR...");
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                textField.addFocusListener(new FocusListener() {
                        public void focusGained(FocusEvent e) {
                                textField.setText("");
                        }

                        public void focusLost(FocusEvent e) {
                                // nothing
                        }
                });
                textField.setBounds(100,100,500,200);
                searchMusicButton = new JButton("Search!");
                searchMusicButton.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                try {
                                        /*JOptionPane.showMessageDialog(UserProfileFrame.this, "Entries Refreshed!", "Operation Successful",
                                                JOptionPane.INFORMATION_MESSAGE);*/
                                        refreshSearchedSongList();
                                } catch (SQLException | IOException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                }
                        }
                });

                panel.add(rSearchSongScrollPanel, BorderLayout.CENTER);
                panel.add(textField, BorderLayout.NORTH);
                panel.add(searchMusicButton, BorderLayout.AFTER_LAST_LINE);
                try {
                        refreshSearchedSongList();
                } catch (SQLException | IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }

        }

        private void displayRecommendedArtists(JPanel panel) {
                rArtistScrollPane = new JScrollPane();
                rArtistListModel = new DefaultListModel();
                rArtistList = new JList(rArtistListModel);

                rArtistList.setCellRenderer(new ArtistListCellRenderer(rArtistList));
                rArtistScrollPane.setViewportView(rArtistList);

                rRefreshArtistsButton = new JButton("Refresh");
                rLikeArtistButton = new JButton("Like");
                rRefreshArtistsButton.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                try {
                                        JOptionPane.showMessageDialog(UserProfileFrame.this, "Entries Refreshed!", "Operation Successful",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        refreshRecommendedArtistList();
                                } catch (SQLException | IOException e1) {
                                        e1.printStackTrace();
                                }
                        }
                });
                rLikeArtistButton.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                try {
                                        if (rArtistList.getSelectedValue() != null) {
                                                if (mainController.isLiked(loggedUser, (Artist) rArtistList.getSelectedValue())) JOptionPane
                                                        .showMessageDialog(UserProfileFrame.this, "Entry previously liked!", "Message",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                else {
                                                        JOptionPane.showMessageDialog(UserProfileFrame.this, "Entry liked!", "Operation Successful",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                }
                                                likeArtist(rArtistList.getSelectedValue());
                                        } else {
                                                JOptionPane.showMessageDialog(UserProfileFrame.this, "Select an entry, then press like!", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                } catch (SQLException e1) {
                                        e1.printStackTrace();
                                }
                        }
                });
                panel.add(rArtistScrollPane, BorderLayout.CENTER);
                panel.add(rLikeArtistButton, BorderLayout.NORTH);
                panel.add(rRefreshArtistsButton, BorderLayout.AFTER_LAST_LINE);
                try {
                        refreshRecommendedArtistList();
                } catch (SQLException | IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }
        }

        private void displayAllSongs(JPanel panel) {
                aSongListModel = new DefaultListModel();
                aSongList = new JList(aSongListModel);
                aSongList.setCellRenderer(new SongListCellRenderer(aSongList));
                aSongScrollPane = new JScrollPane(aSongList);
                List<Song> songs;
                try {
                        songs = mainController.getAllSongList();
                        aSongList.setListData(songs.toArray());
                } catch (IOException | SQLException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                }

                aLikeSongButton = new JButton("Like");
                aLikeSongButton.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                try {
                                        if (aSongList.getSelectedValue() != null) {
                                                if (mainController.isLiked(loggedUser, (Song) aSongList.getSelectedValue())) JOptionPane
                                                        .showMessageDialog(UserProfileFrame.this, "Entry previously liked!", "Message",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                else {
                                                        JOptionPane.showMessageDialog(UserProfileFrame.this, "Entry liked!", "Operation Successful",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                }
                                                likeSong(aSongList.getSelectedValue());
                                        } else {
                                                JOptionPane.showMessageDialog(UserProfileFrame.this, "Select an entry, then press like!", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                } catch (SQLException e1) {
                                        e1.printStackTrace();
                                }
                        }
                });
                panel.add(aSongScrollPane, BorderLayout.CENTER);
                panel.add(aLikeSongButton, BorderLayout.NORTH);
        }

        private void displayAllArtists(JPanel panel) {
                aArtistScrollPane = new JScrollPane();
                aArtistListModel = new DefaultListModel();
                aArtistList = new JList(aArtistListModel);
                aArtistList.setCellRenderer(new ArtistListCellRenderer(aArtistList));
                aArtistScrollPane.setViewportView(aArtistList);
                List<Artist> artists;
                try {
                        artists = mainController.getAllArtistList();
                        aArtistList.setListData(artists.toArray());
                } catch (SQLException | IOException e2) {
                        e2.printStackTrace();
                }

                aLikeArtistButton = new JButton("Like");
                aLikeArtistButton.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                try {
                                        if (aArtistList.getSelectedValue() != null) {
                                                if (mainController.isLiked(loggedUser, (Artist) aArtistList.getSelectedValue())) JOptionPane
                                                        .showMessageDialog(UserProfileFrame.this, "Entry previously liked!", "Message",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                else {
                                                        JOptionPane.showMessageDialog(UserProfileFrame.this, "Entry liked!", "Operation Successful",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                }
                                                likeArtist(aArtistList.getSelectedValue());
                                        } else {
                                                JOptionPane.showMessageDialog(UserProfileFrame.this, "Select an entry, then press like!", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                        }
                                } catch (SQLException e1) {
                                        e1.printStackTrace();
                                }
                        }
                });
                panel.add(aArtistScrollPane, BorderLayout.CENTER);
                panel.add(aLikeArtistButton, BorderLayout.NORTH);
        }

        private void displayLikedSongs(JPanel panel) {
                lSongListModel = new DefaultListModel();
                lSongList = new JList(lSongListModel);
                lSongList.setCellRenderer(new SongListCellRenderer(lSongList));
                lSongScrollPane = new JScrollPane(lSongList);
                lRefreshSongsButton = new JButton("Refresh");
                lRefreshSongsButton.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                try {
                                        JOptionPane.showMessageDialog(UserProfileFrame.this, "Entries Refreshed!", "Operation Successful",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        refreshLikedSongList();
                                } catch (SQLException | IOException e1) {
                                        e1.printStackTrace();
                                }
                        }
                });
                panel.add(lSongScrollPane, BorderLayout.CENTER);
                panel.add(lRefreshSongsButton, BorderLayout.AFTER_LAST_LINE);
                try {
                        refreshLikedSongList();
                } catch (SQLException | IOException e1) {
                        e1.printStackTrace();
                }
        }

        private void displayLikedArtists(JPanel panel) {
                lArtistScrollPane = new JScrollPane();
                lArtistListModel = new DefaultListModel();
                lArtistList = new JList(lArtistListModel);
                lArtistList.setCellRenderer(new ArtistListCellRenderer(lArtistList));
                lArtistScrollPane.setViewportView(lArtistList);

                lRefreshArtistsButton = new JButton("Refresh");
                lRefreshArtistsButton.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                try {
                                        JOptionPane.showMessageDialog(UserProfileFrame.this, "Entries Refreshed!", "Operation Successful",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        refreshLikedArtistList();
                                } catch (SQLException | IOException e1) {
                                        e1.printStackTrace();
                                }
                        }
                });
                panel.add(lArtistScrollPane, BorderLayout.CENTER);
                panel.add(lRefreshArtistsButton, BorderLayout.AFTER_LAST_LINE);
                try {
                        refreshLikedArtistList();
                } catch (SQLException | IOException e1) {
                        e1.printStackTrace();
                }
        }

        private void initSwingComponents() {
                setTitle(
                        "                                                                                                                                                                                            "
                                + "Logged User: " + loggedUser.getUserName().toUpperCase());
                setBounds(20, 20, 1440, 900);
                setLayout(null);
                setResizable(false);

                Mainpanel = new JPanel();
                Mainpanel.setBounds(0, 0, getWidth(), getHeight());
                Mainpanel.setLayout(null);
                Mainpanel.setBackground(Color.decode("#000000"));
                Color tabColor = Color.decode("#E8F5E9");
                Color tabBackgroundColor = Color.decode("#E8F5E9");
                logOut = new JButton("Logout");
                logOut.setBounds(1250, 25, 100, 30);
                logOut.addActionListener(new ActionListener() {
                        @Override public void actionPerformed(ActionEvent e) {
                                mainController.openLogin();
                                setVisible(false);
                        }
                });

                tabbedPane = new JTabbedPane();
                tabbedPane.setBounds(20, 20, getWidth() - 50, getHeight() - 70);
                tabbedPane.setBackground(tabColor);

                searchPanel = new JPanel();
                searchPanel.setLayout(new BorderLayout());
                searchPanel.setBackground(tabBackgroundColor);
                displaySearchPanel(searchPanel);


                recommendedSongPane = new JPanel();
                recommendedSongPane.setLayout(new BorderLayout());
                recommendedSongPane.setBackground(tabBackgroundColor);
                displayRecommendedSongs(recommendedSongPane);

                recommendedArtistPane = new JPanel();
                recommendedArtistPane.setLayout(new BorderLayout());
                recommendedArtistPane.setBackground(tabBackgroundColor);
                displayRecommendedArtists(recommendedArtistPane);

                allSongPane = new JPanel();
                allSongPane.setLayout(new BorderLayout());
                allSongPane.setBackground(tabBackgroundColor);
                displayAllSongs(allSongPane);

                allArtistPane = new JPanel();
                allArtistPane.setLayout(new BorderLayout());
                allArtistPane.setBackground(tabBackgroundColor);
                displayAllArtists(allArtistPane);

                likedSongPane = new JPanel();
                likedSongPane.setLayout(new BorderLayout());
                likedSongPane.setBackground(tabBackgroundColor);
                displayLikedSongs(likedSongPane);

                likedArtistPane = new JPanel();
                likedArtistPane.setLayout(new BorderLayout());
                likedArtistPane.setBackground(tabBackgroundColor);
                // likedArtistPane.setBackground(Color.decode("#000000"));
                displayLikedArtists(likedArtistPane);

                ImageIcon icon = new ImageIcon("src/main/images/a.png");
                ImageIcon icon2 = new ImageIcon("src/main/images/aa.png");
                tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
                tabbedPane.addTab("Search for music", icon, searchPanel, "Search for music based on song or artist");
                tabbedPane.addTab("Recommended Songs", icon, recommendedSongPane, "Default : Shows Top Songs,Else Recommended Songs based on User Preferences");
                tabbedPane.addTab("Recommended Artists", icon2, recommendedArtistPane,
                        "Default : Shows Top Artists,Else Recommended Artists based on User Preferences");
                tabbedPane.addTab("All Songs", icon, allSongPane, "Shows All Songs in Knowledge Base");
                tabbedPane.addTab("All Artists", icon2, allArtistPane, "Shows All Artists in Knowledge Base");
                tabbedPane.addTab("Liked Songs", icon, likedSongPane, "Shows All Liked Songs (Facts Base)");
                tabbedPane.addTab("Liked Artists", icon2, likedArtistPane, "Shows All Liked Artists (Facts Base)");
                ChangeListener changeListener = new ChangeListener() {
                        @Override public void stateChanged(ChangeEvent changeEvent) {
                                // JTabbedPane source = (JTabbedPane) changeEvent.getSource();
                                try {
                                        refresh();
                                } catch (SQLException | IOException e) {
                                        System.out.println("Error during Refresh");
                                }
                        }
                };
                tabbedPane.addChangeListener(changeListener);
                Mainpanel.add(logOut);
                Mainpanel.add(tabbedPane);
                add(Mainpanel);
                setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        void refreshRecommendedSongList() throws FileNotFoundException, SQLException, IOException {
                List<Song> songs = mainController.getRecommendedSongList(loggedUser);
                rSongList.setListData(songs.toArray());
        }

        void refreshSearchedSongList() throws FileNotFoundException, SQLException, IOException {
                List<Song> songs = mainController.getSearchedSongs(textField.getText());
                if(songs.isEmpty()){
                        songs.add(new Song("EMPTY", "EMPTY", "EMPTY"));
                }
                rSearchSongList.setListData(songs.toArray());
        }

        void refreshRecommendedArtistList() throws SQLException, FileNotFoundException, IOException {
                List<Artist> artists = mainController.getRecommendedArtistList(loggedUser);
                rArtistList.setListData(artists.toArray());
        }

        void refreshLikedSongList() throws FileNotFoundException, SQLException, IOException {
                List<Song> songs = mainController.getLikedSongList(loggedUser);
                lSongList.setListData(songs.toArray());
        }

        void refreshLikedArtistList() throws SQLException, FileNotFoundException, IOException {
                List<Artist> artists = mainController.getLikedArtistList(loggedUser);
                lArtistList.setListData(artists.toArray());
        }

        public void likeSong(Object object) throws SQLException {
                mainController.updateLikeSong((Song) object, loggedUser);
        }

        public void likeArtist(Object object) throws SQLException {
                mainController.updateLikeArtist((Artist) object, loggedUser);
        }

        public void refresh() throws FileNotFoundException, SQLException, IOException {
                refreshSearchedSongList();
                refreshLikedArtistList();
                refreshLikedSongList();
                refreshRecommendedArtistList();
                refreshRecommendedSongList();
        }

        public static void main(String args[]) {
                new UserProfileFrame();
        }
}
