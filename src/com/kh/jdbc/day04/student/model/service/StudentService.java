package com.kh.jdbc.day04.student.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.jdbc.day04.student.common.JDBCTemplate;
import com.kh.jdbc.day04.student.model.dao.StudentDAO;
import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentService {
	// service는 controller와 dao 사이에 존재함
	private StudentDAO sDao;
	private JDBCTemplate jdbcTemplate;
	
	public StudentService() {
		sDao = new StudentDAO();
		// jdbcTemplate = new JDBCTemplate(); // 생성자가 private이기 때문에 사용 못함!
		jdbcTemplate = JDBCTemplate.getInstance();
		// new 로 생성하지 않고 JDBCTemplate의 getInstance() 메소드를 통해 생성
	}

	public List<Student> selectAll() {
		Connection conn = jdbcTemplate.createConnection();
		List<Student> sList = sDao.selectAll(conn);
		jdbcTemplate.close(); // 동작이 끝나면 연결 닫아줌
		return sList;
	}

	public Student seletOneById(String studentId) {
		Connection conn = jdbcTemplate.createConnection();
		Student student = sDao.selectOneById(conn, studentId);
		jdbcTemplate.close();
		return student;
	}

	public List<Student> selectAllByName(String studentName) {
		Connection conn = jdbcTemplate.createConnection();
		List<Student> sList = sDao.selectAllByName(conn, studentName);
		jdbcTemplate.close();
		return sList;
	}

	public int insertStudent(Student student) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.insertStudent(conn, student);
		jdbcTemplate.close();
		return result;
	}

	public int updateStudent(Student student) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.updateStudent(conn, student);
		return result;
	}

	public int deleteStudent(String studentId) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.deleteStudent(conn, studentId);
		jdbcTemplate.close();
		return result;
	}
}
