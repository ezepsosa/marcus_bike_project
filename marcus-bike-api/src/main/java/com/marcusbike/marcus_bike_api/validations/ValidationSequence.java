package com.marcusbike.marcus_bike_api.validations;

import jakarta.validation.GroupSequence;

@GroupSequence({ Step1.class, Step2.class, Step3.class })
public interface ValidationSequence {
}
