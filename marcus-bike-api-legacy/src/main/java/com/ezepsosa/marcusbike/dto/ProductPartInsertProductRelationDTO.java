package com.ezepsosa.marcusbike.dto;

import java.util.List;

public record ProductPartInsertProductRelationDTO(Long productId, List<Long> productPartsId) {

}
