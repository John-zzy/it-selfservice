package com.cnec5.it.selfservice.provider;

import com.cnec5.it.selfservice.dto.AddUserParams;
import com.cnec5.it.selfservice.provider.api.AdminUserService;
import com.cnec5.it.selfservice.provider.domain.AdminUser;
import com.cnec5.it.selfservice.provider.mapper.AdminUserMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminUserServiceTests {

    @Resource
    public AdminUserMapper adminUserMapper;

    @Reference(version = "1.0.0")
    private AdminUserService adminUserService;

    @Resource
    public BCryptPasswordEncoder passwordEncoder;

    @Test
    public void test_get_by_id() {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(15L);
        AdminUser testUser = adminUserMapper.selectOne(adminUser);
        assertEquals(testUser.getUsername(), "user10");
    }

    @Test
    public void test_get_by_username() {
        AdminUser admin = adminUserService.get("admin");
        assertEquals(admin.getUsername(), "admin");
        assertEquals(admin.getEmail(), "zzy123@cnec5.com");
    }

    @Test
    public void test_get_by_AdminUser() {
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername("admin");
        AdminUser admin2 = adminUserService.get(adminUser);
        assertEquals(admin2.getUsername(), "admin");
        assertEquals(admin2.getEmail(), "zzy123@cnec5.com");
    }

    @Test
    public void test_password_encoder() {
        System.out.println(passwordEncoder.encode("123456"));
    }

    @Test
    @Transactional
    @Rollback
    public void test_update_by_AdminUser() {
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername("admin");
        adminUser.setEmail("zzy@123.com");
        int result = adminUserMapper.updateByPrimaryKey(adminUser);
    }

//    @Test
//    public void test_select_userLists_by_example() {
//        String query = "";
//        int pageNum = 1;
//        int pageSize = 5;
//        List<AdminUser> list = adminUserService.list(query, pageNum, pageSize);
//        list.forEach(System.out::println);
//        System.out.println("========================");
//        query = "admin";
//        list = adminUserService.list(query, pageNum, pageSize);
//        list.forEach(System.out::println);
//        System.out.println("========================");
//        query = "测试账号";
//        list = adminUserService.list(query, pageNum, pageSize);
//        list.forEach(System.out::println);
//    }

    @Test
    public void test_total_AdminUser() {
        int total = adminUserService.total();
        assertEquals(total, 13);
    }

//    @Test
//    public void test_page_AdminUser() {
//        List<AdminUser> user1 = adminUserService.list("user", 1, 5);
//        PageInfo<AdminUser> pageInfo = new PageInfo<>(user1);
//        assertEquals(pageInfo.getPageNum(), 1);
//        assertEquals(pageInfo.getPageSize(), 5);
//        assertEquals(pageInfo.getTotal(), 10);
//    }

    @Test
    @Transactional
    @Rollback
    public void test_update_user_status() {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(15L);
        adminUserService.updateStatus(adminUser.getId(), 0);
        assertEquals(adminUserMapper.selectOne(adminUser).getStatus(), Integer.valueOf(0));
        adminUserService.updateStatus(adminUser.getId(), 1);
        assertEquals(adminUserMapper.selectOne(adminUser).getStatus(), Integer.valueOf(1));
    }

    @Test
    @Transactional
    @Rollback
    public void test_insert_user() {
        AddUserParams addUserParams = new AddUserParams();
        addUserParams.setUsername("Test01");
        addUserParams.setPassword("Test01");
        addUserParams.setEmail("Test01@cnec5.com");
        int result = adminUserService.insert(addUserParams);
        assertEquals(result, 1);
    }

    @Test
    public void test_get_user_by_id() {
        Long id = 15L;
        AdminUser adminUser = adminUserService.get(id);
        assertEquals(adminUser.getUsername(), "user10");
    }

    @Test
    @Transactional
    @Rollback
    public void test_delete_by_id() {
        Long id = 15L;
        int result = adminUserService.delete(id);
        assertEquals(result, 1);
    }
}
