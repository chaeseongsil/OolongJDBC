package com.kh.jdbc.day04.student.model.service;

import java.sql.Connection;
import java.sql.SQLException;
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
		List<Student> sList = null;;
		try {
			Connection conn = jdbcTemplate.createConnection();
			sList = sDao.selectAll(conn);
			jdbcTemplate.close(); // 동작이 끝나면 연결 닫아줌
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		result += sDao.updateStudent(conn, student);
		
		if(result > 1) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		// 한 개의 트랜잭션에서 insert & update 등 여러 작업을 수행하는 경우
		// 도중에 오류가 발생하면 그 전꺼는 자동으로 커밋되어 문제 발생
		// auto commit을 해제하고 result에 결과값을 누적시켜서
		// 둘 다 성공했을 때(result > 1)만 commit하게 하도록 함
		jdbcTemplate.close();
		return result;
	}

	public int updateStudent(Student student) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.updateStudent(conn, student);
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		jdbcTemplate.close();
		return result;
	}

	public int deleteStudent(String studentId) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.deleteStudent(conn, studentId);
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		jdbcTemplate.close();
		return result;
	}
}
