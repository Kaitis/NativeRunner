package com.ak.native_runner_admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.ak.native_runner_admin.domain.ProcessGroup;
import com.ak.native_runner_admin.repository.ProcessGroupRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessLauncherAdminApplicationTests {

	@Autowired
	private ProcessGroupRepository groupRepository;

	@Test
	public void contextLoads() {
		ProcessGroup processGroup = groupRepository.findById(4L).get();

		System.out.println(processGroup);
	}


}

