package com.biscuit.b1.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.biscuit.b1.model.ChoiceVO;
import com.biscuit.b1.model.CinemaVO;
import com.biscuit.b1.model.MovieInfoVO;
import com.biscuit.b1.model.TheaterVO;
import com.biscuit.b1.model.TimeInfoVO;

@Repository
public class AdminDAO {

	@Inject
	private SqlSession sqlSession;
	private final static String NAMESPACE = "adminMapper.";

	public List<CinemaVO> adminLocSelect(CinemaVO cinemaVO) {
		return sqlSession.selectList(NAMESPACE + "adminLocSelect", cinemaVO);
	}

	public MovieInfoVO movie_num(MovieInfoVO movieInfoVO) {
		return sqlSession.selectOne(NAMESPACE + "movie_num", movieInfoVO);
	}

	public TheaterVO theater_num(TheaterVO theaterVO) {
		return sqlSession.selectOne(NAMESPACE + "theater_num", theaterVO);
	}

	public int timeInfoInsertA(ChoiceVO choiceVO) {
		return sqlSession.insert(NAMESPACE + "timeInfoInsertA", choiceVO);
	}

	public int timeInfoInsertB(ChoiceVO choiceVO) {
		return sqlSession.insert(NAMESPACE + "timeInfoInsertB", choiceVO);
	}

	public int timeInfoInsertC(ChoiceVO choiceVO) {
		return sqlSession.insert(NAMESPACE + "timeInfoInsertC", choiceVO);
	}

	public int seq_plus() {
		return sqlSession.update(NAMESPACE + "seq_plus");
	}

	public List<MovieInfoVO> movieList() {
		return sqlSession.selectList(NAMESPACE + "movieList");
	}

	public int cinemaInsert(CinemaVO cinemaVO) {
		return sqlSession.insert(NAMESPACE + "cinemaInsert", cinemaVO);
	}

	public int theaterInsert(CinemaVO cinemaVO) {
		return sqlSession.insert(NAMESPACE + "theaterInsert", cinemaVO);
	}
	
	//list
	public List<CinemaVO> cinemaList() {
		return sqlSession.selectList(NAMESPACE+"cinemaList");
	}

	public List<ChoiceVO> movieTimeList() {
		return sqlSession.selectList(NAMESPACE+"movieTimeList");
	}

}
