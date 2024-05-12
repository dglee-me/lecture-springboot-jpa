package kr.co.dglee.lecture.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import kr.co.dglee.lecture.domain.Address;
import kr.co.dglee.lecture.domain.Member;
import kr.co.dglee.lecture.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

	private final MemberService memberService;

	/**
	 * Entity를 변수로 받는 방식 (하지말자)
	 *
	 * @param member
	 * @return
	 */
	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	/**
	 * 회원가입 요청을 받는 DTO를 만들어서 사용하는 방식
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

		Member member = new Member();
		member.setName(request.getName());
		member.setAddress(request.getAddress());

		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}

	@PutMapping("/api/v2/members/{id}")
	public UpdateMemberResponse updateMemberV2(
		@PathVariable Long id,
		@RequestBody @Valid UpdateMemberRequest request) {

		memberService.update(id, request.getName());
		Member findMember = memberService.findById(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName());
	}

	/**
	 * Entity를 직접 반환하는 방식 (하지말자)
	 * Entity에 프레젠테이션을 위해 @JsonIgnore 등을 사용하고,
	 * 리스트(배열)을 넘겨주기 때문에 확장할 수 없다. (Count값 추가 등)
	 *
	 * @return
	 */
	@GetMapping("/api/v1/members")
	public List<Member> memberListV1() {
		return memberService.findMembers();
	}

	@GetMapping("/api/v2/members")
	public Result memberListV2() {
		List<Member> findMembers = memberService.findMembers();

		List<MemberDTO> resultMembers = findMembers.stream()
			.map(m -> new MemberDTO(m.getName()))
			.collect(Collectors.toList());

		return new Result(resultMembers.size(), resultMembers);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class Result<T> {
		private int count;
		private T data;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class MemberDTO {
		private String name;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class CreateMemberRequest {
		@NotEmpty
		private String name;

		@Embedded
		private Address address;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class CreateMemberResponse {
		private Long id;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class UpdateMemberRequest {
		private String name;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class UpdateMemberResponse {
		private Long id;
		private String name;
	}
}
