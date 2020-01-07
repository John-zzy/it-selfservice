package com.cnec5.it.selfservice.provider;

import com.cnec5.it.selfservice.base.BaseSwichDto;
import com.cnec5.it.selfservice.provider.api.SwiListService;
import com.cnec5.it.selfservice.provider.domain.SwiList;
import com.cnec5.it.selfservice.provider.mapper.SwiListMapper;
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
public class SwiListServiceTests {

    @Resource
    public SwiListMapper swiListMapper;

    @Resource
    public SwiListService swiListService;

    @Test
    public void test_select_all_swiList() {
        List<SwiList> swiLists = swiListMapper.selectAll();
        swiLists.forEach(System.out::println);
    }

    @Test
    public void test_select_swilist_parent() {
        List<SwiList> swiLists = swiListService.selectSwiLocation();
        swiLists.forEach(System.out::println);
    }

    @Transactional
    @Rollback
    @Test
    public void test_insert_swich() {
        SwiList swiList = new SwiList();
        swiList.setParentId(448);
        swiList.setSwiLocation("新办公楼");
        swiList.setSwiIpv4("10.0.0.0");
        swiList.setSwiName("ZLTEST01");
        swiList.setSwiComment("test");
        int result = swiListService.insert(swiList);
        assertEquals(result, 1);
    }

    @Test
    public void test_for_radio() {
        int radio1 = 48;
        int radio2 = 24;
        for (int i = 1; i <= radio2; i++) {
            System.out.println(i);
        }
    }

    @Test
    public void test_get_by_swiIpv4() {
        String ipv4 = "10.1.0.211";  // FL11
        SwiList swiList = swiListService.get(ipv4);
        assertEquals(swiList.getSwiName(), "FL11");
    }

    @Test
    public void test_swichinfo_get_by_id() {
        int id = 478;
        SwiList swiList = swiListService.get(id);
        assertEquals(swiList.getSwiLocation(), "辅楼四楼");
    }

    @Transactional
    @Rollback
    @Test
    public void test_update_switch_detail_by_id() {
        BaseSwichDto dto = new BaseSwichDto();
        dto.setSwiName("Test name");
        dto.setSwiIpv4("0.0.0.test");
        dto.setSwiLocation("location");
        dto.setSwiComment("test comment");
        Integer id = 468;
        int result = swiListService.update(id, dto);
        assertEquals(result, 1);
    }

    @Transactional
    @Rollback
    @Test
    public void test_delete_by_SwiList() {
        SwiList swiList = new SwiList();
        swiList.setId(468);
        int result = swiListService.delete(swiList);
        assertEquals(result, 1);
    }
}
