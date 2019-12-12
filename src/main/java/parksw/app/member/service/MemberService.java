package parksw.app.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parksw.app.member.domain.Member;
import parksw.app.member.repository.MemberRepository;

import java.util.List;

/**
 * MemberService
 * author: sinuki
 * createdAt: 2019/11/23
 **/
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // @Transaction의 readOnly 디폴트 값은 false
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.find(id);
        member.setName(name);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> members = memberRepository.findByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member find(Long memberId) {
        return memberRepository.find(memberId);
    }
}
