package data;

public class Supplier extends BaseItem {
    private String supplierCode;
    private String supplierName;
    private String contactInfo;

    // Constructor
    public Supplier(String supplierCode, String supplierName, String contactInfo) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
    }

    public Supplier(String supplierCode, String supplierName, String contactInfo, String Id) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.contactInfo = contactInfo;
        this.Id = Id;
    }

    // Getters and Setters
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public void printLog(){
        System.out.println("Supplier loaded.");
    }  
}
