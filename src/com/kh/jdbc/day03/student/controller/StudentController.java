package com.kh.jdbc.day03.student.controller;

import java.util.List;

import com.kh.jdbc.day03.student.model.dao.StudentDAO;
import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentController {
	private StudentDAO sDao;
	
	public StudentController() {
		sDao = new StudentDAO();
	}

	public List<Student> selectAllStudent() {
		List<Student> sList = sDao.selectAll();
		return sList;
	}

	public Student selectOneById(String studentId) {
		Student student = sDao.selectOneById(studentId);
		return student;
	}

	public Student studentLogin(Student student) {
		Student result = sDao.selectLoginInfo(student);
		return result;
	}

	public List<Student> selectAllByName(String studentName) {
		List<Student> sList = sDao.selectAllByName(studentName);
		return sList;
	}

	public int inputStudent(Student student) {
		int result = sDao.insertStudent(student);
		return result;
	}

	public int deleteStudent(String studentId) {
		int result = sDao.deleteStudent(studentId);
		return result;
	}

	public int updateStudent(Student student) {
		int result = sDao.updateStudent(student);
		return result;
	}

}
