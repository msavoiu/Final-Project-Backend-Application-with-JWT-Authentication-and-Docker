package dto;

import java.time.LocalDate;
import java.util.List;

public class BackorderDTO {
    private LocalDate datePlaced;
    private Boolean received;
    private Long brandId;
    private List<Long> skateIds;
    private List<Long> bladeIds;

    // Getters and Setters
    public LocalDate getDatePlaced() { return datePlaced; }
    public void setDatePlaced(LocalDate datePlaced) { this.datePlaced = datePlaced; }

    public Boolean getReceived() { return received; }
    public void setReceived(Boolean received) { this.received = received; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public List<Long> getSkateIds() { return skateIds; }
    public void setSkateIds(List<Long> skateIds) { this.skateIds = skateIds; }

    public List<Long> getBladeIds() { return bladeIds; }
    public void setBladeIds(List<Long> bladeIds) { this.bladeIds = bladeIds; }
}