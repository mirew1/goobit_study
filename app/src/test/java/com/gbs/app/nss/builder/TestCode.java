package com.gbs.app.nss.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCode {

    @Test
	@DisplayName("빌드 생성 시 필수 값을 생성자 파라미터로 강제할 수 있다.")
	void builderTest() {
		String name = "aaa";
		String age = "22";
		BuilderTest build1 = BuilderTest.mustNamePhone("홍길동", "남").build();
		BuilderTest build2 = BuilderTest.mustNamePhone("홍길동", "남").age("14").build();

		System.out.println("build1 :: " +build1.toString());
		System.out.println("build2 :: " +build2.toString());

		BuilderTest build = BuilderTest.builder().age(age).name(name).build();
		System.out.println("build :: " + build.toString());

	}

	@Test
	@DisplayName("빌더 항목 미설정 시 기본값 설정 시 별도로 생성한 빌더는 디폴트값이 적용되지 않는다.")
	void builderDefaultValueTest() {
		String name = "aaa";
		String age = "22";
		BuilderTest build1 = BuilderTest.mustNamePhone("홍길동", "010-1234-1234").build();
		BuilderTest build2 = BuilderTest.mustNamePhone("홍길동", "010-7777-8888").age("14").build();
		BuilderTest build3 = BuilderTest.allBuilder().name("홍길동").phone("12312312312").build();

		System.out.println("build1 :: " +build1.toString());
		System.out.println("build2 :: " +build2.toString());
		System.out.println("build3 :: " +build3.toString());


	}

}
