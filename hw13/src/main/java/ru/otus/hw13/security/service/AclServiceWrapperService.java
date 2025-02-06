package ru.otus.hw13.security.service;

import org.springframework.security.acls.model.Permission;

public interface AclServiceWrapperService {

    void createPermissions(Object object, Permission... permissions);
}
