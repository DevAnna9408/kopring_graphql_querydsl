package kr.co.anna.domain.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.anna.domain.model.NewMember;
import kr.co.anna.domain.repository.NewMemberRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewMemberQuery implements GraphQLQueryResolver {

    private final NewMemberRepository newMemberRepository;

    public NewMemberQuery(NewMemberRepository newMemberRepository) {
        this.newMemberRepository = newMemberRepository;
    }

    /**
     * Http Method: POST
     * Header: Content-Type: application/json
     * Body :  { "query": "{ findAllNewMembers { id, name, email } }" }
     * **/
    public List<NewMember> findAllNewMembers() {
        return newMemberRepository.findAll();

    }

}
