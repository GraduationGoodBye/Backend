package com.ggb.graduationgoodbye.domain.member.entity;

import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(builderMethodName = "testBuilder")
@Setter
public class TestMember extends Member {

}