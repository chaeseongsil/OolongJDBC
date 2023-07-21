package com.kh.jdbc.day02.view;

import java.util.*;

import com.kh.jdbc.day01.student.controller.StudentController;
import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentView {
	
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}

	public void startProgram() {
		List<Student> sList = null;
		Student student = null;
		String studentId = "";
		finish :
		while(true) {
			int choice = printMenu();
			switch(choice) {
				case 1 :
					// SELECT * FROM STUDENT_TBL -> 결과값이 복수개이므로 List 사용
					sList = controller.printStudentList();
					if(!sList.isEmpty()) {
						showAllStudents(sList);
						displaySuccess("학생 전체 정보 조회 성공");
					}else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 2 :
					// 아이디로 조회하는 쿼리문 생각해보기(리턴형은 무엇으로? 매개변수는 무엇으로?)
					studentId = inputStudentId();
					// printStudentById() 메소드가 학생 정보를 조회, dao의 메소드는 selectOneById()
					student = controller.printStudentById(studentId); 
					// showStudent() 메소드로 학생정보 출력
					if(student != null) {
						showStudent(student);
						displaySuccess("학생 정보 조회 성공");
					} else {
						displayError("해당 아이디는 존재하지 않습니다.");
					}
					
					break;
				case 3 :
					// 쿼리문 생각해보기 (매개변수 유무, 리턴형은?)
					String studentName = inputStudentName();
					// printStudentByName, printStudentsByName (?)
					sList = controller.printStudentsByName(studentName);
					// selectOneByName, selectAllByName (?)
					// showStudent, showAllStudents
					if(!sList.isEmpty()) {
						showAllStudents(sList);
						displaySuccess("학생 이름으로 조회 성공");
					} else {
						displayError("해당 학생은 존재하지 않습니다.");
					}
					break;
				case 4 : 
					// INSERT INTO STUDENT_TBL VALUES('khuser01', 'pass01', '일용자', 'M', 11
					// , 'khuser01@kh.com', '01082829222', '서울시 중구 남대문로 120', '독서,수영', SYSDATE);
					student = inputStudent();
					int result = controller.insertStudent(student); // 결과값이 int이므로 후처리가 따로 없음
					if(result > 0) {
						displaySuccess("학생 정보 등록 성공");
					}else {
						displayError("학생 정보 등록 실패");
					}
					break;
				case 5 :
//					UPDATE STUDENT_TBL 
//					SET STUDENT_PWD = 'pass11', EMAIL = 'khuser01@iei.or.kr', PHONE = '01092920303'
//					, ADDRESS = '서울시 강남구 개포동', HOBBY = '코딩,수영'
//					WHERE STUDENT_ID = 'khuser01';
					student = modifyStudent();
					result = controller.modifyStudent(student);
					if(result > 0) {
						displaySuccess("학생 정보가 변경되었습니다.");
					}else {
						displayError("학생 정보가 수정되지 않았습니다.");
					}
					break;
				case 6 :
					// 쿼리문 생각해보기 (매개변수 필요유무, 반환형?)
					// DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
					studentId = inputStudentId();
					result = controller.deleteStudent(studentId);
					if(result > 0) {
						displaySuccess("학생 정보 삭제 성공");
					}else {
						displayError("학생 정보 삭제 실패");
					}
					break;
				case 0 :
					break finish;
				default :
					break;
			}
		}
		
	}

	private Student modifyStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 학생 정보 수정 ======");
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();	// ***공백 제거, 엔터 제거***
		String address = sc.nextLine();
		System.out.print("취미(','로 구분) : ");
		String hobby = sc.next();
		Student student = new Student(studentId, studentPw, email, phone, address, hobby);
		return student;
	}

	public int printMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 학생관리 프로그램 ======");
		System.out.println("1. 학생 전체 조회");
		System.out.println("2. 학생 아이디로 조회");
		System.out.println("3. 학생 이름으로 조회");
		System.out.println("4. 학생 정보 등록");
		System.out.println("5. 학생 정보 수정");
		System.out.println("6. 학생 정보 삭제");
		System.out.println("0. 프로그램 종료");
		System.out.print("메뉴 선택 : ");
		int input = sc.nextInt();
		return input;
	}

	private String inputStudentId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 학생 아이디로 조회 ======");
		System.out.print("학생 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}

	private String inputStudentName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("====== 학생 이름으로 조회 ======");
		System.out.print("학생 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
	}

	private Student inputStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이름 : ");
		String studentName = sc.next();
		System.out.print("성별 : ");
		char gender = sc.next().charAt(0);
		System.out.print("나이 : ");
		int age = sc.nextInt();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();	// ***공백 제거, 엔터 제거***
		String address = sc.nextLine();
		System.out.print("취미(','로 구분) : ");
		String hobby = sc.next();
		Student student = new Student(studentId, studentPw, studentName, gender, age, email, phone, address, hobby);
		return student;
	}

	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : " + message);
	}

	private void displayError(String message) {
		System.out.println("[서비스 실패] : " + message);
	}

	private void showAllStudents(List<Student> sList) {
		System.out.println("====== 학생 전체 정보 출력 ======");
		for(Student std : sList) {
			System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n"
					, std.getStudentName(), std.getAge(), std.getStudentId()
					, std.getGender(), std.getEmail(), std.getPhone()
					, std.getAddress(), std.getHobby(), std.getEnrollDate());
		}
	}

	private void showStudent(Student std) {
		System.out.println("====== 학생 정보 출력(아이디로 조회) ======");
		System.out.printf("이름 : %s, 나이 : %d, 아이디 : %s, 성별 : %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n"
				, std.getStudentName(), std.getAge(), std.getStudentId()
				, std.getGender(), std.getEmail(), std.getPhone()
				, std.getAddress(), std.getHobby(), std.getEnrollDate());
	}
}
