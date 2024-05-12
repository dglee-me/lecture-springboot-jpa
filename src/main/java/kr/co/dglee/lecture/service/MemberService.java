package kr.co.dglee.lecture.service;

import java.util.List;
import kr.co.dglee.lecture.domain.Member;
import kr.co.dglee.lecture.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  /**
   * 회원 가입
   *
   * @param member
   * @return
   */
  @Transactional
  public Long join(Member member) {
    validateDuplicateMember(member);

    memberRepository.save(member);
    return member.getId();
  }

  /**
   * 회원 전체 조회
   *
   * @return
   */
  @Transactional(readOnly = true)
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  /**
   * 회원 조회
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public Member findById(Long id) {
    return memberRepository.findById(id);
  }

  private void validateDuplicateMember(Member member) {
    List<Member> findMembers = memberRepository.findByName(member.getName());
    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  /**
   * 회원 수정
   *
   * @param id
   * @param name
  */
  @Transactional
  public void update(Long id, String name) {
    Member member = memberRepository.findById(id);
    member.setName(name);
  }
}
