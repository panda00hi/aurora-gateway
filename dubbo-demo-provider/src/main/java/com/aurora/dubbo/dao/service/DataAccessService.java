package com.aurora.dubbo.dao.service;

import com.aurora.dubbo.dao.entity.DataAccess;
import com.aurora.dubbo.dao.repository.DataAccessRepository;
import com.aurora.gateway.common.utils.AccessKeyUtils;
import org.springframework.stereotype.Service;

/**
 * @author panda00hi
 * @date 2024.07.10
 */
@Service
public class DataAccessService {

    // 注入dataAccessRepository
    private final DataAccessRepository dataAccessRepository;

    public DataAccessService(DataAccessRepository dataAccessRepository) {
        this.dataAccessRepository = dataAccessRepository;
    }

    /**
     * 生成新的数据访问密钥
     *
     * @param tenant
     * @return
     */
    public DataAccess addDataAccess(String tenant) {
        DataAccess dataAccess = new DataAccess();
        dataAccess.setTenant(tenant);
        dataAccess.setAccessKey(AccessKeyUtils.generateAccessKey());
        dataAccess.setSecretKey(AccessKeyUtils.generateSecretKey());
        dataAccess.setExpireTime(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000L);
        return dataAccessRepository.save(dataAccess);
    }

    public DataAccess getDataAccessById(Integer id) {
        return dataAccessRepository.findById(id).orElse(null);
    }

    public void deleteDataAccessById(Integer id) {
        dataAccessRepository.deleteById(id);
    }

}
