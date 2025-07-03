/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.*;
import mz.sisden.sisden.repositories.*;
import mz.sisden.sisden.entities.NotificationType;
import mz.sisden.sisden.entities.Permission;
import mz.sisden.sisden.repositories.NotificationTypeRepository;
import mz.sisden.sisden.repositories.PermissionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * methods that end with Id will be added in ZkListModel paramMap, getFeeXyzId will be fee_xyz_id;
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AppConstants {

    private final Environment environment;
    private final PermissionRepository permissionRepository;
    private final NotificationTypeRepository notificationTypeRepository;

    // Cache para NotificationType
    private final ConcurrentMap<Long, NotificationType> notificationTypeCache = new ConcurrentHashMap<>();

    // Cache para Permission
    private final ConcurrentMap<Long, Permission> permissionCache = new ConcurrentHashMap<>();


    //
    public static HashMap<String, Object> createParamMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        fillMap(hashMap);
        return hashMap;
    }

    public static void fillMap(Map<String, Object> paramMap) {
        Method[] methods = AppConstants.class.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (!methodName.endsWith("Id")) {
                continue;
            }

            String methodNameWithoutGet = StringUtils.remove(methodName, "get");
            StringBuilder paramName = new StringBuilder();

            List<String> charsList = Arrays.stream(methodNameWithoutGet.split("")).toList();
            for (int i = 0; i < charsList.size(); i++) {
                String character = charsList.get(i);

                if (Character.isUpperCase(character.charAt(0)) && i != 0) {
                    paramName.append("_").append(character);
                } else {
                    paramName.append(character);
                }
            }

            try {
                paramMap.put(StringUtils.lowerCase(paramName.toString()), method.invoke(AppContext.getByClass(AppConstants.class)));
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn("Failed to set param {} due to: ", paramName, e);
            }
        }
    }
}
