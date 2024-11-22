package com.devsuperior.dslist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplacementDTO {
    private Integer sourceIndex;
    private Integer destinationIndex;
}
