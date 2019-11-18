package parksw.app.member.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * MemberRepositoryTest
 * author: sinuki
 * createdAt: 2019/11/09
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    void testMember() {
//        // given
//        Member member = new Member();
//        member.setUsername("parksw");
//        // when
//        Long savedId = memberRepository.save(member);
//        Member foundMember = memberRepository.find(savedId);
//        // then
//        Assertions.assertThat(foundMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(foundMember.getUsername()).isEqualTo(member.getUsername());
    }
}