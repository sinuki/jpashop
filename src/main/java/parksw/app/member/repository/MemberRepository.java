package parksw.app.member.repository;

import org.springframework.stereotype.Repository;
import parksw.app.member.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * MemberRepository
 * author: sinuki
 * createdAt: 2019/11/09
 **/
@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
