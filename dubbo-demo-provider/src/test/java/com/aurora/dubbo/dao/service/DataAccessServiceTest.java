package com.aurora.dubbo.dao.service;

import com.aurora.dubbo.dao.entity.DataAccess;
import com.aurora.dubbo.dao.repository.DataAccessRepository;
import com.aurora.gateway.common.utils.AccessKeyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataAccessServiceTest {

    @Mock
    private DataAccessRepository dataAccessRepository;

    @InjectMocks
    private DataAccessService dataAccessService;

    private static final Integer TEST_ID = 1;
    private static final String TEST_TENANT = "testTenant";

    @BeforeEach
    public void setUp() {
        // Setup mock behavior here if needed
    }

    @Test
    public void testAddDataAccess() {
        DataAccess dataAccess = new DataAccess();
        dataAccess.setTenant(TEST_TENANT);
        dataAccess.setAccessKey(AccessKeyUtils.generateAccessKey());
        dataAccess.setSecretKey(AccessKeyUtils.generateSecretKey());
        dataAccess.setExpireTime(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000L);

        when(dataAccessRepository.save(any(DataAccess.class))).thenReturn(dataAccess);

        DataAccess result = dataAccessService.addDataAccess(TEST_TENANT);

        assertNotNull(result);
        assertEquals(TEST_TENANT, result.getTenant());
        assertNotNull(result.getAccessKey());
        assertNotNull(result.getSecretKey());
        assertTrue(result.getExpireTime() > System.currentTimeMillis());

        verify(dataAccessRepository).save(any(DataAccess.class));
    }

    @Test
    public void testGetDataAccessById() {
        DataAccess existingDataAccess = new DataAccess();
        existingDataAccess.setId(TEST_ID);
        existingDataAccess.setTenant(TEST_TENANT);

        when(dataAccessRepository.findById(TEST_ID)).thenReturn(Optional.of(existingDataAccess));

        DataAccess result = dataAccessService.getDataAccessById(TEST_ID);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_TENANT, result.getTenant());

        verify(dataAccessRepository).findById(TEST_ID);
    }

    @Test
    public void testGetDataAccessByIdNotFound() {
        when(dataAccessRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        DataAccess result = dataAccessService.getDataAccessById(TEST_ID);

        assertNull(result);

        verify(dataAccessRepository).findById(TEST_ID);
    }

    @Test
    public void testDeleteDataAccessById() {
        dataAccessService.deleteDataAccessById(TEST_ID);

        verify(dataAccessRepository).deleteById(TEST_ID);
    }
}