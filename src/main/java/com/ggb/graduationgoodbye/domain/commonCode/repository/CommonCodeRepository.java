package com.ggb.graduationgoodbye.domain.commonCode.repository;

import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommonCodeRepository {
    private final SqlSession mysql;

    public List<CommonCode> findUniversityAll(){
        return mysql.selectList("CommonCodeMapper.findUniversityAll");
    }

    public Optional<CommonCode> findByUniversity(String name){
        return Optional.ofNullable(mysql.selectOne("CommonCodeMapper.findByUniversity",name));
    }

    public List<CommonCode> findMajorAll(){
        return mysql.selectList("CommonCodeMapper.findMajorAll");
    }

    public Optional<CommonCode> findByMajor(String name){
        return Optional.ofNullable(mysql.selectOne("CommonCodeMapper.findByMajor",name));
    }
}
