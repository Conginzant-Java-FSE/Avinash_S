package com.revhire.service;

import com.revhire.dao.JobDAO;
import com.revhire.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private JobDAO jobDAO;

    @InjectMocks
    private JobService jobService;

    private Job testJob;

    @BeforeEach
    void setUp() {
        testJob = new Job();
        testJob.setId(1);
        testJob.setTitle("Software Engineer");
        testJob.setEmployerId(101);
    }

    @Test
    void testPostJob() throws SQLException {
        when(jobDAO.createJob(any(Job.class))).thenReturn(testJob);

        Job createdJob = jobService.postJob(101, "Software Engineer", "Desc", "Req", "Remote", "100k", "Full-time", 5,
                "2023-12-31");

        assertNotNull(createdJob);
        assertEquals("Software Engineer", createdJob.getTitle());
        verify(jobDAO, times(1)).createJob(any(Job.class));
    }

}
