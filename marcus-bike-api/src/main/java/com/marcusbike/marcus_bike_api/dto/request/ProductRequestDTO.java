package com.marcusbike.marcus_bike_api.dto.request;

import org.hibernate.validator.constraints.URL;

import com.marcusbike.marcus_bike_api.validations.Step1;
import com.marcusbike.marcus_bike_api.validations.Step2;
import com.marcusbike.marcus_bike_api.validations.Step3;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductRequestDTO {

	@NotBlank(message = "Product name field is mandatory", groups = Step1.class)
	@Size(min = 2, max = 254, message = "Product name field can't have more than 254 characters or less than 2 characters", groups = Step2.class)
	private String productName;
	
	@NotBlank(message = "Brand field is mandatory", groups = Step1.class)
	@Size(min = 2, max = 100, message = "Brand field can't have more than 100 characters or less than 2 characters", groups = Step2.class)
	private String brand;

	@NotBlank(message = "Category field is mandatory", groups = Step1.class)
	@Size(min = 2, max = 50, message = "Product field can't have more than 50 characters or less than 2 characters", groups = Step2.class)
	private String category;

	@NotBlank(message = "Material field is mandatory", groups = Step1.class)
	@Size(min = 2, max = 100, message = "Product field can't have more than 100 characters or less than 2 characters", groups = Step2.class)
	private String material;

	@NotBlank(message = "Product field is mandatory", groups = Step1.class)
	@URL(message = "Image url is not valid", groups = Step3.class)
	private String imageUrl;
}
