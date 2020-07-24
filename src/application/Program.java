package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

    public static void main(final String[] args) {

        Scanner sc = new Scanner(System.in);
        //projects/89-exercicio-resolvido-stream/in.csv (diretorio do che.openshift o eclipse da internet)
        System.out.print("Enter full file path:");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            List<Product> list = new ArrayList<>();

            String line = br.readLine();
            while(line != null){
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            //transforma a lista em stream pega o preço e usa o reduce pra somar todos e divide pelo tamanho da lista
            //assim dando a media dos valores
            double avg = list.stream()
                             .map(p -> p.getPrice())
                             .reduce(0.0, (x,y) -> x + y) / list.size();

            System.out.println("Avarage price: " + String.format("%.2f", avg));

            //filtra os produtos menores que o avg, pega o nome, cricou um comparator aqui pra nao ficar muito
            //grande na parte do .sorted depois usa o comp.reverse(reverse é do proprio comparator) ele devolve
            //a string na ordem inversa

            Comparator<String> comp = (s1,s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> names = list.stream()
                                     .filter(p -> p.getPrice() < avg)
                                     .map(p -> p.getName())
                                     .sorted(comp.reversed())
                                     .collect(Collectors.toList());

            names.forEach(System.out::println);

        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}