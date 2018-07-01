package com.target.api.busstop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TextAndValue
{
   @JsonProperty("Text")
   private String text;
   @JsonProperty("Value")
   private String value;
}
