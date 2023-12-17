package repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import sqlConnect.Connect;

public class UserRepository {
	
	//method untuk mendapatkan detail/data dari user berdasarkan id usernya dari database
	//akan mengembalikan 1 data user dengan id user yang diinginkan
	public static User getUserDetail(Integer userID) {
		Connect db = Connect.getConnection();//mendapatkan koneksi ke database
		
		//membuat query
		String query = "SELECT * FROM user WHERE userID = '%d'";
		String queryExecute = String.format(query, userID);//mengisi %d dalam query dengan user id yang diterima
		
		//menjalankan query dan mengambil hasilnya
		ResultSet res = db.executeQuery(queryExecute);
		
		User getUser = null; //membuat getUset dengan tipe data User
		try {
			while(res.next()) {//hasil yang sudah diambil dimasukkan ke variabel sementara
				Integer currUserID = res.getInt(1);
				String currUsername = res.getString(2);
				Integer currUserAge = res.getInt(4);
				String currUserRole = res.getString(5);
				
				//dari variabel sementara, dibuat sebuah object User baru yang dimasukan kedalam getUser
				getUser = new User(currUserID, currUsername, currUserAge, currUserRole);
			}
		} catch (SQLException e) {
			//Menangkap dan menangani exception jika terjadi kesalahan SQL
			e.printStackTrace();
		}
		
		return getUser;//mengambalikan getUser
	}
}