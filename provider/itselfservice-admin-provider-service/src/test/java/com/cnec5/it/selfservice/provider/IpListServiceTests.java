package com.cnec5.it.selfservice.provider;

import com.cnec5.it.selfservice.dto.IpListUpdateParam;
import com.cnec5.it.selfservice.provider.api.IpListService;
import com.cnec5.it.selfservice.provider.domain.IpList;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IpListServiceTests {

    @Resource
    private IpListService ipListService;

    @Test
    public void test_get_iplist_by_swi_id() {
        Integer swiAddress = 450;
        List<IpList> zl001List = ipListService.getListBySwiAddress(swiAddress);
        zl001List.forEach(System.out::println);
    }

    @Test
    public void test_page() {
        String query1 = "2F-21";
        String query2 = "10.1.17";

        PageInfo<IpList> page1 = ipListService.page(query1, 1, 5);
        List<IpList> list1 = page1.getList();
        System.out.println(list1.toString());

        System.out.println("==============================");

        PageInfo<IpList> page2 = ipListService.page(query2, 1, 5);
        List<IpList> list2 = page2.getList();
        System.out.println(list2.toString());
    }

    @Test
    public void test_get_ip_by_id() {
        IpList id1 = ipListService.getById(1);
        assertEquals(id1.getPortid(), "1F-1");
    }

    @Transactional
    @Rollback
    @Test
    public void test_update_port_by_id() {
        IpListUpdateParam param = new IpListUpdateParam();
        param.setPortid("FF-FF");
        param.setIpv4("10.1.1.11");
        int result = ipListService.update(1, param);
        assertEquals(1, result);
    }

    @Transactional
    @Rollback
    @Test
    public void test_update_port_status() {
        int result = ipListService.updatePortStatus(7, 1);
        assertEquals(1, result);
    }

    @Transactional
    @Rollback
    @Test
    public void test_delete_port() {
        IpList list8 = ipListService.getById(8);
        int result = ipListService.delete(list8);
        assertEquals(1, result);
    }

    @Transactional
    @Rollback
    @Test
    public void test_delete_ip_all_by_example() {
        int result = ipListService.delete(450);
        assertEquals(8, result);
    }
}
