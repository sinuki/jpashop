package parksw.app.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import parksw.app.member.domain.Address;
import parksw.app.member.domain.Member;
import parksw.app.member.service.MemberService;

import javax.validation.Valid;

/**
 * MemberController
 * author: sinuki
 * createdAt: 2019/12/07
 **/
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(@Valid MemberForm form, BindingResult bind) {
        if (bind.hasErrors())   return "members/createMemberForm";

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(new Address(form.getCity(), form.getStreet(), form.getZipcode()));

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("members")
    public String list(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "members/memberList";
    }
}
