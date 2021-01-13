package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.model.StudentComplex;

public interface StudentComplexRepo extends ReactiveMongoRepository<StudentComplex, Integer>{

}
