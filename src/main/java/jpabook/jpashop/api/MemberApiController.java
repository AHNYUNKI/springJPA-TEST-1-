package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * Member Entity를 이용해서 객체를 사용하면 큰 장애가 발생할 수 있다. (Entity 객체는 외부에 절대 노출하면 안된다.)
     * 그래서 DTO 객체를 만들어 사용해야한다.
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid MemberRequest request) {

        Member member = new Member();
        member.setName(request.name);

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);

    }

    @Data
    static class MemberRequest {
        @NotEmpty
        public String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
