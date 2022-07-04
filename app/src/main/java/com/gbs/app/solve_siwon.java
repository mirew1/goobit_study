package com.gbs.app;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class solve_siwon {

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
    	transactions.stream().filter(t->t.getYear()==2011).sorted(Comparator.comparing(Transaction::getValue)).forEach(System.out::println);
    	System.out.println("\n2. 거래자가 근무하는 모든 도시를 중복없이 나열하시오.");
    	transactions.stream().map(t->t.getTrader().getCity()).distinct().forEach(System.out::println);
    	System.out.println("\n3. 케임브릿지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.");
    	transactions.stream().filter(t->t.getTrader().getCity().equals("Cambridge")).map(t->t.getTrader().getName()).distinct().sorted().forEach(System.out::println);
    	// System.out.println("또는");
    	// transactions.stream().filter(t->t.getTrader().getCity().equals("Cambridge")).map(t->t.getTrader()).distinct().sorted(Comparator.comparing(Trader::getName)).forEach(System.out::println);
    	System.out.println("\n4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.");
    	transactions.stream().map(t->t.getTrader().getName()).distinct().sorted().forEach(System.out::println);
    	System.out.println("\n5. 밀라노에 거래자가 있는가?");
    	System.out.println(transactions.stream().anyMatch(t->t.getTrader().getCity().equals("Milan")));
    	System.out.println("\n6. 케임브리지에서 거주하는 거래자의 모든 트랜잭션값을 출력하시오.");
    	transactions.stream().filter(t->t.getTrader().getCity().equals("Cambridge")).forEach(System.out::println);
    	System.out.println("\n7. 전체 트랜잭션 중 최댓값은 얼마인가?");
    	System.out.println(transactions.stream().max(Comparator.comparing(Transaction::getValue)).get().getValue());
    	System.out.println("\n8. 전체 트랜잭션 중 최솟값은 얼마인가?");
    	System.out.println(transactions.stream().min(Comparator.comparing(Transaction::getValue)).get().getValue());
    	System.out.println("\n9. 케임브리지에서 일어난 트랜잭션을 거래자의 이름순(오름차순), 년도순(오름차순)으로 정렬하시오.");
    	transactions.stream().filter(t->t.getTrader().getCity().equals("Cambridge")).sorted(Comparator.comparing(Transaction::getYear)).sorted(Comparator.comparing(t -> t.getTrader().getName())).forEach(System.out::println);
    }
    public static void main(String[] args) {
    	
    }
    
    public void nsmsApiTest() {
    	String timestamp = Long.toString(System.currentTimeMillis());  // 현재 타임스탬프 (epoch, millisecond)
        String accessKey = "";
        String secretKey = "";
        
        String message = new StringBuilder()
                .append("GET")
                .append(" ")
//                .append("/api/v1/mails/requests")
                .append("/api/v1/mails/requests/20220629000000808401/mails")
                .append("\n")
                .append(timestamp)
                .append("\n")
                .append(accessKey)
                .toString();
        try {
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = java.util.Base64.getEncoder().encodeToString(rawHmac);

        System.out.println("x-ncp-apigw-timestamp : "+ timestamp);
        System.out.println("x-ncp-iam-access-key : "+ accessKey);
        System.out.println("x-ncp-apigw-signature-v2 : "+ encodeBase64String);
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
}
