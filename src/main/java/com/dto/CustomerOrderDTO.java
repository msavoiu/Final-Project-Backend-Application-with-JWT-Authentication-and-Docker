package dto;

import java.time.LocalDate;
import java.util.List;

public class CustomerOrderDTO {
    private LocalDate datePlaced;
    private boolean readyForPickup;
    private List<Long> skateIds;
    private List<Long> bladeIds;
    private Long userId;

    // Getters and Setters
    public LocalDate getDatePlaced() { return datePlaced; }
    public void setDatePlaced(LocalDate datePlaced) { this.datePlaced = datePlaced; }

    public boolean isReadyForPickup() { return readyForPickup; }
    public void setReadyForPickup(boolean readyForPickup) { this.readyForPickup = readyForPickup; }

    public List<Long> getSkateIds() { return skateIds; }
    public void setSkateIds(List<Long> skateIds) { this.skateIds = skateIds; }

    public List<Long> getBladeIds() { return bladeIds; }
    public void setBladeIds(List<Long> bladeIds) { this.bladeIds = bladeIds; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}