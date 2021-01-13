package com.example.demo.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Repository.StudentComplexRepo;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.model.Student;
import com.example.demo.model.StudentComplex;
import com.example.demo.model.StudentEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

	@Autowired
	StudentRepository repository;
	@Autowired
	StudentComplexRepo complexrepo;

	public Flux<Student> viewAll() {
		return repository.findAll();
	}

	public Mono<Student> getByRoll(int roll) {
		
		return repository.findById(roll);
	}

	public Mono<Student> save(Student student) {
		return repository.save(student);
	}

	public Mono<StudentComplex> complexSave(StudentComplex student) {
		return complexrepo.save(student);
	}

	public Mono<Student> update(Student student) {
		return repository.findById(student.getRollNo()).flatMap(existingStudent -> {
			existingStudent.setDepartment(student.getDepartment());
			existingStudent.setYear(student.getYear());
			return repository.save(existingStudent);
		});
	}

	public Mono<StudentComplex> updateComplex(StudentComplex student) {
		return complexrepo.findById(student.getRollNo()).flatMap(existingStudent -> {
			existingStudent.setDepartment(student.getDepartment());
			existingStudent.setYear(student.getYear());
			return complexrepo.save(existingStudent);
		});
	}

	public Flux<StudentEvent> getEvents(final int roll) {
		return repository.findById(roll).flatMapMany(student -> {

			Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

			Flux<StudentEvent> studentEventFlux = Flux
					.fromStream(Stream.generate(() -> new StudentEvent(student, new Date())));

			return Flux.zip(interval, studentEventFlux).map(objects -> objects.getT2());

		});
	}
}
