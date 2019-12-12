package com.biscuit.b1.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biscuit.b1.model.MemberVO;
import com.biscuit.b1.service.MemberService;
import com.biscuit.b1.util.Pager;

@Controller
@RequestMapping("member/**")
public class MemberController {

	@Inject
	private MemberService memberService;

	@GetMapping("memberLogout")
	public String memberLogout(HttpSession session) throws Exception {
		// 로그아웃
		session.invalidate();
		return "redirect:../";
	}

	@PostMapping("memberDelete")
	public ModelAndView memberDelete(String pwCheck, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String sessionPw = memberVO.getPw();
		System.out.println("세션비밀번호" + sessionPw);
		System.out.println("폼비밀번호" + pwCheck);
		if (sessionPw.equals(pwCheck)) {
			int result = memberService.memberDelete(memberVO);
			String msg = "탈퇴에 실패했습니다.";
			String path = "./memberMyPage";
			if (result > 0) {
				msg = "정상적으로 탈퇴하였습니다.";
				path = "../";
				session.invalidate();
			}
			mv.addObject("msg", msg);
			mv.addObject("path", path);
			mv.setViewName("common/common_result");
			return mv;
		} else {
			mv.addObject("msg", "비밀번호가 일치하지 않습니다");
			mv.addObject("path", "./memberMyPage");
			mv.setViewName("common/common_result");
			return mv;
		}
	}

	@GetMapping("memberDelete")
	public void memberDelete() throws Exception {
	}

	@PostMapping("memberUpdate")
	public ModelAndView memberUpdate(MemberVO memberVO) throws Exception {
		ModelAndView mv = new ModelAndView();
		int result = memberService.memberUpdate(memberVO);
		String msg = "업데이트 실패";
		String path = "./memberMyPage";
		if (result > 0) {
			msg = "업데이트 완료";
			path = "../";
		}

		mv.addObject("msg", msg);
		mv.addObject("path", path);
		mv.setViewName("common/common_result");
		return mv;
	}

	@GetMapping("memberUpdate")
	public void memberUpdate() throws Exception {

	}

	@GetMapping("memberJoin")
	public void memberJoin() throws Exception {
	}

	@PostMapping("memberJoin")
	public ModelAndView memberJoin(MemberVO memberVO) throws Exception {
		ModelAndView mv = new ModelAndView();
		int result = memberService.memberJoin(memberVO);
		String msg = "회원가입 실패";
		if (result > 0)
			msg = "회원가입 완료";

		mv.addObject("msg", msg);
		mv.addObject("path", "../");
		mv.setViewName("common/common_result");
		return mv;
	}

	@GetMapping("memberLogin")
	public void memberLogin() {

	}

	@GetMapping("memberJoin2")
	public void memberJoin2() {

	}

	@PostMapping("memberLogin")
	public ModelAndView memberLogin(MemberVO memberVO, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		memberVO = memberService.memberLogin(memberVO);
		String msg = "로그인 실패";
		String path = "./memberLogin";
		if (memberVO != null) {
			msg = "로그인 완료";
			path = "../";
			session.setAttribute("member", memberVO);
		}

		mv.addObject("msg", msg);
		mv.addObject("path", path);
		mv.setViewName("common/common_result");
		return mv;
	}

	@PostMapping("memberManagementAdd")
	public ModelAndView memberManagementAdd(MemberVO memberVO) throws Exception{
		System.out.println(memberVO.getId());
		ModelAndView mv = new ModelAndView();
		int result = memberService.memberJoin(memberVO);
		String msg = "멤버추가 실패";
		if (result > 0)
			msg = "멤버추가 완료";
		mv.addObject("msg", msg);
		mv.addObject("path", "./memberManagement");
		mv.setViewName("common/common_result");
		return mv;
	}
	
	@GetMapping("memberManagement")
	public ModelAndView memberManagement(Pager pager) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<MemberVO> memberVOs = memberService.memberManagement(pager);
		for (MemberVO memberVO2 : memberVOs) {
			memberVO2.setBirth(memberVO2.getBirth().substring(0, 10));
			memberVO2.setSignIn_date(memberVO2.getSignIn_date().substring(0, 10));
		}
		System.out.println(pager.getSearch());
		mv.addObject("members", memberVOs);
		mv.addObject("pager", pager);
		return mv;
	}

	@PostMapping("memberManagementUpdate")
	public ModelAndView memberManagementUpdate(MemberVO memberVO) throws Exception{
		ModelAndView mv = new ModelAndView();
		int result = memberService.memberManagementUpdate(memberVO);
		String msg = "업데이트 실패";
		if (result > 0) {
			msg = "업데이트 완료";
		}
		mv.addObject("msg", msg);
		mv.addObject("path", "./memberManagement");
		mv.setViewName("common/common_result");
		return mv;
	}
	
	

	@GetMapping("memberManagementDelete")
	public ModelAndView memberManagementDelete(String id, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		int result = memberService.memberManagementDelete(id);
		String msg = "탈퇴에 실패했습니다.";
		if (result > 0) {
			msg = "정상적으로 탈퇴 처리 되었습니다.";
		}
		mv.addObject("msg", msg);
		mv.addObject("path", "./memberManagement");
		mv.setViewName("common/common_result");
		return mv;
	}

	@GetMapping("memberMyPage")
	public void myPage(MemberVO memberVO) throws Exception {

	}

	@GetMapping("idCheck")
	public ModelAndView idCheck(String id) throws Exception {
		ModelAndView mv = new ModelAndView();
		MemberVO memberVO = memberService.idCheck(id);
		if (memberVO == null) {
			mv.addObject("result", 1);
		} else
			mv.addObject("result", 0);
		return mv;
	}

}