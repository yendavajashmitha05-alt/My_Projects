package com.ShoppingCartProject;

import java.util.LinkedList;

public class ShoppingCart {

    LinkedList<Item> cart = new LinkedList<>();

    // Add item
    public void addItem(Item item) {
        cart.add(item);
        System.out.println("Item added successfully!");
    }

    // Remove item
    public void removeItem(int id) {
        boolean found = false;

        for (Item item : cart) {
            if (item.getId() == id) {
                cart.remove(item);
                System.out.println("Item removed successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Item not found!");
        }
    }

    // Update quantity
    public void updateQuantity(int id, int quantity) {
        for (Item item : cart) {
            if (item.getId() == id) {
                item.setQuantity(quantity);
                System.out.println("Quantity updated successfully!");
                return;
            }
        }

        System.out.println("Item not found!");
    }

    // Display cart
    public void displayCart() {

        if (cart.isEmpty()) {
            System.out.println("Shopping cart is empty!");
            return;
        }

        System.out.println("\nID\tName\tPrice\tQuantity\tTotal");

        for (Item item : cart) {
            item.displayItem();
        }
    }

    // Calculate total bill
    public void calculateTotal() {

        double total = 0;

        for (Item item : cart) {
            total += item.getTotalPrice();
        }

        System.out.println("Total Bill: Rs." + total);
    }

    // Clear cart
    public void clearCart() {
        cart.clear();
        System.out.println("Shopping cart cleared!");
    }
}
