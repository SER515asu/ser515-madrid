package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;

public class NewUserStoryFormTest {

    private NewUserStoryForm newUserStoryForm;

    @BeforeEach
    public void setUp() {
        newUserStoryForm = new NewUserStoryForm();
        newUserStoryForm.init();
    }

    @Test
    public void testInitialization() {
        assertNotNull(newUserStoryForm, "NewUserStoryForm should be initialized");
        assertEquals("New User Story", newUserStoryForm.getTitle(), "Title should be 'New User Story'");
    }

    @Test
    public void testComponentsExist() {
        assertNotNull(newUserStoryForm.nameField, "Name field should exist");
        assertNotNull(newUserStoryForm.descArea, "Description area should exist");
        assertNotNull(newUserStoryForm.pointsCombo, "Points combo box should exist");
        assertNotNull(newUserStoryForm.businessValueCombo, "Business value combo box should exist");
        assertNotNull(newUserStoryForm.getSubmitButton(), "Submit button should exist");
    }

    @Test
    public void testPointsAndBusinessValueDefaultSelections() {
        JComboBox<Double> pointsCombo = newUserStoryForm.pointsCombo;
        JComboBox<Double> businessValueCombo = newUserStoryForm.businessValueCombo;

        assertEquals(1.0, pointsCombo.getItemAt(0), "First item in points combo should be 1.0");
        assertEquals(1.0, businessValueCombo.getItemAt(0), "First item in business value combo should be 1.0");
    }

    @Test
    public void testGetUserStoryObject() {
        newUserStoryForm.nameField.setText("Test Story");
        newUserStoryForm.descArea.setText("Test Description");
        newUserStoryForm.pointsCombo.setSelectedItem(5.0);
        newUserStoryForm.businessValueCombo.setSelectedItem(8.0);

        UserStory userStory = newUserStoryForm.getUserStoryObject();

        assertNotNull(userStory, "User story object should not be null");
        assertEquals("Test Story", userStory.getName(), "User story name should match");
        assertEquals("Test Description", userStory.getDescription(), "User story description should match");
        assertEquals(5.0, userStory.getPointValue(), "User story points should be 5.0");
        assertEquals(8.0, userStory.getBusinessValue(), "User story business value should be 8.0");
    }


}
