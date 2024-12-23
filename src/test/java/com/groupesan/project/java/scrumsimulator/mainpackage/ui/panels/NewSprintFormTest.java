package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.SpinnerNumberModel;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;

public class NewSprintFormTest {

    private NewSprintForm newSprintForm;

    @BeforeEach
    public void setUp() {
        newSprintForm = new NewSprintForm();
    }

    @Test
    public void testInitialization() {
        assertNotNull(newSprintForm, "NewSprintForm should be initialized");
        assertEquals("New Sprint", newSprintForm.getTitle(), "Title should be 'New Sprint'");
    }

    @Test
    public void testComponentsExist() {
        assertNotNull(newSprintForm.nameField);
        assertNotNull(newSprintForm.descArea);
        assertNotNull(newSprintForm.sprintDays);
        assertNotNull(newSprintForm.usList);
    }

    @Test
    public void testSpinnerInitialValues() {
        SpinnerNumberModel model = (SpinnerNumberModel) newSprintForm.sprintDays.getModel();
        assertEquals(5, model.getValue());
        assertEquals(1, model.getMinimum());
        assertEquals(999999, model.getMaximum());
        assertEquals(1, model.getStepSize());
    }

    @Test
    public void testCreateSprint() {
        SprintFactory sprintFactory = SprintFactory.getSprintFactory();
        Sprint sprint = sprintFactory.createNewSprint("Test Sprint", "Test Description", 10);

        assertNotNull(sprint, "Sprint should not be null");
        assertEquals("Test Sprint", sprint.getName(), "Sprint name should be 'Test Sprint'");
        assertEquals("Test Description", sprint.getDescription(), "Description should match");
        assertEquals(10, sprint.getLength(), "Sprint length should be 10 days");
    }

    @Test
    public void testAddUserStory() {
        SprintFactory sprintFactory = SprintFactory.getSprintFactory();
        Sprint sprint = sprintFactory.createNewSprint("Test Sprint", "Test Description", 10);
        UserStory story = new UserStory("Story 1", "Description 1", 5, 0);

        sprint.addUserStory(story);
        assertEquals(1, sprint.getUserStories().size(), "Sprint should have 1 user story");
        assertTrue(sprint.getUserStories().contains(story), "Sprint should contain the added user story");
    }
}