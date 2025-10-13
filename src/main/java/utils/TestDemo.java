package utils;

import java.sql.SQLException;

import com.sxt.web.servlet.entity.BookDB;
import com.sxt.web.servlet.entity.HistoryDB;
import com.sxt.web.servlet.entity.UserDB;

import book.BookDao;
import book.HistoryDao;
import book.UserDao;

public class TestDemo {
public static void main(String[] args) throws SQLException {
		HistoryDao historyDao=new HistoryDao();
		HistoryDB historyDB=new HistoryDB();
		historyDB.setHid(1);
		historyDB.setStatus(2);
		System.out.println(historyDao.updHistory(historyDB,C3p0Tool.getConnection()));
	}

}
