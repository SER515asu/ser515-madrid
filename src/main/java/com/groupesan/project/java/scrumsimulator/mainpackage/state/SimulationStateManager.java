package com.groupesan.project.java.scrumsimulator.mainpackage.state;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.*;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.BlockersListPane;
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
    private boolean interrupted = false;
    private JFrame frame;
    private JTextArea sprintDisplayArea;
    private static final SecureRandom secureRandom = new SecureRandom();
    private SimulationButtonStateInterface buttonStateListener;
    private List<UserStory> blockedUserStories;

    private List<BlockerUpdateListener> blockerUpdateListeners = new ArrayList<>();

    private BlockersListPane blockersListPane;

    SecureRandom random = new SecureRandom();

    /** Simulation State manager. Not running by default. */
    public SimulationStateManager() {
        this.running = false;
        this.blockedUserStories = new ArrayList<>();
    }

   public void setButtonStateListener(SimulationButtonStateInterface listener) {
        this.buttonStateListener = listener;
    }
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
        if (buttonStateListener != null) {
            buttonStateListener.updateButtonVisibility(running);
        }
    }

    /** Method to set the simulation state to running. */
    public void startSimulation() {
        interrupted = false;
        blockedUserStories.clear();
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
                boolean executionFailed = false;
                for (Sprint sprint : sprints) {
                    if (interrupted) break;
                    int expectedSprintPoints = sprint.getStoryPoints();
                    int actualSprintPoints = 0;
                    String sprintExecutionText = "Sprint " + sprint.getName() + " executing...";
                    SwingUtilities.invokeLater(() -> sprintDisplayArea.append(sprintExecutionText + "\n"));
                    try {
                        List<UserStory> userStories = sprint.getUserStories();
                        for (UserStory userStory : userStories) {
                            if (interrupted) break;
                            int expectedStoryPoints = (int) userStory.getPointValue();
                            int secureRandomValue = secureRandom.nextInt(100) + 1;
                            int actualStoryPoints = 0;
                            if (secureRandomValue <= 80) {
                                actualStoryPoints = expectedStoryPoints;  // 80% chance to be equal
                            } else if (secureRandomValue <= 90) {
                                actualStoryPoints = expectedStoryPoints - 1;  // 10% chance to be less
                            } else {
                                actualStoryPoints = expectedStoryPoints + 2;  // 10% chance to be more
                            }
                            actualSprintPoints += actualStoryPoints;
                            String storyExecText = "  User Story " + userStory + " executing...";
                            SwingUtilities.invokeLater(() -> sprintDisplayArea.append(storyExecText + "\n"));

                            handleBlocker(userStory, sprintDisplayArea);

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

                if (!executionFailed && !interrupted) {
                    boolean allStoriesResolved = handleBlockedUserStories();
                    if (!allStoriesResolved) {
                        executionFailed = true;
                    }
                }

                if (executionFailed) {
                    SwingUtilities.invokeLater(() -> {
                        sprintDisplayArea.append("Simulator execution failed\n");
                        JOptionPane.showMessageDialog(frame, "Simulator execution failed", "Execution Status", JOptionPane.ERROR_MESSAGE);
                    });
                } else if (interrupted) {
                    sprintDisplayArea.append("Simulator execution stopped\n");
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
                setRunning(false);
            }
        };

        worker.execute();
    }

    /** Method to set the simulation state to not running. */
    public void stopSimulation() {
        interrupted = true;
        setRunning(false);
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
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                stopSimulation();
            }
        });
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

    private boolean handleBlockedUserStories() throws Exception {
        if (blockedUserStories.isEmpty()) {
            return true;
        }

        for (Iterator<UserStory> iterator = blockedUserStories.iterator(); iterator.hasNext();) {
            UserStory blockedStory = iterator.next();
            
            AtomicInteger choice = new AtomicInteger();
            
            SwingUtilities.invokeAndWait(() -> {
                String message = String.format("User Story %s is blocked. Has a solution been found?", 
                    blockedStory.toString());
                Object[] options = {"Solved", "No solution available"};
                int result = JOptionPane.showOptionDialog(
                    frame,
                    message,
                    "Blocked User Story Resolution",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
                );
                choice.set(result);
            });

            if (choice.get() == JOptionPane.YES_OPTION) {
                List<SprintBlocker> blockers = blockedStory.getBlockers();
                if (blockers != null) {
                    for (SprintBlocker blocker : blockers) {
                        if (!blocker.getStatus().equals("RESOLVED")) {
                            blocker.setStatus("Resolved");
                            sprintDisplayArea.append("BLOCKER : " + blocker.getName() + " RESOLVED\n");
                            notifyBlockerStatusChanged(blocker,"Resolved");
                        }
                    }
                }
                iterator.remove();
                sprintDisplayArea.append("User Story " + blockedStory + " has been unblocked\n");
            } else {
                
                String userStoryText = "User Story " + blockedStory + " COMPLETED";
                String updatedUserStoryText = "User Story " + blockedStory + " BLOCKED";
                String sprintCompletedText = "Sprint " + blockedStory.getAssignedSprint().getName() + " COMPLETED";
                String sprintIncompleteText = "Sprint " + blockedStory.getAssignedSprint().getName() + " INCOMPLETE";
                
                SwingUtilities.invokeLater(() -> {
                    String currentText = sprintDisplayArea.getText();
                    String updatedText = currentText.replace(userStoryText, updatedUserStoryText);
                    
                    updatedText = updatedText.replace(sprintCompletedText, sprintIncompleteText);
                    
                    sprintDisplayArea.setText(updatedText);
                });
                
                sprintDisplayArea.append("No solution available for User Story " + blockedStory + 
                    ". Simulation failed.\n");
                return false;
            }
        }
        return true;
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
    public boolean evaluateBlockerAndSolution(ProbabilityRange blockerOrSolution) {
        double blockerOrSolutionProbability = ProbabilityUtils.generateRandomProbability(blockerOrSolution);
        return ProbabilityUtils.checkTheSuccessScenario(blockerOrSolutionProbability);
    }

    synchronized void resetAllBlockersStatus() {
        List<SprintBlocker> blockersList =  BlockerStore.getInstance().getBlockers();

        if(blockersList !=null && blockersList.size()>0){
            for(SprintBlocker blocker : BlockerStore.getInstance().getBlockers()){
                blocker.setStatus("Open");
                notifyBlockerStatusChanged(blocker,"Open");
            }
        }
    }

    private void handleBlocker(UserStory userStory, JTextArea sprintDisplayArea) {
        double probability = random.nextDouble();
        List<SprintBlocker> blockers = userStory.getBlockers();
        if (probability <= 0.1) {
            JOptionPane optionPane = new JOptionPane("Technical issue detected! Stopping the simulator...",
                    JOptionPane.WARNING_MESSAGE);

            // Create the dialog
            JDialog dialog = optionPane.createDialog("Warning");
            dialog.setModal(true); // Make the dialog modal
            dialog.setVisible(true);

            resetAllBlockersStatus();
            sprintDisplayArea.append("All blockers have been reset to UNRESOLVED due to the technical issue.\n");

            stopSimulation();
           // openBlockersPane();
            return;
        }
        if (blockers != null && !blockers.isEmpty()) {
            for (SprintBlocker blocker : blockers) {
                boolean foundBlocker = evaluateBlockerAndSolution(blocker);
                if (foundBlocker) {
                    SwingUtilities.invokeLater(() -> {
                        sprintDisplayArea.append("  BLOCKER DETECTED for User Story " + userStory.toString() + ":\n");
                        sprintDisplayArea.append("    Blocker: " + blocker.getName() + "\n");
                        sprintDisplayArea.append("    Description: " + blocker.getDescription() + "\n");

                        SprintBlockerSolution solution = blocker.getSolution();
                        boolean foundSolution = evaluateBlockerAndSolution(solution);
                        if (foundSolution){
                            blocker.setStatus("Resolved");
                            notifyBlockerStatusChanged(blocker, "Resolved");
                            sprintDisplayArea.append("BLOCKER : " + blocker.getName() + "Resolved  "+"\n");
                        }
                        else{
                            blockedUserStories.add(userStory);
                            sprintDisplayArea.append("BLOCKER: " + blocker.getName() + " NOT RESOLVED - Added to blocked stories.\n");
                        }
                    });
                }
            }
        }
    }

    void openBlockersPane(){
        if (blockersListPane == null || !blockersListPane.isDisplayable()) {
            blockersListPane = new BlockersListPane();
            blockersListPane.setVisible(true);
        } else {
            blockersListPane.toFront();
        }
    }

    public void addBlockerUpdateListener(BlockerUpdateListener listener) {
        if (!blockerUpdateListeners.contains(listener)) {
            blockerUpdateListeners.add(listener);
        }
    }

    private void notifyBlockerStatusChanged(SprintBlocker blocker, String newStatus) {
        for (BlockerUpdateListener listener : blockerUpdateListeners) {
            listener.onBlockerStatusChanged(blocker, newStatus);
        }
    }

    
}
