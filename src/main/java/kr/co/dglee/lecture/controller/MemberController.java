package kr.co.dglee.lecture.controller;

import jakarta.validation.Valid;
import kr.co.dglee.lecture.domain.Address;
import kr.co.dglee.lecture.domain.Member;
import kr.co.dglee.lecture.form.MemberForm;
import kr.co.dglee.lecture.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/members/new")
  public String createForm(Model model) {

    model.addAttribute("memberForm", new MemberForm());

    return "/pages/members/createMemberForm";
  }

  @PostMapping("/members/new")
  public String create(@Valid MemberForm form, BindingResult result) {

    if(result.hasErrors()) {
      return "pages/members/createMemberForm";
    }

    Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

    Member member = new Member();
    member.setName(form.getName());
    member.setAddress(address);

    memberService.join(member);

    return "redirect:/";
  }

  @GetMapping("/members")
  public String memberList(Model model) {

    model.addAttribute("members", memberService.findMembers());
    return "/pages/members/memberList";
  }
}
