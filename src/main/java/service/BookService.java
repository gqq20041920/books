package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.sxt.web.servlet.entity.BookDB;
import com.sxt.web.servlet.entity.HistoryDB;
import com.sxt.web.servlet.entity.UserDB;

import book.BookDao;
import book.HistoryDao;
import book.UserDao;
import utils.C3p0Tool;
import utils.DateUtils;
import utils.MyException;
import utils.PageTool;

public class BookService {
	private BookDao bookDao=new BookDao();
	private HistoryDao historyDao=new HistoryDao();
	private UserDao userDao=new UserDao();
	
	//图书分页查询
	 public PageTool<BookDB>listByPage(String currentPage,String pageSize,String word,Integer order){
		 return bookDao.listByPage(currentPage, pageSize,word,order);
	 }
	 
	 public List<BookDB> list(String bookName)
	 {
		 return bookDao.list(bookName,null);
	 }
	 public Integer addBook(BookDB bookDB) {
		 return bookDao.addBook(bookDB);
	 }
	 public Integer updBook(BookDB bookDB) {
		 return bookDao.updBook(bookDB);
	 }
	 public int delBook(String bid)
	 {
		 return bookDao.delBook(bid); 
	 }
   //用户借阅图书
	public void borrowBook(UserDB userDB, String bid) throws SQLException {
	//获取数据库的连接
		Connection conn=C3p0Tool.getConnection();
		try {
			//设置事务不自动提交
		conn.setAutoCommit(false);
			//根据bid获取完整的图书信息
			List<BookDB>list=bookDao.list(null, bid);
			BookDB bookDB=list.get(0);
			//保证用户数据和数据库数据同步更新
			userDB=userDao.getList(userDB).get(0);
			//t_history创建图书借阅历史记录
			HistoryDB historyDB=new HistoryDB();
			historyDB.setUid(userDB.getUid());
			historyDB.setName(userDB.getName());
			historyDB.setAccount(userDB.getAccount());
			historyDB.setBid(bookDB.getBid());
			historyDB.setBookName(bookDB.getBookName());
			// 借书时间
			LocalDateTime beginTime = LocalDateTime.now();
			historyDB.setBeginTime(beginTime);
			// 还书时间 = 借书时间 + 用户借阅天数
			LocalDateTime endTime = beginTime.plusDays(userDB.getLendNum());
			historyDB.setEndTime(endTime);
			historyDB.setStatus(1);//正在借阅
			historyDao.addHistory(historyDB,conn);
					
			 //t_book改变图书库存book.num--,图书的被借阅次数加一book.tmes++
			Integer num=bookDB.getNum();
			//库存判断
			if(num<=0)
			{
				throw new MyException("库存不足");
	
			}
			
			bookDB.setNum(--num);
			Integer times=bookDB.getTimes();
			bookDB.setTimes(++times);
			bookDao.changeNum(bookDB,conn);
			//t_user用户借阅数量加1 user.times++ max_num--
//				throw new MyException("我的异常");
				userDB.setTimes(userDB.getTimes()+1);
				//可借阅数量判断
				if(userDB.getMaxNum()<=0)
				{
					throw new MyException("借阅次数已满");
				}
				userDB.setMaxNum(userDB.getMaxNum()-1);
				userDao.updNum(userDB,conn);
				//事务提交
				conn.commit();
		}catch(Exception e) {
			//事务回滚
			
			conn.rollback();
			//判断自定义异常
			if(e instanceof MyException) {
				throw new MyException(e.getMessage());
			}else {
				e.printStackTrace();
				throw new MyException("借阅失败");
			}
			
			
		}
	
	}
	//图书归还
	public void backBook(String hid) throws SQLException {
		//获取数据库的连接
			Connection conn=C3p0Tool.getConnection();
			try {
				//设置事务不自动提交
			conn.setAutoCommit(false);
				//根据hid获取historyDB,修改status为2
			HistoryDB historyDB=historyDao.list(hid).get(0);
			historyDB.setStatus(2);
			historyDao.updHistory(historyDB,conn);
			
			    //根据historyDB获取bid
			Integer bid=historyDB.getBid();
			    //根据bid获取图书信息。修改库存+1
			BookDB bookDB=bookDao.list(historyDB.getBookName(), bid+"").get(0);
			bookDB.setNum(bookDB.getNum()+1);
			bookDao.changeNum(bookDB,conn);
			//根据historyDB获取用户account
			String account=historyDB.getAccount();
			//根据获取uid用户信息，修改max_num+1
			UserDB userDB=new UserDB();
			userDB.setAccount(account);
			userDB=userDao.getList(userDB).get(0);
			userDB.setMaxNum(userDB.getMaxNum()+1);
			userDao.updNum(userDB,conn);
					conn.commit();
			}catch(Exception e) {
				//事务回滚
				
				conn.rollback();
				//判断自定义异常
				if(e instanceof MyException) {
					throw new MyException(e.getMessage());
				}else {
					e.printStackTrace();
					throw new MyException("还书失败");
				}
				
				
			}
		
		}
}
