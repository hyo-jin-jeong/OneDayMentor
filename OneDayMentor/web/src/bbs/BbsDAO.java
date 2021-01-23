package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {
   // dao : �����ͺ��̽� ���� ��ü�� ����
         private Connection conn; // connection:db�������ϰ� ���ִ� ��ü
         private ResultSet rs;

         // mysql ó���κ�
         public BbsDAO() {
            // �����ڸ� ������ش�.
            try {
               String dbURL = "jdbc:mysql://localhost:3306/ODM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // localhost:3306 ��Ʈ�� ��ǻ�ͼ�ġ�� mysql�ּ�
               String dbID = "root";
               String dbPassword = "1234";
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
            } catch (Exception e) {
               e.printStackTrace();
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
         

         //bbsID �Խñ� ��ȣ �������� �Լ�
            public int getNext() { 
               String SQL = "SELECT bbsID FROM Bbs ORDER BY bbsID DESC ";
               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  rs = pstmt.executeQuery();
                  if(rs.next()) {
                     return rs.getInt(1) + 1;
                  }
                  return 1;//���簡 ù ��° �Խù��� ���
               } catch (Exception e) {
                  e.printStackTrace();
               }
               return -1; //�����ͺ��̽� ����
            }
            
            
            //������ ���� �ۼ��ϴ� �Լ�
            public int write(String bbsTitle, String userID, String bbsContent, int categoryID) { 
               String SQL = "INSERT INTO Bbs VALUES(?, ?, ?, ?, ?, ?, ?)";

               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  pstmt.setInt(1, getNext());
                  pstmt.setString(2, bbsTitle);
                  pstmt.setString(3, userID);
                  pstmt.setString(4, getDate());
                  pstmt.setString(5, bbsContent);
                  pstmt.setInt(6,1);
                  pstmt.setInt(7, categoryID);
                  return pstmt.executeUpdate();
               } catch (Exception e) {
                  e.printStackTrace();
               }
               return -1; //�����ͺ��̽� ����
            }
            
            
            public int getCount(int categoryID) {
               String SQL = "select count(bbsID) from bbs where categoryID = ?";
               int count = 0;
               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  pstmt.setInt(1,  categoryID);
                  rs = pstmt.executeQuery();
                  if(rs.next())
                     count = rs.getInt(1);
                  System.out.println(count);

               } catch (Exception e) {
                  e.printStackTrace();
               }

               return count; //���� �̾ƿ� �Խñ� ����Ʈ ����
   
            }
            
            
            //ī�װ��� �Խñ� �ҷ����� �Լ�
            public ArrayList<Bbs> getList(int categoryID, int row, int count){
               String SQL = "select bbsID, bbsTitle, userID, bbsDate, categoryID from bbs where categoryID = ? order by bbsDate desc limit ?, ?";
               
               ArrayList<Bbs> list = new ArrayList<Bbs>();
               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  
                  pstmt.setInt(1, categoryID);
                  pstmt.setInt(2, row);
                  pstmt.setInt(3, count);
                  
                  rs = pstmt.executeQuery();
                  while (rs.next()) {
                     Bbs bbs = new Bbs();
                     bbs.setBbsID(rs.getInt(1));
                     bbs.setBbsTitle(rs.getString(2));
                     bbs.setUserID(rs.getString(3));
                     bbs.setBbsDate(rs.getString(4));
                     bbs.setCategoryID(rs.getInt(5));
                     list.add(bbs);
                  }

               } catch (Exception e) {
                  e.printStackTrace();
               }
               return list; //���� �̾ƿ� �Խñ� ����Ʈ ����
            }
            
            //������ �Խñ� �ҷ����� �Լ� //��������������
            public ArrayList<Bbs> getList2(String userID, int row, int count){
               String SQL = "select bbsID, bbsTitle, userID, bbsDate, categoryID from bbs where userID = ? order by bbsDate desc limit ?, ?";
               
               ArrayList<Bbs> list = new ArrayList<Bbs>();
               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  
                  pstmt.setString(1, userID);
                  pstmt.setInt(2, row);
                  pstmt.setInt(3, count);
                  
                  rs = pstmt.executeQuery();
                  while (rs.next()) {
                     Bbs bbs = new Bbs();
                     bbs.setBbsID(rs.getInt(1));
                     bbs.setBbsTitle(rs.getString(2));
                     bbs.setUserID(rs.getString(3));
                     bbs.setBbsDate(rs.getString(4));
                     bbs.setCategoryID(rs.getInt(5));
                     list.add(bbs);
                  }

               } catch (Exception e) {
                  e.printStackTrace();
               }
               return list; //���� �̾ƿ� �Խñ� ����Ʈ ����
            }
           
              
         public ArrayList<Bbs> getList3(String type, String search, int row, int count){
        		//String SQL = "select bbsID, bbsTitle, userID, bbsDate, categoryID from bbs where bbsTitle like ? order by bbsDate desc limit ?, ?";
         String SQL = null;
          if (type.equals("����"))
             SQL = "SELECT bbsID, bbsTitle, userID, bbsDate, categoryID from bbs where bbsTitle like ? order by bbsDate desc limit ?, ?";
          else
             SQL = "SELECT bbsID, bbsTitle, userID, bbsDate, categoryID from bbs where bbsContent like ? order by bbsDate desc limit ?, ?";
          
          ArrayList<Bbs> list = new ArrayList<Bbs>();
          try {
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             
             pstmt.setString(1, "%" + search + "%");
             pstmt.setInt(2, row);
             pstmt.setInt(3, count);
             
             rs = pstmt.executeQuery();
             while (rs.next()) {
                Bbs bbs = new Bbs();
                bbs.setBbsID(rs.getInt(1));
                bbs.setBbsTitle(rs.getString(2));
                bbs.setUserID(rs.getString(3));
                bbs.setBbsDate(rs.getString(4));
                bbs.setCategoryID(rs.getInt(5));
                list.add(bbs);
             }

          } catch (Exception e) {
             e.printStackTrace();
          }
          return list; //���� �̾ƿ� �Խñ� ����Ʈ ����
       }
         
         //���������� �����ȱ� bbsID�ѱ�� �� bbs list�� ���
         public ArrayList<Bbs> getList4(String mentorID, int row, int count){
             String SQL = "select b.bbsID, b.bbsTitle, b.userID, b.bbsDate, b.categoryID "
             		+ "from bbs b, apply a where a.mentorID = ? and b.userID = a.menteeID and b.bbsID=a.bbsID and "
             		+ "b.categoryID=a.categoryID order by a.applyID desc limit ?, ?";
             
             ArrayList<Bbs> list = new ArrayList<Bbs>();
             try {
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                
                pstmt.setString(1, mentorID);
                pstmt.setInt(2, row);
                pstmt.setInt(3, count);
                
                rs = pstmt.executeQuery();
                while (rs.next()) {
                   Bbs bbs = new Bbs();
                   bbs.setBbsID(rs.getInt(1));
                   bbs.setBbsTitle(rs.getString(2));
                   bbs.setUserID(rs.getString(3));
                   bbs.setBbsDate(rs.getString(4));
                   bbs.setCategoryID(rs.getInt(5));
                   list.add(bbs);
                }

             } catch (Exception e) {
                e.printStackTrace();
             }
             return list; //���� �̾ƿ� �Խñ� ����Ʈ ����
          }

         public ArrayList<Bbs> getList5(String id, int row, int count){
             String SQL = "select b.bbsID, b.bbsTitle, b.userID, b.bbsDate, b.categoryID "
             		+ "from bbs b, apply a "
             		+ "where (a.mentorID = ? or a.menteeID=?) and a.applyAvailable=1 and b.userID = a.menteeID and b.bbsID=a.bbsID and "
             		+ "b.categoryID=a.categoryID "
             		+ "order by a.applyID desc limit ?, ?";
             
             ArrayList<Bbs> list = new ArrayList<Bbs>();
             try {
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                
                pstmt.setString(1, id);
                pstmt.setString(2, id);
                pstmt.setInt(3, row);
                pstmt.setInt(4, count);
                
                rs = pstmt.executeQuery();
                while (rs.next()) {
                   Bbs bbs = new Bbs();
                   bbs.setBbsID(rs.getInt(1));
                   bbs.setBbsTitle(rs.getString(2));
                   bbs.setUserID(rs.getString(3));
                   bbs.setBbsDate(rs.getString(4));
                   bbs.setCategoryID(rs.getInt(5));
                   list.add(bbs);
                }

             } catch (Exception e) {
                e.printStackTrace();
             }
             return list; //���� �̾ƿ� �Խñ� ����Ʈ ����
          }
         
         
            //10 ���� ����¡ ó���� ���� �Լ� //�Խù�����
            public boolean nextPage (int pageNumber,int categoryID) {
               int count=0;
               String SQL = "SELECT bbsID FROM bbs where categoryID=? limit ?,?";
               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  pstmt.setInt(1,  categoryID);
                  pstmt.setInt(2, pageNumber*10);
                  pstmt.setInt(3, 11);
                  rs = pstmt.executeQuery();
                  while(rs.next()) {
                     count++;
                     if(count == 11)
                        return true;
                  }
               } catch (Exception e) {
                  e.printStackTrace();
               }
               return false;       
            }
            
            //10 ���� ����¡ ó���� ���� �Լ� //��������������
            public boolean nextPage2 (int pageNumber, String userID) {
               int count=0;
               String SQL = "SELECT bbsID FROM bbs where userID=? limit ?,?";
               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  pstmt.setString(1,  userID);
                  pstmt.setInt(2, pageNumber*10);
                  pstmt.setInt(3, 11);
                  rs = pstmt.executeQuery();
                  while(rs.next()) {
                     count++;
                     if(count == 11)
                        return true;
                  }
               } catch (Exception e) {
                  e.printStackTrace();
               }
               return false;       
            }
            
          //10 ���� ����¡ ó���� ���� �Լ� //�˻��Ҷ�
            public boolean nextPage3 (int searchType, int pageNumber,String search) {
               int count=0;
               String SQL = null;
               if(searchType == 0) {
                   SQL = "SELECT bbsID FROM bbs where bbsTitle like ? limit ?,?";
               }
               else {
            	   SQL = "SELECT bbsID FROM bbs where bbsContent like ? limit ?,?";
               }
               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  pstmt.setString(1, "%" + search + "%");
                  pstmt.setInt(2, pageNumber*10);
                  pstmt.setInt(3, 11);
                  rs = pstmt.executeQuery();
                  while(rs.next()) {
                     count++;
                     if(count == 11)
                        return true;
                  }
               } catch (Exception e) {
                  e.printStackTrace();
               }
               return false;       
            }
            
            public boolean nextPage4(int pageNumber, String mentorID) {
                int count = 0;
                String SQL = "SELECT applyID FROM apply where mentorID=? limit ?,?";
                try {
                   PreparedStatement pstmt = conn.prepareStatement(SQL);
                   pstmt.setString(1, mentorID);
                   pstmt.setInt(2, pageNumber * 10);
                   pstmt.setInt(3, 11);
                   rs = pstmt.executeQuery();
                   while (rs.next()) {
                      count++;
                      if (count == 11)
                         return true;
                   }
                } catch (Exception e) {
                   e.printStackTrace();
                }
                return false;
             }
            
            public boolean nextPage5(int pageNumber, String id) {
                int count = 0;
                String SQL = "SELECT applyID FROM apply where (mentorID=? or menteeID =?) and applyAvailable=1 limit ?,?";
                try {
                   PreparedStatement pstmt = conn.prepareStatement(SQL);
                   pstmt.setString(1, id);
                   pstmt.setString(2, id);
                   pstmt.setInt(3, pageNumber * 8);
                   pstmt.setInt(4, 9);
                   rs = pstmt.executeQuery();
                   while (rs.next()) {
                      count++;
                      if (count == 9)
                         return true;
                   }
                } catch (Exception e) {
                   e.printStackTrace();
                }
                return false;
             }

            
            public Bbs getBbs(int bbsID) {
               String SQL = "SELECT * FROM Bbs WHERE bbsID = ?";

               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  pstmt.setInt(1, bbsID);
                  rs = pstmt.executeQuery();

                  if (rs.next()) {
                     Bbs bbs = new Bbs();
                     bbs.setBbsID(rs.getInt(1));
                     bbs.setBbsTitle(rs.getString(2));
                     bbs.setUserID(rs.getString(3));
                     bbs.setBbsDate(rs.getString(4));
                     bbs.setBbsContent(rs.getString(5));
                     bbs.setBbsAvailable(rs.getInt(6));
                     bbs.setCategoryID(rs.getInt(7));
                     return bbs;
                  }
               } catch (Exception e) {
                  e.printStackTrace();
               }
               return null;
            }
            
            //���� �Լ�
            public int update(int bbsID, String bbsTitle, String bbsContent) {
               String SQL = "UPDATE Bbs SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";

               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);
                  pstmt.setString(1, bbsTitle);
                  pstmt.setString(2, bbsContent);
                  pstmt.setInt(3, bbsID);
                  return pstmt.executeUpdate();
               } catch (Exception e) {
                  e.printStackTrace();
               }
               return -1; // �����ͺ��̽� ����
            }
            
            //���� �Լ�
            public int delete(int bbsID) {
               String SQL = "DELETE FROM Bbs WHERE bbsID = ?";
               try {
                  PreparedStatement pstmt = conn.prepareStatement(SQL);   
                  pstmt.setInt(1, bbsID);
                  return pstmt.executeUpdate();
               } catch (Exception e) {
                  e.printStackTrace();
               }
               return -1; // �����ͺ��̽� ����
            }
            
           
            
   }