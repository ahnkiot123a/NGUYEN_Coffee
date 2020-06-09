package com.koit.project_prm391_1.dao;

import android.util.Log;

import com.koit.project_prm391_1.object.Diachi;
import com.koit.project_prm391_1.object.Huyen;
import com.koit.project_prm391_1.object.Tinh;
import com.koit.project_prm391_1.object.Xa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {
    ConnectDB connectDB = new ConnectDB();
    ResultSet rs;
    Connection con = connectDB.CONN();
    List<Diachi> list;
    List<Tinh> listTinh;
    List<Huyen> listHuyen;
    List<Xa> listXa;
    /*
    Add address
     */
    public boolean addDiaChi(int userID, String name, String soNha, String tinh, String huyen, String xa, String email, String phoneNumber) {
        String sqlInsert = "INSERT INTO DiaChi (UserID, Name, SoNha, TinhID, HuyenID, XaID,Email,PhoneNumber,status)" +
                " VALUES (?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.setInt(1, userID);
            stmt.setString(2, name);
            stmt.setString(3, soNha);
            stmt.setString(4, tinh);
            stmt.setString(5, huyen);
            stmt.setString(6, xa);
            stmt.setString(7, email);
            stmt.setString(8, phoneNumber);
            stmt.setBoolean(9, true);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return false;
    }

    public boolean updateDiachi(int diachiID, String name, String soNha, String tinh, String huyen, String xa, String email, String phoneNumber) {
        String sqlInsert = "UPDATE Diachi " +
                "SET name ='" + name + "', sonha='" + soNha + "', tinhId='" + tinh + "',huyenID='" + huyen + "',xaID='" + xa + "',email='" + email + "',phoneNumber='" + phoneNumber +
                "'                WHERE diachiID=" + diachiID;
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("ERROR", e.getMessage());
        }
        return false;
    }

    public String getTinhIDFromNameID(String name) {
        String sqlInsert = "SELECT * FROM TinhThanhPho WHERE name = N'" + name + "'";
        String ID = null;
        ResultSet resultSet;
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                ID = resultSet.getString(1);
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return ID;
    }

    public String getHuyenIDFromNameID(String name) {
        String sqlInsert = "SELECT * FROM QuanHuyen WHERE name = N'" + name + "'";
        String ID = null;
        ResultSet resultSet;

        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                ID = resultSet.getString("maqh");
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return ID;
    }

    public String getXaIDFromNameID(String name) {
        String sqlInsert = "SELECT * FROM XaPhuongThiTran WHERE name = N'" + name + "'";
        String ID = null;
        ResultSet resultSet;
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                ID = resultSet.getString("xaid");
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return ID;
    }
    public Tinh getTinhFromTinhID(String tinhID) {
        String sqlInsert = "SELECT * FROM TinhThanhPho WHERE matp = N'" + tinhID + "'";
        String ID = null, name = null, type = null;
        Tinh t = null;
        ResultSet resultSet;
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                ID = resultSet.getString(1);
                name = resultSet.getString(2);
                type = resultSet.getString(3);
            }
            t = new Tinh(ID, name, type);
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return t;
    }

    public Huyen getHuyenFromHuyen(String huyenID) {
        String sqlInsert = "SELECT * FROM QuanHuyen WHERE maqh = N'" + huyenID + "'";
        String ID = null, name = null, type = null, matp = null;
        Huyen t = null;
        ResultSet resultSet;
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                ID = resultSet.getString(1);
                name = resultSet.getString(2);
                type = resultSet.getString(3);
                matp = resultSet.getString(4);
            }
            t = new Huyen(ID, name, type, matp);
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return t;
    }

    public Xa getXaFromXaID(String xaID) {
        String sqlInsert = "SELECT * FROM XaPhuongThiTran WHERE xaid = N'" + xaID + "'";
        String ID = null, name = null, type = null, maqh = null;
        Xa t = null;
        ResultSet resultSet;
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                ID = resultSet.getString(1);
                name = resultSet.getString(2);
                type = resultSet.getString(3);
                maqh = resultSet.getString(4);
            }
            t = new Xa(ID, name, type, maqh);
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return t;
    }
    public boolean deleteDiachi(int diachiID) {
        String sqlInsert = "UPDATE Diachi SET status=0\n" +
                "WHERE diachiID= " + diachiID;
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return false;
    }

    public List<Diachi> getAllDiachiFromUser(int userID) {
        String sqlInsert = "SELECT        Diachi.DiachiID, Diachi.Name, Diachi.SoNha, TinhThanhPho.name AS Tinh, QuanHuyen.name AS Huyen, XaPhuongThiTran.name AS Xa, Diachi.Email, Diachi.PhoneNumber\n" +
                "FROM            Diachi INNER JOIN\n" +
                "                         QuanHuyen ON Diachi.HuyenID = QuanHuyen.maqh INNER JOIN\n" +
                "                         TinhThanhPho ON Diachi.TinhID = TinhThanhPho.matp AND QuanHuyen.matp = TinhThanhPho.matp INNER JOIN\n" +
                "                         XaPhuongThiTran ON Diachi.XaID = XaPhuongThiTran.xaid AND QuanHuyen.maqh = XaPhuongThiTran.maqh\n" +
                " WHERE Diachi.UserID=? AND Diachi.status=1";
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.setInt(1, userID);
            rs = stmt.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                int diachiId = rs.getInt("DiachiID");
                String name = rs.getString("Name");
                String sonha = rs.getString("Sonha");
                String tinh = rs.getString("Tinh");
                String huyen = rs.getString("Huyen");
                String xa = rs.getString("Xa");
                String email = rs.getString("Email");
                String phoneNumber = rs.getString("PhoneNumber");
                Diachi address = new Diachi(diachiId, userID, name, sonha, tinh, huyen, xa, email, phoneNumber,true);
                list.add(address);
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return list;
    }

    public Diachi getDiachiFromDiachiID(int diachiID) {
        String sqlInsert = "SELECT * FROM Diachi WHERE DiachiID=?";
        Diachi diachi = null;
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.setInt(1, diachiID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String sonha = rs.getString("Sonha");
                String tinh = rs.getString("TinhID");
                String huyen = rs.getString("HuyenID");
                String xa = rs.getString("XaID");
                String email = rs.getString("Email");
                String phoneNumber = rs.getString("PhoneNumber");
                diachi = new Diachi(diachiID, name, sonha, tinh, huyen, xa, email, phoneNumber);
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return diachi;
    }

    public List<Xa> getAllXaFromHuyenName(String huyenName) {
        String sqlInsert = "SELECT        XaPhuongThiTran.*, QuanHuyen.name AS Expr1\n" +
                "FROM            QuanHuyen INNER JOIN\n" +
                "                         TinhThanhPho ON QuanHuyen.matp = TinhThanhPho.matp INNER JOIN\n" +
                "                         XaPhuongThiTran ON QuanHuyen.maqh = XaPhuongThiTran.maqh\n" +
                "\t\t\t\t\t\t WHERE QuanHuyen.name=N'" + huyenName + "'";
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            rs = stmt.executeQuery();
            listXa = new ArrayList<>();
            while (rs.next()) {
                String xaid = rs.getString("xaid");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String maqh = rs.getString("maqh");
                Xa xa = new Xa(xaid, name, type, maqh);
                listXa.add(xa);
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return listXa;
    }

    public List<Huyen> getHuyenFromTinhName(String tinhName) {
        String sqlInsert = "SELECT        QuanHuyen.*, TinhThanhPho.name\n" +
                "FROM            QuanHuyen INNER JOIN\n" +
                "                         TinhThanhPho ON QuanHuyen.matp = TinhThanhPho.matp\n" +
                "\t\t\t\t\t\t WHERE TinhThanhPho.name = N'" + tinhName + "'";
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            // stmt.setString(1, tinhName);
            rs = stmt.executeQuery();
            listHuyen = new ArrayList<>();
            while (rs.next()) {
                String maqh = rs.getString("maqh");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String matp = rs.getString("matp");
                Huyen huyen = new Huyen(maqh, name, type, matp);
                listHuyen.add(huyen);
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return listHuyen;
    }

    public List<Tinh> getAllTinh() {
        String sqlInsert = "SELECT * FROM TinhThanhPho";
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            rs = stmt.executeQuery();
            listTinh = new ArrayList<>();
            while (rs.next()) {
                String matp = rs.getString("matp");
                String name = rs.getString("name");
                String type = rs.getString("type");
                Tinh tinh = new Tinh(matp, name, type);
                listTinh.add(tinh);
            }
        } catch (SQLException e) {
            Log.e("ERROR", e.getMessage());
        }
        return listTinh;
    }

}
