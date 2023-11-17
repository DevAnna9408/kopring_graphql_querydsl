package kr.co.anna.domain.repository;

import kr.co.anna.domain.model.NewMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewMemberRepository extends JpaRepository<NewMember, Integer> {
}
