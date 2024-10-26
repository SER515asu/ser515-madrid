package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlockerSolution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class NewBlockerSolutionFormTest {
    private NewBlockerSolutionForm form;
    private JTextField nameField;
    private JTextArea descArea;

    @BeforeEach
    void setUp() throws Exception {
        form = new NewBlockerSolutionForm();
        Field nameFieldField = NewBlockerSolutionForm.class.getDeclaredField("nameField");
        Field descAreaField = NewBlockerSolutionForm.class.getDeclaredField("descArea");
        nameFieldField.setAccessible(true);
        descAreaField.setAccessible(true);
        nameField = (JTextField) nameFieldField.get(form);
        descArea = (JTextArea) descAreaField.get(form);
    }

    @Test
    void testInitialComponentsSetup() {
        assertNotNull(form, "Form should be initialized");
        assertEquals("New Solution", form.getTitle(), "Form title should be 'New Solution'");
        assertEquals(JFrame.DISPOSE_ON_CLOSE, form.getDefaultCloseOperation(), 
            "Default close operation should be DISPOSE_ON_CLOSE");
        
        Dimension size = form.getSize();
        assertEquals(500, size.width, "Form width should be 500");
        assertEquals(400, size.height, "Form height should be 400");
    }

    @Test
    void testValidateFormWithValidInput() {
        nameField.setText("Test Title");
        descArea.setText("Test Description");
        assertTrue(form.validateForm(), "Form should validate with all fields filled");
    }

    @Test
    void testGetBlockerSolutionObjectWhenSubmitted() throws Exception {
        Field isFormSubmittedField = NewBlockerSolutionForm.class.getDeclaredField("isFormSubmitted");
        isFormSubmittedField.setAccessible(true);
        isFormSubmittedField.set(form, true);

        nameField.setText("Test Title");
        descArea.setText("Test Description");

        SprintBlockerSolution solution = form.getBlockerSolutionObject();
        assertNotNull(solution, "Should create blocker solution when form is submitted");
        assertEquals("Test Title", solution.getName(), "Solution title should match input");
        assertEquals("Test Description", solution.getDescription(), "Solution description should match input");
    }

    @Test
    void testFormLayout() {
        Container contentPane = form.getContentPane();
        Component[] components = ((JPanel) contentPane.getComponent(0)).getComponents();
        
        boolean hasNameLabel = false;
        boolean hasDescriptionLabel = false;
        boolean hasSubmitButton = false;
        boolean hasCancelButton = false;

        for (Component component : components) {
            if (component instanceof JLabel) {
                String text = ((JLabel) component).getText();
                if ("Title:".equals(text)) hasNameLabel = true;
                if ("Description:".equals(text)) hasDescriptionLabel = true;
            }
            if (component instanceof JButton) {
                String text = ((JButton) component).getText();
                if ("Submit".equals(text)) hasSubmitButton = true;
                if ("Cancel".equals(text)) hasCancelButton = true;
            }
        }

        assertTrue(hasNameLabel, "Form should have a title label");
        assertTrue(hasDescriptionLabel, "Form should have a description label");
        assertTrue(hasSubmitButton, "Form should have a submit button");
        assertTrue(hasCancelButton, "Form should have a cancel button");
    }
}