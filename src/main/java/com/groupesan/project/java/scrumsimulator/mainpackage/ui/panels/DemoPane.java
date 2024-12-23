package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.Player;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumRole;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.Simulation;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.WizardManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.WizardHandler;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoPane extends JFrame implements BaseComponent {
    private Player player = new Player("bob", new ScrumRole("demo"));
    private JButton productBacklogButton;
    private String currentRole = "Scrum Master";

    // Fields to store references to opened windows
    private SprintListPane sprintListPane;
    // private UserStoryListPane userStoryListPane;
    private UpdateUserStoryPanel updateUserStoryPanel;
    private SpikePane spikePane;
    private SimulationPane simulationPane;
    private ModifySimulationPane modifySimulationPane;
    private SimulationUI simulationUserInterface;
    private SimulationStateManager simulationStateManager;
    private BlockersListPane blockersListPane;
    // private SimulationSwitchRolePane simulationSwitchRolePane;
    private VariantSimulationUI variantSimulationUI;
    // private SprintUIPane sprintUIPane;

    public DemoPane() {
        this.simulationStateManager = new SimulationStateManager();
        this.init();
        player.doRegister();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Demo");
        setSize(1500, 400);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        JButton sprintsButton = new JButton("Sprints");
        sprintsButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (sprintListPane == null || !sprintListPane.isDisplayable()) {
                            sprintListPane = new SprintListPane();
                            sprintListPane.setVisible(true);
                        } else {
                            sprintListPane.toFront();
                        }
                    }
                });

        SimulationPanel simulationPanel = new SimulationPanel(simulationStateManager);
        myJpanel.add(
                simulationPanel,
                new CustomConstraints(
                        2, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        myJpanel.add(
                sprintsButton,
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        productBacklogButton = new JButton("Product Backlog");
        productBacklogButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        UserStoryListPane form = new UserStoryListPane(currentRole);
                        form.setVisible(true);
                    }
                });

        myJpanel.add(
                productBacklogButton,
                new CustomConstraints(
                        1, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton spikePaneButton = new JButton("Spike");
        spikePaneButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (spikePane == null || !spikePane.isDisplayable()) {
                            spikePane = new SpikePane();
                            spikePane.setVisible(true);
                        } else {
                            spikePane.toFront();
                        }
                    }
                });

        myJpanel.add(
                spikePaneButton,
                new CustomConstraints(
                        3, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton updateStoryStatusButton = new JButton("Update User Story Status");
        updateStoryStatusButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (updateUserStoryPanel == null || !updateUserStoryPanel.isDisplayable()) {
                            updateUserStoryPanel = new UpdateUserStoryPanel();
                            updateUserStoryPanel.setVisible(true);
                        } else {
                            updateUserStoryPanel.toFront();
                        }
                    }
                });

        myJpanel.add(
                updateStoryStatusButton,
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton newSimulationButton = new JButton("New Simulation");
        newSimulationButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        WizardManager.get().showSimulationWizard(new WizardHandler<Simulation>() {
                            @Override
                            public void onSubmit(Simulation data) {
                                // Handle the created simulation
                                // You can add more logic here to handle the new simulation
                            }
                        });
                    }
                });

        myJpanel.add(
                newSimulationButton,
                new CustomConstraints(
                        9, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton simulationButton = new JButton("Add User");
        simulationButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (simulationPane == null || !simulationPane.isDisplayable()) {
                            simulationPane = new SimulationPane();
                            simulationPane.setVisible(true);
                        } else {
                            simulationPane.toFront();
                        }
                    }
                });

        myJpanel.add(
                simulationButton,
                new CustomConstraints(
                        7, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

//        JButton modifySimulationButton = new JButton("Modify Simulation");
//        modifySimulationButton.addActionListener(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (modifySimulationPane == null || !modifySimulationPane.isDisplayable()) {
//                            SimulationManager simulationManager = new SimulationManager();
//                            modifySimulationPane = new ModifySimulationPane(simulationManager);
//                            modifySimulationPane.setVisible(true);
//                        } else {
//                            modifySimulationPane.toFront();
//                        }
//                    }
//                });


        JButton joinSimulationButton = new JButton("Join Simulation");
        joinSimulationButton.addActionListener(
                e -> {
                    if (simulationUserInterface == null || !simulationUserInterface.isDisplayable()) {
                        simulationUserInterface = new SimulationUI();
                        simulationUserInterface.setVisible(true);
                    } else {
                        simulationUserInterface.toFront();
                    }
                });

        myJpanel.add(
                joinSimulationButton,
                new CustomConstraints(
                        6, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton simulationSwitchRoleButton = new JButton("Switch Role");
        simulationSwitchRoleButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SimulationSwitchRolePane feedbackPanelUI = new SimulationSwitchRolePane(new RoleUpdateListener() {
                            @Override
                            public void onRoleUpdate(String newRole) {
                                currentRole = newRole;
                                simulationPanel.setRole(currentRole);
                                newSimulationButton.setEnabled("Scrum Master".equals(currentRole));
                                simulationPanel.setRole(currentRole);
                                spikePaneButton.setEnabled(!"Product Owner".equals(currentRole));
                            }
                        });
                        feedbackPanelUI.setVisible(true);
                    }
                });

        myJpanel.add(
                simulationSwitchRoleButton,
                new CustomConstraints(
                        1, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton variantSimulationUIButton = new JButton("Variant Simulation UI");
        variantSimulationUIButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (variantSimulationUI == null || !variantSimulationUI.isDisplayable()) {
                            variantSimulationUI = new VariantSimulationUI();
                            variantSimulationUI.setVisible(true);
                        } else {
                            variantSimulationUI.toFront();
                        }
                    }
                });

        myJpanel.add(
                variantSimulationUIButton,
                new CustomConstraints(
                        3, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton SprintUIButton = new JButton("US Selection UI");
        SprintUIButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Load SprintUIPane
                        SprintUIPane sprintUIPane = new SprintUIPane(player, currentRole);
                        sprintUIPane.setVisible(true);
                    }
                });

        myJpanel.add(
                SprintUIButton,
                new CustomConstraints(
                        8, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton blockerButton = new JButton("Blocker");
        blockerButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (blockersListPane == null || !blockersListPane.isDisplayable()) {
                            blockersListPane = new BlockersListPane();
                            blockersListPane.setSimulationStateManager(simulationStateManager);
                            blockersListPane.setVisible(true);
                        } else {
                            blockersListPane.toFront();
                        }
                    }
                });

        myJpanel.add(
                blockerButton,
                new CustomConstraints(
                        13, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        
        JButton blockerSolutionsButton = new JButton("Blocker Solutions");
        blockerSolutionsButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                BlockerSolutionsListPane blockerSolutionsPane = new BlockerSolutionsListPane();
                blockerSolutionsPane.setVisible(true);
                }
            });
        myJpanel.add(
            blockerSolutionsButton,
            new CustomConstraints(
                14, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));


        add(myJpanel);
    }
}
