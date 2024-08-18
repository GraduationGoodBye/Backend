package com.ggb.graduationgoodbye.domain.member.controller;

import com.ggb.graduationgoodbye.domain.artist.entity.Artist;
import lombok.Getter;




public class PromoteArtistDTO {
    @Getter
    public static class Request{
        private String university;
        private String major;
        private String name;
    }
    @Getter
    public static class Response{
        // member 정보도 함께 반환 필요
//        private Member member;
        private String university;
        private String major;
        private String name;
        private String certificate_url;

        Response(Artist artist){
            this.university = artist.getCommon_university_id().getName();
            this.major = artist.getCommon_major_id().getName();
            this.name = artist.getName();
            this.certificate_url = artist.getCertificate_url();
        }
    }
}
