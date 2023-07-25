package com.kh.jdbc.day04.student.common;

import java.sql.*;

public class JDBCTemplate {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL 		 = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private final String USER		 = "STUDENT";
	private final String PASSWORD 	 = "STUDENT";
	// 디자인 패턴 : 각기 다른 소프트웨어 모듈이나 기능을 가진 응용 SW를
	// 개발할 때 공통되는 설계 문제를 해결하기 위하여 사용되는 패턴임.
	// ==> 효율적인 방식을 위함!
	// 패턴의 종류 : 생성패턴, 구조패턴, 행위패턴
	// 1. 생성패턴 : 싱글톤 패턴, 추상팩토리, 팩토리 메서드, 빌드, 프로토타입, ..
	// 2. 구조패턴 : 브릿지, 컴포지트, 데코레이트, 프록시, ...
	// 3. 행위패턴 : 옵저버, 스테이트, 전략, 템플릿 메서드, ...
	
	/*
	 * public class Singleton {
	 * 		private static Singleton instance;
	 * 		
	 * 		private Singleton() {}
	 * 		
	 * 		public static Singleton getInstance() {
	 * 			if(instance == null) {
	 * 				instance = new Singleton();
	 * 			}
	 * 			return instance;
	 * 		}
	 * 
	 */
	
	// ----------------싱글톤 패턴----------------------
	// -> 무조건 딱 한번만 객체를 생성, 없으면 생성하고 이미 존재하면 존재하는 객체를 사용함
	private static JDBCTemplate instance;
	// JDBCTemplate 객체 타입의 변수 선언
	private static Connection conn;
	
	private JDBCTemplate() {} 
	// 생성자를 private으로 선언해서 getInstance() 메소드만 생성에 관여할 수 있게 함
	
	public static JDBCTemplate getInstance() {
		// 이미 만들어져있는지 체크하고
		// 만들어져 있으면 그거 사용하게 하기
		if(instance == null) {
			// JDBCTemplate 객체가 만들어져있지 않으면
			instance = new JDBCTemplate();
			//instance 변수 안에 JDBCTemplate 객체 생성
		}
		return instance;
		// 생성되어 있다면 생성된 것을 return
	}
	// ------------------------------------------------
	
	public Connection createConnection() {
		try {
			if(conn == null || conn.isClosed()) {
				// conn이 null 이거나 닫혀있을 때만 연결을 생성하라~
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);				
			}
			// DBCP(DataBase Connection Pool)
			// 연결을 계속 생성하면 시스템 과부하 생김
			// -> 한번 만들어놓고 재사용하기 위해 싱글톤 패턴 사용
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void close() {
		if(conn != null) {
			try {
				if(!conn.isClosed()) {
					// isClosed() 는 Checked Exception
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
