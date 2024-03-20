package com.github.yumyum.chat.repository;

import com.github.yumyum.chat.entity.Friendship;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FriendRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public FriendRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(Friendship friendship) {
        em.persist(friendship);
    }

    public Optional<Friendship> findByMemberId1(Integer memberId1) {
        Friendship findFriendShip = em.find(Friendship.class, memberId1);
        return Optional.ofNullable(findFriendShip);
    }

    public List<Friendship> findAll() {
        return em.createQuery("select fs from Friendship fs", Friendship.class)
                .getResultList();
    }

    public List<Friendship> findByUsername(String username) {
        return em.createQuery("select fs from Friendship fs where fs.username = :username", Friendship.class)
                .setParameter("username", username)
                .getResultList();
    }
}
