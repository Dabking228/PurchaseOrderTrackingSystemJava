package data;

import java.util.*;

public enum Role {
    // enum items
    SALES_MANAGER(buildSalesManagerPermissions(),
            List.of("CreateSale", "salesReport", "restockItem", "purReq", "PurOrd")),
    PURCHASE_MANAGER(buildPurchaseManagerPermissions(), List.of("purReq")),
    INVENTORY_MANAGER(buildInventoryManagerPermissions(), List.of("addItem" )),
    FINANCE_MANAGER(buildFinanceManagerPermissions(), List.of("trackPurchaseOrder", "PurOrd")),
    ADMIN(buildAdminPermissions(),
            List.of("addItem","addUser","TrackPurchaseOrder", "CreateSale", "salesReport", "restockItem", "purReq", "PurOrd"));

    // fields for permissions and special features
    private final Map<String, EnumSet<Permission>> permissions;
    private final List<String> specialFeatures;

    Role(Map<String, EnumSet<Permission>> permissions, List<String> specialFeatures) {
        this.permissions = permissions;
        this.specialFeatures = specialFeatures;
    }

    public boolean hasPermission(String tableName, Permission permission) {
        return permissions.getOrDefault(tableName, EnumSet.noneOf(Permission.class)).contains(permission);
    }

    public boolean hasFeature(String feature) {
        return specialFeatures.contains(feature);
    }

    // add permissions for each role
    private static Map<String, EnumSet<Permission>> buildSalesManagerPermissions() {
        Map<String, EnumSet<Permission>> permissions = new HashMap<>();
        permissions.put("Items", EnumSet.of(Permission.READ));
        permissions.put("Suppliers", EnumSet.of(Permission.READ));
        permissions.put("Sales", EnumSet.of(Permission.READ));
        permissions.put("PurchaseRequisition", EnumSet.of(Permission.CREATE, Permission.READ));
        permissions.put("PurchaseOrder", EnumSet.of(Permission.READ));
        return permissions;
    }

    private static Map<String, EnumSet<Permission>> buildPurchaseManagerPermissions() {
        Map<String, EnumSet<Permission>> permissions = new HashMap<>();
        permissions.put("Items", EnumSet.of(Permission.READ));
        permissions.put("Suppliers", EnumSet.of(Permission.READ));
        permissions.put("PurchaseRequisition", EnumSet.of(Permission.CREATE, Permission.READ));
        permissions.put("PurchaseOrder", EnumSet.allOf(Permission.class));
        return permissions;
    }

    private static Map<String, EnumSet<Permission>> buildInventoryManagerPermissions() {
        Map<String, EnumSet<Permission>> permissions = new HashMap<>();
        permissions.put("Items", EnumSet.allOf(Permission.class));
        permissions.put("ItemStock", EnumSet.of(Permission.READ, Permission.UPDATE));
        permissions.put("Suppliers", EnumSet.allOf(Permission.class));
        return permissions;
    }

    private static Map<String, EnumSet<Permission>> buildFinanceManagerPermissions() {
        Map<String, EnumSet<Permission>> permissions = new HashMap<>();
        // permissions.put("Items", EnumSet.of(Permission.READ));
        permissions.put("PurchaseOrder", EnumSet.of(Permission.READ, Permission.UPDATE));
        return permissions;
    }

    private static Map<String, EnumSet<Permission>> buildAdminPermissions() {
        Map<String, EnumSet<Permission>> permissions = new HashMap<>();
        permissions.put("Items", EnumSet.allOf(Permission.class));
        permissions.put("Suppliers", EnumSet.allOf(Permission.class));
        permissions.put("Sales", EnumSet.allOf(Permission.class));
        permissions.put("PurchaseRequisition", EnumSet.allOf(Permission.class));
        permissions.put("PurchaseOrder", EnumSet.allOf(Permission.class));
        permissions.put("Accounts", EnumSet.allOf(Permission.class));
        return permissions;
    }
}
