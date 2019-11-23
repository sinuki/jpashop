package parksw.app.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import parksw.app.member.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * MemberRepository
 * author: sinuki
 * createdAt: 2019/11/09
 **/
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
