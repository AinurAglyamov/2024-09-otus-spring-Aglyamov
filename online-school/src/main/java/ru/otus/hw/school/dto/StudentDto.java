package ru.otus.hw.school.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class StudentDto {
    private UUID id;

    private String firstName;

    private String lastName;

    private String country;

    private String email;

    private String phoneNumber;

    private List<GroupDto> groups;
}
