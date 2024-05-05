package kr.co.dglee.lecture.service;

import kr.co.dglee.lecture.domain.Member;
import kr.co.dglee.lecture.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberServiceTest {

  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepository memberRepository;

  @Test
  @DisplayName("회원가입")
  void 회원가입() throws Exception {
    // given
    Member member = new Member();
    member.setName("Kim");

    // when
    Long savedId = memberService.join(member);

    // then
    Assertions.assertEquals(member, memberRepository.findById(savedId));
  }

  @Test
  @DisplayName("회원가입 시 이름이 중복되면 예외가 발생해야한다.")
  void 회원가입_중복_예외() throws Exception {
    // given
    Member member1 = new Member();
    member1.setName("Kim");

    Member member2 = new Member();
    member2.setName("Kim");

    // when
    memberService.join(member1);

    // then
    Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
  }
}