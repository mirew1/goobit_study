package com.gbs.app;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@SpringBootTest
class AppApplicationTests {
	
	class NotAtomicTest {
		int cnt = 0;
		public int increaseCnt() {
			++cnt;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return cnt;
		}
	}
	
	class AtomicTest {
		AtomicInteger cnt = new AtomicInteger(1);
		public int increaseCnt() {
			int result = cnt.getAndIncrement();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return result; 
		}
	}
	
	@Test
	void contextLoads() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("1234"));
	}
	
//	@Test
	@DisplayName("멀티스레드에서 int 증가 시 누락되는 번호가 있다.")
	void notAtomicTest() throws InterruptedException {
		final int CNT = 20;
		NotAtomicTest t1 = new NotAtomicTest();
		CountDownLatch latch = new CountDownLatch(CNT);
		
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		for (int i = 0; i < CNT; i++) {
			executorService.submit(()-> {
				log.info("now Thread :: {} / now Cnt :: {}", Thread.currentThread().getName(), t1.increaseCnt());
				latch.countDown();
			});
		}
		
		latch.await();
		System.out.println("==================== END ====================");
	}
	
//	@Test
	@DisplayName("멀티스레드에서 int 증가 시 누락되는 번호는 없다.")
	void atomicTest() throws InterruptedException {
		final int CNT = 20;
		AtomicTest t1 = new AtomicTest();
		CountDownLatch latch = new CountDownLatch(CNT);
		
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		for (int i = 0; i < CNT; i++) {
			executorService.submit(()-> {
				log.info("now Thread :: {} / now Cnt :: {}", Thread.currentThread().getName(), t1.increaseCnt());
				latch.countDown();
			});
		}
		
		latch.await();
		System.out.println("==================== END ====================");
	}
}
