package com.marcusbike.marcus_bike_api.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Builder
@Table
public class ProductPart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String partOption;
	
	@Column(nullable = false)
	@Builder.Default
	private Double stock = 0.0;
	
	@Column(nullable = false)
	private Double basePrice;
	
	@Column(nullable = false)
	private ProductPartCategory category;
	
	@Column(nullable = false)
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "productPart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPartAssociation> productPartAssociations;
}
