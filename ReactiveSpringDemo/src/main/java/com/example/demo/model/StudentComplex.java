package com.example.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
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
public class StudentComplex{
	@Id
	private int rollNo;
	private String department;
	private String year;
	private List<Integer> marks;

}
