package com.kh.jdbc.day02.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentDAO {
	// DAO의 역할 : DB에 있는 정보를 가져와서 VO로 보냄
	// control + shift + - : 한 파일을 나눠서 볼 수 있음
	// 계속 똑같이 쓸 변수들을 필드에 상수로 선언 -> 모든 메소드에서 가져다 쓸 수 있도록~
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL 		 = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER 	     = "STUDENT";
	private final String PASSWORD 	 = "STUDENT";
	
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 5. 결과 받기
		 * 6. 자원해제(close())
		 */
		
		String query	 	= "SELECT * FROM STUDENT_TBL";
		Student student 	= null;
		List<Student> sList = null;
		// List는 while문 바깥에서 선언 -> 안에 넣으면 계속 초기화됨
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			// 2. DB 연결 생성 (DriverManager)
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 5. 결과 받기
			ResultSet rset = stmt.executeQuery(query); // SELECT -> ResultSet
			sList = new ArrayList<Student>();
			// 후처리
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			// 6.자원해제
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sList;
	}

	public List<Student> selectAllByName(String studentName) {
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '" + studentName + "'";
		// '='으로 검색하면 똑같은 것만 출력됨
		List<Student> sList = new ArrayList<Student>();
		Student student = null;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sList;
	}

	public Student selectOneById(String studentId) {
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";
		Student student = null;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			if(rset.next()) {	// ***결과 튜플이 1개일때는 if문 사용***
				student = rsetToStudent(rset);
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	public int insertStudent(Student student) {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 5.  결과 받기
		 * 6. 자원 해제 
		 */
		// INSERT INTO STUDENT_TBL VALUES('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01082829222', '서울시 중구 남대문로 120', '독서,수영', SYSDATE);
		String query = "INSERT INTO STUDENT_TBL VALUES("
				+ "'"+student.getStudentId()+"', "
						+ "'"+student.getStudentPwd()+"', "
								+ "'"+student.getStudentName()+"', "
										+ "'"+student.getGender()+"', "
												+student.getAge()+", "
														+ "'"+student.getEmail()+"', "
																+ "'"+student.getPhone()+"', "
																		+ "'"+student.getAddress()+"', "
																				+ "'"+student.getHobby()+"', "
																						+ "SYSDATE)";
		// ***************** 홑따옴표 주의 ******************
		int result = -1;
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			// 2. DB 연결 생성
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 및 5. 결과 받기
			// stmt.executeQuery(query); // ***SELECT용***
			result = stmt.executeUpdate(query); // ***DML(INSERT, UPDATE, DELETE)***
			// 6. 자원 해제
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateStudent(Student student) {
		String query = "UPDATE STUDENT_TBL SET "
				+ "STUDENT_PWD = '"+student.getStudentPwd()+"', "
						+ "EMAIL = '"+student.getEmail()+"', "
								+ "PHONE = '"+student.getPhone()+"', "
										+ "ADDRESS = '"+student.getAddress() +"', "
												+ "HOBBY = '"+student.getHobby()+"' "
														+ "WHERE STUDENT_ID = '"+student.getStudentId()+"'";
		int result = -1;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int deleteStudent(String studentId) {
		
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId + "'";
		int result = -1;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private Student rsetToStudent(ResultSet rset) throws SQLException {

		Student student = new Student();
		student.setStudentId(rset.getString("STUDENT_ID"));
		student.setStudentPwd(rset.getString("STUDENT_PWD"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setGender(rset.getString("GENDER").charAt(0));
		// 문자는 문자열에 문자로 잘라서 사용 -> charAt() 메소드
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));
		
		return student;
	}
}
