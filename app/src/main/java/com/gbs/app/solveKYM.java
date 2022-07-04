package com.gbs.app;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class solveKYM {
    static {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        
    	System.out.println("1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.");
    	transactions.stream().filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).forEach(t -> System.out.println(t.getValue()));
    	// filter : 조건에 맞는 데이터만 추출
    	// sorted : 정렬 알파벳 순 정렬? 안에 내용물이 객체이면 Comparator.comparing을 사용하여 객체::함수명 와 같은 형태로 사용할 수 있다? ex)Comparator.comparing(String::length)
    	// 출력을 위해서는 최종연산자로 forEach(x -> sysout)처리 할 수 있음.
    	// 필터로 year가 2011인것만 가져와서 sorted를 통해 값을 오름차순으로 정렬  
    	System.out.println("\n2. 거래자가 근무하는 모든 도시를 중복없이 나열하시오.");
    	transactions.stream().map(t -> t.getTrader().getCity()).distinct().forEach(t -> System.out.println(t));
    	// map : 스트림의 요소를 변환한다?
    	// distinct : 중복 제거
    	// map으로 도시명을 가져와서 distinct를 통해 중복을 제거
    	System.out.println("\n3. 케임브릿지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.");
    	transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).map(t -> t.getTrader().getName()).distinct().sorted().forEach(t -> System.out.println(t));
    	// filter로 케임브릿지에서 근무하는 트랜잭션을 찾아 map으로 그중에 이름을 가져오고 distinct로 중복을제거하고 sorted로 오름차순 정렬 
    	System.out.println("\n4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.");
    	transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().forEach(t -> System.out.println(t));
    	// map으로 이름을 가져와서 distinct로 중복제거 후 sorted로 오름차순 정렬
    	System.out.println("\n5. 밀라노에 거래자가 있는가?");
    	if(transactions.stream().filter(t -> t.getTrader().getCity().equals("Milan")).count() > 0 ) System.out.println("밀라노에 거래자가 있다.");
    	else System.out.println("밀라노에 거래자가 없다.");
    	System.out.println(transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan")));
    	// count : 리스트의 개수를 반환?
    	// anyMatch 매칭되는게 있는지 확인?
    	System.out.println("\n6. 케임브리지에서 거주하는 거래자의 모든 트랜잭션값을 출력하시오.");
    	transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).forEach(t -> System.out.println(t.getValue()));
    	// filter로 케임브릿지에 사는 사람만 찾아서 조회
    	System.out.println("\n7. 전체 트랜잭션 중 최댓값은 얼마인가?");
    	System.out.println(transactions.stream().mapToInt(t -> t.getValue()).max().getAsInt());
    	// mapToInt : map이랑 같이 스트림의 요소를 반환하는데 int로 반환한다?
    	// max 최대값 구하기
    	System.out.println("\n8. 전체 트랜잭션 중 최솟값은 얼마인가?");
    	System.out.println(transactions.stream().mapToInt(t -> t.getValue()).min().getAsInt());
    	// min 최소값 구 하기   
    }

    public static void main(String[] args) {
    	
    }
}