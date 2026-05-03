package dto;

public class CustomerBackorderDTO {
    private Long customerId;
    private Long backorderId;

    // Getters and Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getBackorderId() { return backorderId; }
    public void setBackorderId(Long backorderId) { this.backorderId = backorderId; }
}