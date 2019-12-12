package parksw.app.member.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import parksw.app.member.domain.Member;
import parksw.app.member.service.MemberService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MemberApiController
 * author: sinuki
 * createdAt: 2019/12/12
 **/
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * bad case
     * 1. 엔티티 정보가 그대로 노출
     * 2. 엔티티를 그대로 넘기고 있으므로 엔티티가 변경되면 API스펙이 변경
     * @return
     */
//    @GetMapping("api/v1/members")
//    public List<Member> memberV1() {
//        return memberService.findAll();
//    }

    /**
     * good case
     * 1. 엔티티 정보를 그대로 노출하지 않음.
     * 2. 엔티티 정보를 그대로 노출하지 않으므로 API스펙의 변경없이 엔티티를 변경할 수 있음
     * @return
     */
    @GetMapping("api/v2/members")
    public Result<List<MemberDTO>> memberV2() {
        List<Member> members = memberService.findAll();
        List<MemberDTO> memDto = members.stream().map(m -> new MemberDTO(m.getName())).collect(Collectors.toList());
        return new Result<>(memDto);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDTO {
        private String name;
    }

    /**
     * bad case
     * 1. 엔티티를 파라미터로 넘기고 있어서 어떤 값을 입력받는지 애매모호함.
     * 2. 엔티티에 프레젠테이션 레이어용 검증 어노테이션이 들어가게 됨. 실무에선 도메인 객체와 파라미터용 객체가 용도별로 다른 경우가 많으므로 케이스에 잘 맞지 않음.
     * @param member
     * @return
     */
//    @PostMapping("api/v1/members")
//    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
//        Long id = memberService.join(member);
//        return new CreateMemberResponse(id);
//    }

    /**
     * good case
     * 1. 도메인 객체와 파라미터 객체를 분리
     * @param memberRequest
     * @return
     */
    @PostMapping("api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest memberRequest) {
        Member member = new Member();
        member.setName(memberRequest.getName());
        return new CreateMemberResponse(memberService.join(member));
    }

    @PutMapping("api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest memberRequest) {
        memberService.update(id, memberRequest.getName());
        Member member = memberService.find(id);
        return new UpdateMemberResponse(id, member.getName());
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    @Data
    static class UpdateMemberRequest {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }
}
