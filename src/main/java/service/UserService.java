package service;

import java.util.List;

import com.sxt.web.servlet.entity.UserDB;

import book.UserDao;
import utils.PageTool;

//用户业务层
public class UserService {
	private UserDao userDao=new UserDao();
	//登录
	public UserDB login(String account,String password) {
		return userDao.login(account, password);
	}
	//添加用户
	public Integer addUser(UserDB userDB) {
		return userDao.addUser(userDB);
	}
 public PageTool<UserDB> list(String currentPage,String pageSize,Integer order){
	 return (PageTool<UserDB>) userDao.list(currentPage,pageSize,order);
 }
 
 public List<UserDB>getList(UserDB userDB){
	 return userDao.getList(userDB);
 }
 //管理员修改用户信息
 public Integer updUser(UserDB userDB){
	 return userDao.updUser(userDB);
 }
 //删除
 public Integer delUser(Integer  uid){
	 return userDao.delUser(uid);
 }
}
