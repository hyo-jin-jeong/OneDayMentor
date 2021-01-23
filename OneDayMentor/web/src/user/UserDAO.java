package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
   // dao : �����ͺ��̽� ���� ��ü�� ���ڷμ�
   // ���������� db���� ȸ������ �ҷ����ų� db�� ȸ������ ������

   private Connection conn; // connection:db�������ϰ� ���ִ� ��ü
   private PreparedStatement pstmt;
   private ResultSet rs;

   // mysql�� ������ �ִ� �κ�

   public UserDAO() { // ������ ����ɶ����� �ڵ����� db������ �̷�� �� �� �ֵ�����
      try {
         String dbURL = "jdbc:mysql://localhost:3306/ODM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // localhost:3306
                                                                                                                        // ��Ʈ��
                                                                                                                        // ��ǻ�ͼ�ġ��
                                                                                                                        // mysql�ּ�
         String dbID = "root";
         String dbPassword = "1234";
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
      } catch (Exception e) {
         e.printStackTrace(); // ������ �������� ���
      }
   }

   // �α����� �õ��ϴ� �Լ�****
   public int login(String userID, String userPassword) {
      String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
      try {
         // pstmt : prepared statement ������ sql������ db�� �����ϴ� �������� �ν��Ͻ�������
         pstmt = conn.prepareStatement(SQL);
         // sql������ ���� ��ŷ����� ����ϴ°�... pstmt�� �̿��� �ϳ��� ������ �̸� �غ��ؼ�(����ǥ���)
         // ����ǥ�ش��ϴ� ������ �������̵��, �Ű������� �̿�.. 1)�����ϴ��� 2)��й�ȣ��������
         pstmt.setString(1, userID);
         // rs:result set �� �������
         rs = pstmt.executeQuery();
         // ����� �����Ѵٸ� ����

         if (rs.next()) {
            // �н����� ��ġ�Ѵٸ� ����
            if (rs.getString(1).equals(userPassword)) {
               return 1; // ��� ����
            } else
               return 0; // ��й�ȣ ����ġ
         }
         return -1; // ���̵� ���� ����
      } catch (Exception e) {
         e.printStackTrace();
      }
      return -2; // �����ͺ��̽� ������ �ǹ�
   }

   public boolean check(String id) {
	   String SQL = "SELECT id from user";
	   try {
		   pstmt = conn.prepareStatement(SQL);
		   ResultSet rs = pstmt.executeQuery();
		   
		   while(rs.next()) {
			   if(rs.getString("id").equals(id))
				   return true;
		   }
	   } catch(SQLException e) {
		   e.printStackTrace();
	   }
	   return false;
   }
   
   public int join(User user) {
      String SQL = "INSERT INTO USER VALUES (?,?,?,?,?,?)";
      try {
         pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1, user.getUserID());
         pstmt.setString(2, user.getUserPassword());
         pstmt.setString(3, user.getUserName());
         pstmt.setString(4, user.getUserGender());
         pstmt.setString(5, user.getUserEmail());
         pstmt.setString(6, user.getUserMeta());
         return pstmt.executeUpdate();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return -1; // DB ����
   }

   // ȸ�� Ż��
   public int leave(String userID) {
      String SQL = "DELETE FROM USER WHERE userID = ?";
      try {
         pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1, userID);
         return pstmt.executeUpdate();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return -1; // DB ����
   }

   public String getMeta(String userID) {
      String SQL = "SELECT userMeta from user where userID=?";
      try {
         pstmt = conn.prepareStatement(SQL);
         pstmt.setString(1, userID);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            // �н����� ��ġ�Ѵٸ� ����

            return rs.getString(1); // ��� ����

         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      return "";

   }
   
   public String getMenteeMeta(int applyID) {
	      String SQL = "select u.userMeta " + "from user u, apply a " + "where a.applyID = ? and a.menteeID = u.userID";
	      try {
	         pstmt = conn.prepareStatement(SQL);
	         pstmt.setInt(1, applyID);
	         rs = pstmt.executeQuery();
	         if (rs.next()) {
	            return rs.getString(1);
	         }
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      return "";
	   }

   public String getMentorMeta(int applyID) {
      String SQL = "select u.userMeta " + "from user u, apply a " + "where a.applyID = ? and a.mentorID=u.userID";
      try {
         pstmt = conn.prepareStatement(SQL);
         pstmt.setInt(1, applyID);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            return rs.getString(1);
         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return "";
   }

}