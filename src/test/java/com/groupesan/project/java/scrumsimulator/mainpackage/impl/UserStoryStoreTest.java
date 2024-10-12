package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserStoryStoreTest {

    private UserStoryStore userStoryStore;

    @BeforeEach
    void setUp() {
        userStoryStore = UserStoryStore.getInstance();
        userStoryStore.clearUserStories(); // Use the new method to clear stories
    }

    @Test
    void testAddUserStory() {
        UserStory userStory = new UserStory("Test Story", "Description", 5, 0);
        userStoryStore.addUserStory(userStory);
        
        List<UserStory> stories = userStoryStore.getUserStories();
        assertEquals(1, stories.size());
        assertTrue(stories.contains(userStory));
    }

    @Test
    void testGetInstance() {
        UserStoryStore instance1 = UserStoryStore.getInstance();
        UserStoryStore instance2 = UserStoryStore.getInstance();
        
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2);
    }

    @Test
    void testGetUserStories() {
        UserStory story1 = new UserStory("Story 1", "Description 1", 3, 0);
        UserStory story2 = new UserStory("Story 2", "Description 2", 5, 0);
        
        userStoryStore.addUserStory(story1);
        userStoryStore.addUserStory(story2);
        
        List<UserStory> stories = userStoryStore.getUserStories();
        
        assertEquals(2, stories.size());
        assertTrue(stories.contains(story1));
        assertTrue(stories.contains(story2));
    }

    @Test
    void testGetUserStoryByName() {
        UserStory story1 = new UserStory("Story 1", "Description 1", 3, 0);
        UserStory story2 = new UserStory("Story 2", "Description 2", 5, 0);
        
        userStoryStore.addUserStory(story1);
        userStoryStore.addUserStory(story2);
        
        UserStory foundStory = userStoryStore.getUserStoryByName("Story 1");
        assertNotNull(foundStory);
        assertEquals("Story 1", foundStory.getName());
        
        UserStory notFoundStory = userStoryStore.getUserStoryByName("Non-existent Story");
        assertNull(notFoundStory);
    }

    @Test
    void testRemoveUserStory() {
        UserStory story1 = new UserStory("Story 1", "Description 1", 3, 0);
        UserStory story2 = new UserStory("Story 2", "Description 2", 5, 0);
        
        userStoryStore.addUserStory(story1);
        userStoryStore.addUserStory(story2);
        
        userStoryStore.removeUserStory(story1);
        
        List<UserStory> stories = userStoryStore.getUserStories();
        assertEquals(1, stories.size());
        assertFalse(stories.contains(story1));
        assertTrue(stories.contains(story2));
    }
}