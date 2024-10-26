package com.groupesan.project.java.scrumsimulator.mainpackage.state;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import javax.swing.*;
import java.awt.BorderLayout;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.SimulationPanel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * SimulationStateManager manages the state of a simulation, including whether it is running and
 * saving its ID.
 */
public class SimulationStateManager {
    private boolean running;
    private static final String JSON_FILE_PATH = "src/main/resources/simulation.JSON";
    private JFrame frame;
    private JTextArea sprintDisplayArea;
    private SimulationPanel simulationPanel;

    /** Simulation State manager. Not running by default. */
    public SimulationStateManager() {
        this.running = false;
    }

    /**
     * Returns the current state of the simulation.
     *
     * @return boolean running
     */
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /** Method to set the simulation state to running. */
    public void startSimulation() {
        List<Sprint> sprints = SprintStore.getInstance().getSprints();
        if (sprints.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(frame, "You have not added sprints to execute the Simulator.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            });
            return;
        }
        setRunning(true);
        simulationUI();

        sprintDisplayArea.append("Simulation started...\n");

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                SecureRandom random = new SecureRandom();
                boolean executionFailed = false;
                for (Sprint sprint : sprints) {
                    int expectedSprintPoints = sprint.getStoryPoints();
                    int actualSprintPoints = 0;
                    String sprintExecutionText = "Sprint " + sprint.getName() + " executing...";
                    SwingUtilities.invokeLater(() -> sprintDisplayArea.append(sprintExecutionText + "\n"));
                    try {
                        List<UserStory> userStories = sprint.getUserStories();
                        for (UserStory userStory : userStories) {
                            int expectedStoryPoints = (int) userStory.getPointValue();
                            int randomValue = random.nextInt(100) + 1;
                            int actualStoryPoints = 0;
                            if (randomValue <= 80) {
                                actualStoryPoints = expectedStoryPoints;  // 80% chance to be equal
                            } else if (randomValue <= 90) {
                                actualStoryPoints = expectedStoryPoints - 1;  // 10% chance to be less
                            } else {
                                actualStoryPoints = expectedStoryPoints + 2;  // 10% chance to be more
                            }
                            actualSprintPoints += actualStoryPoints;
                            String storyExecText = "  User Story " + userStory + " executing...";
                            SwingUtilities.invokeLater(() -> sprintDisplayArea.append(storyExecText + "\n"));

                            int sleepTime = (actualStoryPoints < 10) ? 2000 : 3000;
                            Thread.sleep(sleepTime);
                            int finalActualStoryPoints = actualStoryPoints;
                            SwingUtilities.invokeLater(() -> {
                                int lastIndex = sprintDisplayArea.getText().lastIndexOf(storyExecText);

                                if (lastIndex != -1) {
                                    String currentText = sprintDisplayArea.getText();
                                    String updatedText = currentText.substring(0, lastIndex)
                                            + "User Story " + userStory
                                            + " COMPLETED (Expected Effort: " + expectedStoryPoints
                                            + ", Actual Effort: " + finalActualStoryPoints + ")"
                                            + currentText.substring(lastIndex + storyExecText.length());
                                    sprintDisplayArea.setText(updatedText);
                                }
                            });
                        }
                        String sprintCompletedText = "Sprint " + sprint.getName() + " COMPLETED";

                        SwingUtilities.invokeLater(() -> {
                            int lastIndex = sprintDisplayArea.getText().lastIndexOf(sprintExecutionText);
                            if (lastIndex != -1) {
                                String currentText = sprintDisplayArea.getText();
                                String updatedText = currentText.substring(0, lastIndex)
                                        + sprintCompletedText
                                        + currentText.substring(lastIndex + sprintExecutionText.length());
                                sprintDisplayArea.setText(updatedText);
                            }
                        });
                        if (actualSprintPoints > expectedSprintPoints) {
                            executionFailed = true;
                            break;
                        }
                    }
                    catch(Exception e){
                        String failureMessage = "Sprint "+ sprint.getName()+"FAILED";
                        SwingUtilities.invokeLater(()->{
                            sprintDisplayArea.append( failureMessage + "\n");
                            JOptionPane.showMessageDialog(frame,failureMessage);
                        });
                    }
                }
                if (executionFailed) {
                    SwingUtilities.invokeLater(() -> {
                        sprintDisplayArea.append("Simulator execution failed\n");
                        JOptionPane.showMessageDialog(frame, "Simulator execution failed", "Execution Status", JOptionPane.ERROR_MESSAGE);
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        sprintDisplayArea.append("Simulator execution successful\n");
                        JOptionPane.showMessageDialog(frame, "Simulator execution successful", "Execution Status", JOptionPane.INFORMATION_MESSAGE);
                    });
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String message : chunks) {
                    sprintDisplayArea.append(message + "\n");
                }
            }

            @Override
            protected void done() {
                running = false;
                SimulationPanel.updateButtonVisibility();
            }
        };

        worker.execute();
    }

    /** Method to set the simulation state to not running. */
    public void stopSimulation() {
        setRunning(false);
        // Add other logic for stopping the simulation
    }

    private void simulationUI() {
        frame = new JFrame("Simulation Progress");
        sprintDisplayArea = new JTextArea(20, 40);
        sprintDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(sprintDisplayArea);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Saves the details of a new simulation to a JSON file.
     *
     * @param simId The ID of the simulation.
     * @param simName The name of the simulation.
     * @param numberOfSprints The number of sprints in the simulation.
     */
    public static void saveNewSimulationDetails(
            String simId, String simName, String numberOfSprints) {
        JSONObject simulationData = getSimulationData();
        if (simulationData == null) {
            simulationData = new JSONObject();
        }

        JSONObject newSimulation = new JSONObject();
        newSimulation.put("ID", simId);
        newSimulation.put("Name", simName);
        newSimulation.put("Status", "New");
        newSimulation.put("NumberOfSprints", numberOfSprints);
        newSimulation.put("Sprints", new JSONArray());
        newSimulation.put("Events", new JSONArray());
        newSimulation.put("Users", new JSONArray());

        JSONArray simulations = simulationData.optJSONArray("Simulations");
        if (simulations == null) {
            simulations = new JSONArray();
            simulationData.put("Simulations", simulations);
        }
        simulations.put(newSimulation);

        updateSimulationData(simulationData);
    }

    private static JSONObject getSimulationData() {
        try (FileInputStream fis = new FileInputStream(JSON_FILE_PATH)) {
            JSONTokener tokener = new JSONTokener(fis);
            return new JSONObject(tokener);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading from simulation.JSON");
            return null;
        }
    }

    private static void updateSimulationData(JSONObject updatedData) {
        try (OutputStreamWriter writer =
                     new OutputStreamWriter(
                             new FileOutputStream(JSON_FILE_PATH), StandardCharsets.UTF_8)) {
            writer.write(updatedData.toString(4));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to simulation.JSON");
        }
    }
}
