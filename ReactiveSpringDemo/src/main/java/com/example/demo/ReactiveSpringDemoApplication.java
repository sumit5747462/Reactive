package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.Repository.StudentComplexRepo;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.model.Student;
import com.example.demo.model.StudentComplex;

@SpringBootApplication
public class ReactiveSpringDemoApplication {
    @Bean
	CommandLineRunner students(StudentRepository repository)
	{
		return args-> {
			repository.deleteAll()
			.subscribe(null,null,()->
			{
				Stream.of(new Student(1,"Raj","Computer Science","Third"),
						new Student(2,"Suman","Electrical","Second"),
						new Student(3,"Bobby","Electronics and Communication","Third"),
						new Student(4,"Suraj","Mechanical","Fourth")
						).forEach(student->
						repository
						.save(student)
						.subscribe(System.out::println));
			}
			);
		};
	}
    
    @Bean
	CommandLineRunner studentComplex(StudentComplexRepo repository)
	{
		return args-> {
			repository.deleteAll()
			.subscribe(null,null,()->
			{
				Stream.of(new StudentComplex(1,"Computer Science","Third",new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6))),
						new StudentComplex(2,"Electrical","Fourth",new ArrayList<Integer>(Arrays.asList(60, 29, 35, 47, 59, 68))),
						new StudentComplex(4,"Civil","Second",new ArrayList<Integer>(Arrays.asList(15, 28, 37, 43, 57, 67))),
						new StudentComplex(3,"Mechanical","First",new ArrayList<Integer>(Arrays.asList(11, 22, 38, 95, 87, 90)))
						).forEach(student->
						repository
						.save(student)
						.subscribe(System.out::println));
			}
			);
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(ReactiveSpringDemoApplication.class, args);
	}

}
