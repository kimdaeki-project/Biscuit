package com.biscuit.b1.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.biscuit.b1.model.ChoiceVO;
import com.biscuit.b1.model.CinemaVO;
import com.biscuit.b1.model.MovieInfoVO;
import com.biscuit.b1.model.TheaterVO;
import com.biscuit.b1.model.TimeInfoVO;
import com.biscuit.b1.service.MovieSelectService;

@Controller
@RequestMapping("/movie/**")
public class MovieController {
	
	@Inject
	private MovieSelectService movieSelectService;
	
	@GetMapping("movieSelect")
	public ModelAndView movieSelect(CinemaVO cinemaVO, TimeInfoVO timeInfoVO) throws Exception {
		List<MovieInfoVO> movieTitle = movieSelectService.movieTitleSelect();
		List<CinemaVO> movieLoc = movieSelectService.movieLocSelect();
		cinemaVO.setCinema_loc("서울");
		List<CinemaVO> movieCinema = movieSelectService.movieCinemaSelect(cinemaVO);
		

		ModelAndView mv = new ModelAndView();
		mv.addObject("movieTitle", movieTitle);
		mv.addObject("movieLoc", movieLoc);
		mv.addObject("movieCinema", movieCinema);
		
		mv.setViewName("movie/movieSelect");
		
		return mv;
	}
	
	@GetMapping("locSelect")
	//@ResponseBody
	public ModelAndView locSelect(CinemaVO cinemaVO, TimeInfoVO timeInfoVO, MovieInfoVO movieInfoVO) throws Exception {
		//영화선택
		System.out.println(cinemaVO.getCinema_loc());
		List<CinemaVO> ar = movieSelectService.movieCinemaSelect(cinemaVO);
		
		for(CinemaVO a : ar) {
			System.out.println(a.getCinema_name());
		}
		
		//선택한 영화에 따른 영화관
		movieInfoVO.setMovieInfo_num(1);
		List<ChoiceVO> cr = movieSelectService.movieChoice(movieInfoVO);
		
		for(ChoiceVO c : cr) {
			System.out.println(c.getMovieInfo_num());
			System.out.println(c.getCinema_num());
			System.out.println(c.getTheater_num());			
		}
		
		
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("common/cineme_result");
		mv.addObject("result", ar);
		mv.addObject("selectResult", cr);
		
		return mv;
	}
	
	
	@GetMapping("dateSelect")
	public ModelAndView dateSelect(TheaterVO theaterVO) throws Exception {
		//날짜선택
		System.out.println(theaterVO.getCinema_num());
		
		List<TimeInfoVO> movieDateSelect = movieSelectService.movieDateSelect(theaterVO);
		
		for(TimeInfoVO a : movieDateSelect){
			System.out.println(a.getTimeInfo_date().substring(0, 10));
			a.setTimeInfo_date(a.getTimeInfo_date().substring(0, 10));
		}
		
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/date_result");
		mv.addObject("result", movieDateSelect);
		
		return mv;
	}
	
	@GetMapping("timeSelect")
	public ModelAndView timeSelect(TheaterVO theaterVO) throws Exception {
		//시간선택
		System.out.println(theaterVO.getCinema_num());
		System.out.println(theaterVO.getMovieInfo_num());
		System.out.println(theaterVO.getTheater_num());
		
		
		List<TimeInfoVO> dateSelect =  movieSelectService.movieTimeSelect(theaterVO);
		
		for(TimeInfoVO a : dateSelect){
			System.out.println("수정"+a.getTimeInfo_date());
			System.out.println("수정"+a.getTimeInfo_start());
			
			
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/time_result");
		mv.addObject("result", dateSelect);
		
		return mv;
		
	}

}
