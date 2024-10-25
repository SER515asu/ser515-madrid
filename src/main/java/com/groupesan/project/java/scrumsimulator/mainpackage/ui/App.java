package com.groupesan.project.java.scrumsimulator.mainpackage.ui;

import com.formdev.flatlaf.FlatLightLaf;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.*;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.DemoPane;
import javax.swing.*;

public class App {
    public App() {}

    public void start() {
        this.loadTheme();
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        // Initialize User Stories and sprints in helper function now
                        initializeSimulatorData();

                        // Load DemoPane
                        DemoPane form = new DemoPane();
                        form.setVisible(true);
                    }
                });
    }

    private void initializeSimulatorData() {
        Sprint aSprint = SprintFactory.getSprintFactory().createNewSprint("Sprint1", "This is Sprint 1", 2, 5);
        Sprint aSprint2 = SprintFactory.getSprintFactory().createNewSprint("Sprint2", "This is Sprint 2", 4, 10);
        Sprint aSprint3 = SprintFactory.getSprintFactory().createNewSprint("Sprint3", "This is Sprint 3", 6, 15);
        Sprint aSprint4 = SprintFactory.getSprintFactory().createNewSprint("Sprint4", "This is Sprint 4", 8, 10);
        Sprint aSprint5 = SprintFactory.getSprintFactory().createNewSprint("Sprint5", "This is Sprint 5", 10, 5);
        SprintStore.getInstance().addSprint(aSprint);
        SprintStore.getInstance().addSprint(aSprint2);
        SprintStore.getInstance().addSprint(aSprint3);
        SprintStore.getInstance().addSprint(aSprint4);
        SprintStore.getInstance().addSprint(aSprint5);
        UserStory us1 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS1", "description1", 1.0, 1.0);
        us1.doRegister();
        UserStoryStore.getInstance().addUserStory(us1);

        UserStory us2 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS2", "description2", 2.0, 2.0);
        us2.doRegister();
        UserStoryStore.getInstance().addUserStory(us2);

        UserStory us3 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS3", "description3", 3.0, 3.0);
        us3.doRegister();
        UserStoryStore.getInstance().addUserStory(us3);

        UserStory us4 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS4", "description4", 4.0, 3.0);
        us4.doRegister();
        UserStoryStore.getInstance().addUserStory(us4);

        UserStory us5 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS5", "description5", 8.0, 7.0);
        us5.doRegister();
        UserStoryStore.getInstance().addUserStory(us5);

        UserStory us6 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS6", "description6", 6.0, 3.0);
        us6.doRegister();
        UserStoryStore.getInstance().addUserStory(us6);

        UserStory us7 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS7", "description7", 7.0, 7.0);
        us7.doRegister();
        UserStoryStore.getInstance().addUserStory(us7);

        UserStory us8 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS8", "description8", 8.0, 8.0);
        us8.doRegister();
        UserStoryStore.getInstance().addUserStory(us8);

        UserStory us9 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS9", "description9", 6.0, 6.0);
        us9.doRegister();
        UserStoryStore.getInstance().addUserStory(us9);

        UserStory us10 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS10", "description10", 6.0, 5.0);
        us10.doRegister();
        UserStoryStore.getInstance().addUserStory(us10);

        UserStory us11 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS11", "description11", 4.0, 4.0);
        us11.doRegister();
        UserStoryStore.getInstance().addUserStory(us11);

        UserStory us12 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS12", "description12", 13.0, 13.0);
        us12.doRegister();
        UserStoryStore.getInstance().addUserStory(us12);

        UserStory us13 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS13", "description13", 8.0, 7.0);
        us13.doRegister();
        UserStoryStore.getInstance().addUserStory(us13);

        UserStory us14 =
                UserStoryFactory.getInstance()
                        .createNewUserStory("predefinedUS14", "description14", 9.0, 7.0);
        us14.doRegister();
        UserStoryStore.getInstance().addUserStory(us14);
        SprintStore.getInstance().getSprints().getFirst().addUserStory(us1);
        SprintStore.getInstance().getSprints().getFirst().addUserStory(us2);
        SprintStore.getInstance().getSprints().getFirst().addUserStory(us3);
        SprintStore.getInstance().getSprints().get(1).addUserStory(us4);
        SprintStore.getInstance().getSprints().get(1).addUserStory(us5);
        SprintStore.getInstance().getSprints().get(2).addUserStory(us6);
        SprintStore.getInstance().getSprints().get(2).addUserStory(us7);
        SprintStore.getInstance().getSprints().get(3).addUserStory(us8);
        SprintStore.getInstance().getSprints().get(4).addUserStory(us9);
        SprintStore.getInstance().getSprints().get(4).addUserStory(us10);
        SprintStore.getInstance().getSprints().get(4).addUserStory(us11);
    }

    private void loadTheme() {
        try {
            // TODO support setting theme from a configuration file
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }
}
