/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author Keith Lo Ze Hui
 */
public class InventoryManager extends Account {
    
    public InventoryManager(String username, String password) {
        super(username, password, Role.INVENTORY_MANAGER);
    }
    
}
