package com.ShoppingCartProject;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();
        int choice;
        do {

            System.out.println("\n===== SHOPPING CART MANAGEMENT SYSTEM =====");

            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Quantity");
            System.out.println("4. Display Cart");
            System.out.println("5. Calculate Total Bill");
            System.out.println("6. Clear Cart");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter Item ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Item Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();

                    if (id <= 0 || price < 0 || quantity <= 0) {
                        System.out.println("Invalid item details!");
                        break;
                    }

                    Item item = new Item(id, name, price, quantity);

                    cart.addItem(item);

                    break;

                case 2:

                    System.out.print("Enter Item ID to remove: ");
                    int removeId = sc.nextInt();

                    cart.removeItem(removeId);

                    break;

                case 3:

                    System.out.print("Enter Item ID: ");
                    int updateId = sc.nextInt();

                    System.out.print("Enter New Quantity: ");
                    int newQuantity = sc.nextInt();

                    if (newQuantity <= 0) {
                        System.out.println("Quantity must be greater than zero!");
                        break;
                    }

                    cart.updateQuantity(updateId, newQuantity);

                    break;

                case 4:

                    cart.displayCart();

                    break;

                case 5:

                    cart.calculateTotal();

                    break;

                case 6:

                    cart.clearCart();

                    break;

                case 7:

                    System.out.println("Thank you for shopping!");

                    break;

                default:

                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 7);


    }
}
