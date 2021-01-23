package hash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HashDAO {
	// dao : �����ͺ��̽� ���� ��ü�� ���ڷμ�
	   // ���������� db���� ȸ������ �ҷ����ų� db�� ȸ������ ������

	   private Connection conn; // connection:db�������ϰ� ���ִ� ��ü
	   private ResultSet rs;

	   // mysql�� ������ �ִ� �κ�

	   public HashDAO() { // ������ ����ɶ����� �ڵ����� db������ �̷�� �� �� �ֵ�����
	      try {
	         String dbURL = "jdbc:mysql://localhost:3306/ODM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // localhost:3306                                                                                                         // mysql�ּ�
	         String dbID = "root";
	         String dbPassword = "1234";
	         Class.forName("com.mysql.jdbc.Driver");
	         conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
	      } catch (Exception e) {
	         e.printStackTrace(); // ������ �������� ���
	      }
	   }
	 //������ �ð��� �������� �Լ�
       public String getDate() { 
          String SQL = "SELECT NOW()";
          try {
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             rs = pstmt.executeQuery();
             if(rs.next()) {
                return rs.getString(1);
             }

          } catch (Exception e) {
             e.printStackTrace();
          }

          return ""; //�����ͺ��̽� ����

       }
       
       // hash ���̺� �ִ� �Լ�
       public int insert(String userMeta, String hash) { 
          String SQL = "INSERT INTO hash VALUES(?, ?, ?)";

          try {
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             pstmt.setString(1, userMeta);
             pstmt.setString(2, hash);
             pstmt.setString(3, getDate());
             return pstmt.executeUpdate();
          } catch (Exception e) {
             e.printStackTrace();
          }
          return -1; //�����ͺ��̽� ����
       }
	   
	/*
	 * public ArrayList<String> getHash(String userMeta) { String SQL =
	 * "select hash from hash where userMeta = ?"; ArrayList<String> hashes = new
	 * ArrayList<String>();
	 * 
	 * try { PreparedStatement pstmt = conn.prepareStatement(SQL);
	 * pstmt.setString(1, userMeta); rs = pstmt.executeQuery(); while (rs.next()) {
	 * hashes.add(rs.getString(1)); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return hashes; }
	 */
       public ArrayList<Hash> getHash(String userMeta) {
		   String SQL = "select hash, date from hash where userMeta = ?";
		   ArrayList<Hash> hashes = new ArrayList<Hash>();
		   
		   try {
	              PreparedStatement pstmt = conn.prepareStatement(SQL);
	              pstmt.setString(1, userMeta);
	              rs = pstmt.executeQuery();
	              while (rs.next()) {
	            	 Hash hash = new Hash();
	                 hash.setHash(rs.getString(1));
	                 hash.setDate(rs.getString(2));
	                 hashes.add(hash);
	              }

	           } catch (Exception e) {
	              e.printStackTrace();
	           }
		   return hashes;
	   }  
	   
	 
}
