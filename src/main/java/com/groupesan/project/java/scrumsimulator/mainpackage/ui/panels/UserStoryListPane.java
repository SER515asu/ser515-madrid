package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.User;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.UserStoryWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class UserStoryListPane extends JFrame implements BaseComponent {
    public UserStoryListPane() {
        this.init();
    }

    private List<UserStoryWidget> widgets = new ArrayList<>();

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("User Story list");
        setSize(400, 300);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        // demo/testing widgets
        //        UserStory aUserStory = UserStoryFactory.getInstance().createNewUserStory("foo",
        // "bar", 2);
        //        UserStory aUserStory2 =
        //                UserStoryFactory.getInstance().createNewUserStory("foo2", "bar2", 4);
        //        widgets.add(new UserStoryWidget(aUserStory));
        //        widgets.add(new UserStoryWidget(aUserStory2));

//        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
//            widgets.add(new UserStoryWidget(userStory));
//        }

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridBagLayout());
        int i = 0;

        // Fetch all user stories and display them with a delete button
        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
            widgets.add(new UserStoryWidget(userStory));
            UserStoryWidget widget = new UserStoryWidget(userStory);

            // Create a new JPanel to hold both the widget and the delete button
            JPanel rowPanel = new JPanel(new GridBagLayout());

            // Create delete button for each user story
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Remove the user story from the store
                    UserStoryStore.getInstance().removeUserStory(userStory);
                    // Remove the entire row (widget + delete button) from the subPanel
                    subPanel.remove(rowPanel);
                    // Refresh the UI
                    subPanel.revalidate();
                    subPanel.repaint();
                }
            });

            // Add the user story widget and the delete button to the row panel
            rowPanel.add(
                    widget,
                    new CustomConstraints(
                            0,
                            0,
                            GridBagConstraints.WEST,
                            1.0,
                            0.1,
                            GridBagConstraints.HORIZONTAL));

            rowPanel.add(
                    deleteButton,
                    new CustomConstraints(
                            1,
                            0,
                            GridBagConstraints.WEST,
                            0.1,
                            0.1,
                            GridBagConstraints.NONE));

            // Add the row panel (widget + delete button) to the subPanel
            subPanel.add(
                    rowPanel,
                    new CustomConstraints(
                            0,
                            i,
                            GridBagConstraints.WEST,
                            1.0,
                            0.1,
                            GridBagConstraints.HORIZONTAL));

            i++; // Increment row position
        }
        myJpanel.add(
                new JScrollPane(subPanel),
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, 1.0, 0.8, GridBagConstraints.HORIZONTAL));

        JButton newSprintButton = new JButton("New User Story");
        newSprintButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NewUserStoryForm form = new NewUserStoryForm();
                        form.setVisible(true);

                        form.addWindowListener(
                                new java.awt.event.WindowAdapter() {
                                    public void windowClosed(
                                            java.awt.event.WindowEvent windowEvent) {
                                        UserStory userStory = form.getUserStoryObject();
                                        UserStoryStore.getInstance().addUserStory(userStory);
                                        widgets.add(new UserStoryWidget(userStory));
                                        int idx = widgets.size() - 1;
                                        subPanel.add(
                                                widgets.get(idx),
                                                new CustomConstraints(
                                                        0,
                                                        idx,
                                                        GridBagConstraints.WEST,
                                                        1.0,
                                                        0.1,
                                                        GridBagConstraints.HORIZONTAL));
                                    }
                                });
                    }
                });
        myJpanel.add(
                newSprintButton,
                new CustomConstraints(
                        0, 1, GridBagConstraints.WEST, 1.0, 0.2, GridBagConstraints.HORIZONTAL));

        add(myJpanel);
    }
}
