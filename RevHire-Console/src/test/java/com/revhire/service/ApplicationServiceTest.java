package com.revhire.service;

import com.revhire.dao.ApplicationDAO;
import com.revhire.model.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationDAO applicationDAO;

    @InjectMocks
    private ApplicationService applicationService;

    private Application testApp;

    @BeforeEach
    void setUp() {
        testApp = new Application();
        testApp.setId(1);
        testApp.setJobId(10);
        testApp.setSeekerId(100);
        testApp.setStatus("APPLIED");
    }

    @Test
    void testApplyForJob() throws SQLException {
        when(applicationDAO.createApplication(any(Application.class))).thenReturn(testApp);

        Application app = applicationService.applyForJob(10, 100, "ResumeContent");

        assertNotNull(app);
        assertEquals("APPLIED", app.getStatus());
        verify(applicationDAO).createApplication(any(Application.class));
    }

    @Test
    void testGetApplicationsForJob() {
        when(applicationDAO.getApplicationsByJobId(10)).thenReturn(Arrays.asList(testApp));

        List<Application> apps = applicationService.getApplicationsForJob(10);

        assertFalse(apps.isEmpty());
        assertEquals(1, apps.size());
    }
}
