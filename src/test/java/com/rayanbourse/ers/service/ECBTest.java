package com.rayanbourse.ers.service;

import com.rayanbourse.ers.model.ecb.CubeData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ECBServiceTest {

    @Autowired
    public ECBService ecbService;

    @Test
    public void testFetchData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<CubeData> cubes = (List<CubeData>) getFetchDataMethod().invoke(ecbService);
        assertFalse(cubes.isEmpty());
    }

    private Method getFetchDataMethod() throws NoSuchMethodException {
        Method method = ECBService.class.getDeclaredMethod("fetchData");
        method.setAccessible(true);
        return method;
    }
}