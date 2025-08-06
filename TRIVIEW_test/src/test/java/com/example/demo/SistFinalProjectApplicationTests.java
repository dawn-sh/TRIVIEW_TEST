package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sist.last.dto.ReserveDto;
import sist.last.service.ReserveServiceInter;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SistFinalProjectApplicationTests {

	@Autowired
	private ReserveServiceInter reserveService;

	@Test
	void concurrentReservationTest() throws InterruptedException {
		int threadCount = 5; // 동시에 예약 시도할 사용자 수
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);

		for (int i = 0; i < threadCount; i++) {
			final int idx = i;
			executor.execute(() -> {
				try {
					ReserveDto dto = new ReserveDto();
					dto.setRoom_num(130);  // 테스트용 방 번호, 실제 DB에 있어야 함
					dto.setReserve_id("test_reserve_" + UUID.randomUUID()); // ✅ 고유한 reserve_id
					dto.setInfo_id("ssung2sin");
					dto.setReserve_name("TestUser" + idx);
					dto.setReserve_hp("010-0000-000" + idx);
					dto.setReserve_amount(10000);
					dto.setReserve_checkin("2025-09-01");
					dto.setReserve_checkout("2025-09-05");
					dto.setAccom_name("TestAccom");
					dto.setRoom_name("TestRoom");
					dto.setAccom_num(74);
					dto.setReserve_coupon("NONE");

					reserveService.reservingInsert(dto);
					System.out.println("예약 성공: " + dto.getReserve_id());
				} catch (Exception e) {
					System.out.println("예약 실패: " + e.getMessage());
				}
			});
		}

		executor.shutdown();
		executor.awaitTermination(30, TimeUnit.SECONDS);
	}
}
