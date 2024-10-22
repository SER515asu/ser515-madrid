package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlockerSolutionsListPaneTest {

    private BlockerSolutionsListPane blockerSolutionsListPane;

    @BeforeEach
    public void setUp() {
        blockerSolutionsListPane = new BlockerSolutionsListPane();
    }

    @Test
    public void testMainComponentsExist() throws NoSuchFieldException, IllegalAccessException {
        Field headerPanelField = BlockerSolutionsListPane.class.getDeclaredField("headerPanel");
        headerPanelField.setAccessible(true);
        JPanel headerPanel = (JPanel) headerPanelField.get(blockerSolutionsListPane);
        assertNotNull(headerPanel, "Header panel should exist");

        Field blockerSolutionsPanelField = BlockerSolutionsListPane.class.getDeclaredField("blockerSolutionsPanel");
        blockerSolutionsPanelField.setAccessible(true);
        JPanel blockerSolutionsPanel = (JPanel) blockerSolutionsPanelField.get(blockerSolutionsListPane);
        assertNotNull(blockerSolutionsPanel, "Blocker solutions panel should exist");
    }

    @Test
    public void testHeaderLabelsExist() throws NoSuchFieldException, IllegalAccessException {
        Field headerPanelField = BlockerSolutionsListPane.class.getDeclaredField("headerPanel");
        headerPanelField.setAccessible(true);
        JPanel headerPanel = (JPanel) headerPanelField.get(blockerSolutionsListPane);
        boolean hasIdLabel = false;
        boolean hasNameLabel = false;
        boolean hasDescriptionLabel = false;

        for (Component comp : headerPanel.getComponents()) {
            if (comp instanceof JLabel) {
                String text = ((JLabel) comp).getText();
                if ("ID".equals(text)) hasIdLabel = true;
                if ("Name".equals(text)) hasNameLabel = true;
                if ("Description".equals(text)) hasDescriptionLabel = true;
            }
        }

        assertTrue(hasIdLabel, "ID label should exist");
        assertTrue(hasNameLabel, "Name label should exist");
        assertTrue(hasDescriptionLabel, "Description label should exist");
    }

    @Test
    public void testWidgetsListExists() throws NoSuchFieldException, IllegalAccessException {
        Field widgetsField = BlockerSolutionsListPane.class.getDeclaredField("widgets");
        widgetsField.setAccessible(true);
        List<?> widgets = (List<?>) widgetsField.get(blockerSolutionsListPane);
        assertNotNull(widgets, "Widgets list should exist");
    }
}