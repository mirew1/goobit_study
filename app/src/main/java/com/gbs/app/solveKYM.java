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
        
    	System.out.println("1. 2011�⿡ �Ͼ ��� Ʈ������� ã�� ���� ������������ �����Ͻÿ�.");
    	transactions.stream().filter(t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).forEach(t -> System.out.println(t.getValue()));
    	// filter : ���ǿ� �´� �����͸� ����
    	// sorted : ���� ���ĺ� �� ����? �ȿ� ���빰�� ��ü�̸� Comparator.comparing�� ����Ͽ� ��ü::�Լ��� �� ���� ���·� ����� �� �ִ�? ex)Comparator.comparing(String::length)
    	// ����� ���ؼ��� ���������ڷ� forEach(x -> sysout)ó�� �� �� ����.
    	// ���ͷ� year�� 2011�ΰ͸� �����ͼ� sorted�� ���� ���� ������������ ����  
    	System.out.println("\n2. �ŷ��ڰ� �ٹ��ϴ� ��� ���ø� �ߺ����� �����Ͻÿ�.");
    	transactions.stream().map(t -> t.getTrader().getCity()).distinct().forEach(t -> System.out.println(t));
    	// map : ��Ʈ���� ��Ҹ� ��ȯ�Ѵ�?
    	// distinct : �ߺ� ����
    	// map���� ���ø��� �����ͼ� distinct�� ���� �ߺ��� ����
    	System.out.println("\n3. ���Ӻ긴������ �ٹ��ϴ� ��� �ŷ��ڸ� ã�Ƽ� �̸������� �����Ͻÿ�.");
    	transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).map(t -> t.getTrader().getName()).distinct().sorted().forEach(t -> System.out.println(t));
    	// filter�� ���Ӻ긴������ �ٹ��ϴ� Ʈ������� ã�� map���� ���߿� �̸��� �������� distinct�� �ߺ��������ϰ� sorted�� �������� ���� 
    	System.out.println("\n4. ��� �ŷ����� �̸��� ���ĺ������� �����ؼ� ��ȯ�Ͻÿ�.");
    	transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted().forEach(t -> System.out.println(t));
    	// map���� �̸��� �����ͼ� distinct�� �ߺ����� �� sorted�� �������� ����
    	System.out.println("\n5. �ж�뿡 �ŷ��ڰ� �ִ°�?");
    	if(transactions.stream().filter(t -> t.getTrader().getCity().equals("Milan")).count() > 0 ) System.out.println("�ж�뿡 �ŷ��ڰ� �ִ�.");
    	else System.out.println("�ж�뿡 �ŷ��ڰ� ����.");
    	System.out.println(transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan")));
    	// count : ����Ʈ�� ������ ��ȯ?
    	// anyMatch ��Ī�Ǵ°� �ִ��� Ȯ��?
    	System.out.println("\n6. ���Ӻ긮������ �����ϴ� �ŷ����� ��� Ʈ����ǰ��� ����Ͻÿ�.");
    	transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge")).forEach(t -> System.out.println(t.getValue()));
    	// filter�� ���Ӻ긴���� ��� ����� ã�Ƽ� ��ȸ
    	System.out.println("\n7. ��ü Ʈ����� �� �ִ��� ���ΰ�?");
    	System.out.println(transactions.stream().mapToInt(t -> t.getValue()).max().getAsInt());
    	// mapToInt : map�̶� ���� ��Ʈ���� ��Ҹ� ��ȯ�ϴµ� int�� ��ȯ�Ѵ�?
    	// max �ִ밪 ���ϱ�
    	System.out.println("\n8. ��ü Ʈ����� �� �ּڰ��� ���ΰ�?");
    	System.out.println(transactions.stream().mapToInt(t -> t.getValue()).min().getAsInt());
    	// min �ּҰ� ���ϱ�
    }

    public static void main(String[] args) {
    	
    }
}