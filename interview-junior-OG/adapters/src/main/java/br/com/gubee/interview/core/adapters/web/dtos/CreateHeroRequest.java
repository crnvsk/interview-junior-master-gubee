package br.com.gubee.interview.core.adapters.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import br.com.gubee.interview.core.domain.enums.Race;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class CreateHeroRequest {

    @NotBlank(message = "Hero name is mandatory.")
    @Length(min = 1, max = 255, message = "Hero name must be between 1 and 255 characters.")
    private String name;

    @NotNull(message = "Hero race is mandatory.")
    private Race race;

    @Min(value = 0, message = "Strength must be at least 0.")
    @Max(value = 10, message = "Strength must be at most 10.")
    @NotNull(message = "Strength is mandatory.")
    private Integer strength;

    @Min(value = 0, message = "Agility must be at least 0.")
    @Max(value = 10, message = "Agility must be at most 10.")
    @NotNull(message = "Agility is mandatory.")
    private Integer agility;

    @Min(value = 0, message = "Dexterity must be at least 0.")
    @Max(value = 10, message = "Dexterity must be at most 10.")
    @NotNull(message = "Dexterity is mandatory.")
    private Integer dexterity;

    @Min(value = 0, message = "Intelligence must be at least 0.")
    @Max(value = 10, message = "Intelligence must be at most 10.")
    @NotNull(message = "Intelligence is mandatory.")
    private Integer intelligence;
}