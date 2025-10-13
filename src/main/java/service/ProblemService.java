package service;

import com.sxt.web.servlet.entity.ProblemDB;

import book.ProblemDao;
import utils.PageTool;

public class ProblemService {

	private ProblemDao problemDao = new ProblemDao();
	
	/**
	 * 列表 分页
	 * @return
	 */
	public PageTool<ProblemDB> list(String currentPage, String pageSize, String word, Integer order,Integer uid){
		return problemDao.list(currentPage, pageSize, word, order,uid);
	}
	
	
	/**
	 * 添加问题
	 * @param ProblemDB
	 * @return
	 */
	public Integer addProblem(ProblemDB ProblemDB) {
		return problemDao.addProblem(ProblemDB);
	}
	
	/**
	 * 修改问题
	 * @return
	 */
	public Integer updProblem(ProblemDB ProblemDB) {
		return problemDao.updProblem(ProblemDB);
	}
	
	
	/**
	 * 删除问题
	 * @return 
	 */
	public Integer delProblem(String pid) {
		return problemDao.delBook(pid);
	}
}

