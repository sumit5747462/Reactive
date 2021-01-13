package com.example.demo.model;

import java.util.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Student {
	@Id
	private int rollNo;
	@Indexed(unique=true)
	private String name;
	private String department;
	private String year;

}
