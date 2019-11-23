package parksw.app.member.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import parksw.app.member.domain.Member;
import parksw.app.member.repository.MemberRepository;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * MemberServiceTest
 * author: sinuki
 * createdAt: 2019/11/23
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);
        // 롤백을 하면서 insert쿼리를 보기 위한 처리
        //em.flush();
        assertEquals(member, memberRepository.find(savedId));
    }

    @Test
    void 중복_회원_예외() {
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });
    }
}