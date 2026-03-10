package org.example;

import java.util.Scanner;

public class Application {
    public static void print_menu(){
        System.out.println("1 - заполнить данными");
        System.out.println("2 - отсортировать");
        System.out.println("3 - что-нибудь еще");
        System.out.println("0 - выход");
    }
    public static void main(String[] args) {
        print_menu();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while(true) {
            if (n == 0) {
                break;
            } else if (n == 1) {
                System.out.println("Тут будет вызов функций");
            } else if (n == 2) {
                //.....
            } else {
                System.out.println("Некорректный ввод");
            }
            print_menu();
            n = scanner.nextInt();

        }
    }
}