package book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sxt.web.servlet.entity.BookDB;
import com.sxt.web.servlet.entity.HistoryDB;

import utils.C3p0Tool;
import utils.PageTool;

public class HistoryDao {
	private QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
	   //开启驼峰自动转换
	  BeanProcessor bean=new GenerousBeanProcessor();
	 RowProcessor processor=new BasicRowProcessor(bean);
	//添加图书借阅记录
	public Integer addHistory(HistoryDB historyDB,Connection conn) throws SQLException {
		QueryRunner queryRunner=new QueryRunner();
	    String sql = "insert into t_history(uid,name,account,bid,book_name,begin_time,end_time,status) values(?,?,?,?,?,?,?,?)";
	    Object[] params = {
	        historyDB.getUid(),
	        historyDB.getName(),
	        historyDB.getAccount(),
	        historyDB.getBid(),
	        historyDB.getBookName(),
	        historyDB.getBeginTime(),
	        historyDB.getEndTime(),
	        historyDB.getStatus(),
	    };
	    return queryRunner.update(conn,sql, params);
	    
	}
	//借阅历史记录查询
	 public PageTool<HistoryDB>listByPage(String currentPage,String pageSize,Integer uid,Integer status){
		
	    	try {
	    		StringBuffer listSql=new StringBuffer("select *");
	    		StringBuffer countSql=new StringBuffer("select count(*)");//多表联查
	    		StringBuffer sql=new StringBuffer("from t_history where 1=1");
	    		List<Object> params = new ArrayList<Object>();
	    		if(uid!=null)
	    		{
	    			sql.append(" and uid= ?");
	    			params.add(uid);
	    		}
	    		if(status!=null)
	    		{
	    			sql.append(" and status= ?");
	    			params.add(status);
	    		}
	    		//获取总记录数
	    		Long total=queryRunner.query(countSql.append(sql).toString(), new ScalarHandler<Long>(),params.toArray());
	    		//初始化分页工具
	    		PageTool<HistoryDB> pageTools=new PageTool<HistoryDB>(total.intValue(),currentPage,pageSize);
	    		sql.append(" order by begin_time desc limit ?,?");
	    		params.add(pageTools.getStartIndex());
	    		params.add(pageTools.getPageSize());
	    		//当前页的数据
	    		
	    		List<HistoryDB>list=queryRunner.query(listSql.append(sql).toString(), new BeanListHandler<HistoryDB>(HistoryDB.class,processor),params.toArray());
	    	 pageTools.setRows(list);
	    	 System.out.println(pageTools);
	    	 return pageTools;
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
			return new PageTool<HistoryDB>(0,currentPage,pageSize);
	    	}
	 //无分页查询
	 public List<HistoryDB> list(String hid){
		 StringBuffer sql=new StringBuffer("select * from t_history where 1=1 ");
		 List<Object>params=new ArrayList<Object>();
		
		 
		 if(hid!=null&&hid!="") {
			 sql.append("and hid= ?");
			 params.add(hid);
		 }
		 try {
			return queryRunner.query(sql.toString(), new BeanListHandler<HistoryDB>(HistoryDB.class,processor), params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	 }
	 
	 //修改图书借阅历史记录
	    public Integer updHistory(HistoryDB historyDB,Connection conn) throws SQLException {
	    	QueryRunner qr=new QueryRunner();
	    	String sql="update t_history set status=? where hid=?";
	    	Object[]params= {historyDB.getStatus(),historyDB.getHid()};
	    	return qr.update(conn,sql,params);
	    	
	    }
	    
	  //修改还书事假
	    public Integer updHistory(HistoryDB historyDB) throws SQLException {
	    	
	    	String sql="update t_history set end_time=? where hid=?";
	    	Object[]params= {historyDB.getEndTime(),historyDB.getHid()};
	    	return queryRunner.update(sql,params);
	    	
	    }
	
}
