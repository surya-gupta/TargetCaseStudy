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

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }
}
