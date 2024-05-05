package kr.co.dglee.lecture.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dglee.lecture.domain.Member;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	@Transactional
	void testMember() throws Exception {
		// given
		Member member = new Member();
		member.setUsername("memberA");

		// when
		Long savedId = memberRepository.save(member);
		Member findMember = memberRepository.find(savedId);

		// then
		Assertions.assertEquals(findMember.getId(), member.getId());
		Assertions.assertEquals(findMember.getUsername(), member.getUsername());
		Assertions.assertEquals(findMember, member);
	}
}
