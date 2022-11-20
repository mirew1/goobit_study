package com.gbs.app.nss.builder;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 클래스에 @Builder를 붙이기 보다는 생성자 레벨에 붙여서 Null 허용 등 의도대로 동작하게 만드는 것이 좋다고 한다.
 */
@ToString
@Getter
public class BuilderTest {

    private String name;

    private String gender = "남자";

    private String phone;
    private String age;

	// allBuilder 호출이 아닌 BuilderTest의 빌더를 호출하면 해달 메서드가 적용된다.
	@Builder
	public BuilderTest(String name, String age, String phone) {
		this.name = name;
		this.age = age;
		this.phone = phone;
	}

	/**
	 * @Builder에 builderClassName, builderMethodName 지정하지 않으면 @Builder를 2개를 써도 첫번째로 작성된 @Builder만 적용된다.
	 */
	@Builder(builderClassName = "allBuilder", builderMethodName = "allBuilder")
	public BuilderTest(String name, String gender, String phone, String age) {
		this.name = name;
		this.gender = gender;
		this.phone = phone;
		this.age = age;
	}

	// 필수로 입력받아야 하는 값을 생성자 파라미터를 통해 강제 할 수 있다.
	// 여기선 allBuilder를 이용하고 있기 때문에 gender, phone 미입력 시 Null)
	public static allBuilder allBuilderMustParam(String name, String age) {
		return allBuilder().name(name).age(age);
	}

	// 클래스에 @Builder를 작성하면 모든 필드값을 대상으로 하는 생성자가 빌더로 된다. (26라인처럼)
	// 이때 강제하고자 하는 값을 지정할 수 있다.
    public static BuilderTestBuilder mustNamePhone(String name, String phone) {
        return builder().name(name).phone(phone);
    }


}
