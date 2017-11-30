package com.enlink.auth.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.enlink.auth.dao.ApplicationTest;
import com.enlink.auth.dao.entity.TlClient;
import com.enlink.auth.dao.service.ITlClientService;

/**
 * Created by someone on 2017/9/23.
 */
@SpringBootTest(classes = ApplicationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("h2")
public class ClientServiceTest {

	@Autowired
	ITlClientService clientService;

	@Test
	public void testGetAllClients() {
		List<TlClient> list = clientService.selectList(null);
		System.out.println("size===" + list.size());
	}
}
