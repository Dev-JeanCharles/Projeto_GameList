package com.devjean.gamelist.application.web.dto;

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
