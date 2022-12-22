package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            List<Sale> sales = new ArrayList<>();
            String line = br.readLine();
            while (line != null){
                String[] filds = line.split(",");
                Integer month = Integer.parseInt(filds[0]);
                Integer year = Integer.parseInt(filds[1]);
                String seller = filds[2];
                Integer items = Integer.parseInt(filds[3]);
                Double total = Double.parseDouble(filds[4]);
                sales.add(new Sale(month,year,seller,items,total));

                line = br.readLine();
            }
            System.out.println();
            System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
            Comparator<Sale> com = (d1, d2) -> d1.averagePrice().compareTo(d2.averagePrice());
            List<Sale> list = sales.stream().filter(p -> p.getYear() == 2016)
                    .sorted(com.reversed()).limit(5).collect(Collectors.toList());
            list.forEach(System.out::println);
            System.out.println();
            Double sum = sales.stream()
                    .filter(p -> p.getSeller().equals("Logan"))
                    .filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
                    .map(p -> p.getTotal()).reduce(0.0, (x, y) -> x + y);

            System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = "
                    + String.format("%.2f", sum));

        }catch (IOException e){
            System.out.println("Erro: " + e.getMessage());
        }

        sc.close();
    }
}
