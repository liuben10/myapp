package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by benjaminliu on 7/7/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonData {
	@Getter
	@Setter
	private String name;
}
