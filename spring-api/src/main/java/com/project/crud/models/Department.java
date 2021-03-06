package com.project.crud.models;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Document("departments")
@TypeAlias("departments")
@JsonIgnoreProperties(value = { "target" })
public class Department extends Base {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;
    private String description;
}