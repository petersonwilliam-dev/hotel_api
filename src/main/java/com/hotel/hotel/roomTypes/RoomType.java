package com.hotel.hotel.roomTypes;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "RoomType")
@Table(name = "room_type")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;
    private BigDecimal basePrice;
    private String bedConfig;
    private String amenities;

    @Enumerated(EnumType.STRING)
    private Category category;

    public RoomType(RoomTypeSaveDTO data) {
        this.name = data.name();
        this.capacity = data.capacity();
        this.basePrice = data.basePrice();
        this.bedConfig = data.bedConfig();
        this.amenities = data.amenities();
        this.category = data.category();
    }
}
