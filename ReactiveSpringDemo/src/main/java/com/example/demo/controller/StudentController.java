package com.example.demo.controller;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.StudentRepository;
import com.example.demo.Service.StudentService;
import com.example.demo.error.CustomException;
import com.example.demo.model.Student;
import com.example.demo.model.StudentComplex;
import com.example.demo.model.StudentData;
import com.example.demo.model.StudentEvent;
import com.example.demo.model.StudentHash;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/student")
public class StudentController{
	@Autowired
	StudentService service;

	Student student1;	
	
	@GetMapping("/viewall")
	public Flux<Student> viewAll() {
		return service.viewAll();
	}

	@GetMapping("/getRollNo/{roll}")
	public Mono<Student> getByRoll(@PathVariable("roll") int roll) {
		Student student2=new Student();
		student2.setRollNo(15);
		student2.setName("Harshit");
		student2.setDepartment("Electronics");
		student2.setYear("fifth");
		
		return Mono.<Student>error(NullPointerException::new)
		        .onErrorResume(e -> Mono.just(student2));
	}
	@GetMapping("/getRollNoresume/{roll}")
	public Mono<String> getByRollResume(@PathVariable("roll") int roll) {
//		Student student2=new Student();
//		student2.setRollNo(15);
//		student2.setName("Subham");
//		student2.setDepartment("Electronics");
//		student2.setYear("fifth");
		
		return Mono.<String>error(NullPointerException::new)
		        .onErrorResume(NullPointerException.class, /*e -> Mono.just(student2)*/e->Mono.just("Sorry! Roll no not exist: "+e.getMessage()));
	}
	@GetMapping("/getRollNoresumefallback/{roll}")
	public Mono<String> getByRollResumeFallbackHandler(@PathVariable("roll") int roll) {
//		Student student2=new Student();
//		student2.setRollNo(15);
//		student2.setName("Subham");
//		student2.setDepartment("Electronics");
//		student2.setYear("fifth");
		
		return Mono.<String>error(NullPointerException::new)
		        .onErrorResume(e->e instanceof NullPointerException,e->Mono.just("Roll no does not exist: "+e.getMessage()));
	}
	
	@PostMapping("/complex")
	public Mono<String> Studentcomplex(@RequestBody StudentHash student)
	{
		System.out.println("Inside "+student.get("rollNo"));
		return Mono.just("OK");
	}
	
	@PostMapping("/saveComplex")
	public Mono<StudentComplex> saveComplex(@RequestBody StudentComplex student) {
		return service.complexSave(student);
	}
	
	@GetMapping
	  public Mono<String> helloWorld() {
	    return Mono.<String>error(IOException::new)
	        .onErrorResume(e -> Mono.just("Error : " + e.getMessage()));
	  }
	
	@PostMapping("/save")
	public Mono save(@RequestBody StudentData student) {
		student1 = new Student();
//		if(student.getRollNo())
//			throw new CustomException();
		
		student1.setRollNo(student.getRollNo());
		student1.setName(student.getName());
		student1.setDepartment(student.getDepartment());
		student1.setYear(student.getYear());
		System.out.println(student.getName());
		return service.save(student1);
		
	}

	@PutMapping("/updateComplex")
	public Mono<StudentComplex> update(@RequestBody StudentComplex student) {
		return service.updateComplex(student);
	}
	
	@PutMapping("/update")
	public Mono<Student> update(@RequestBody StudentData student) {
		student1 = new Student();
		student1.setRollNo(student.getRollNo());
		student1.setName(student.getName());
		student1.setDepartment(student.getDepartment());
		student1.setYear(student.getYear());
		return service.update(student1);
	}

	@GetMapping(value = "/{roll}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<StudentEvent> getEvents(@PathVariable("roll") final int roll) {
		return service.getEvents(roll);

	}

}
