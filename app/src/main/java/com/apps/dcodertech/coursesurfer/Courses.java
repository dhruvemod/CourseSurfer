package com.apps.dcodertech.coursesurfer;

import java.io.Serializable;

/**
 * Created by dhruv on 3/16/2018.
 */

public class Courses implements Serializable {
    String course_prof;
    String course_certifications,course_duration,course_hours,course_institution,course_keywords,course_lang,course_link,course_name,course_provider,course_subject,course_val,sc_url;
    public Courses(){}
    public Courses(String course_val,String course_provider,String course_keywords,String course_name,String course_prof,String course_hours,String course_link,String course_certifications,String course_duration,String course_lang,String course_subject,String sc_url,String course_institution){
        this.course_val=course_val;
        this.course_prof=course_prof;
        this.course_provider=course_provider;
        this.course_keywords=course_keywords;
        this.course_name=course_name;
        this.course_hours=course_hours;
        this.course_link=course_link;
        this.course_certifications=course_certifications;
        this.course_duration=course_duration;
        this.course_lang=course_lang;
        this.course_subject=course_subject;
        this.sc_url=sc_url;
        this.course_institution=course_institution;
    }
    public Courses(String course_val,String course_provider,String course_keywords,String course_name,String course_hours,String course_link,String course_certifications,String course_duration,String course_lang,String course_subject,String sc_url,String course_institution){
        this.course_val=course_val;
        this.course_provider=course_provider;
        this.course_keywords=course_keywords;
        this.course_name=course_name;
        this.course_hours=course_hours;
        this.course_link=course_link;
        this.course_certifications=course_certifications;
        this.course_duration=course_duration;
        this.course_lang=course_lang;
        this.course_subject=course_subject;
        this.sc_url=sc_url;
        this.course_institution=course_institution;
    }
    public Courses(String name,String auth, String company, String provider, String university, String certification, String week, String hours){
        this.course_name=name;
        this.course_provider=provider;
        this.course_institution=university;
        this.course_certifications=certification;
        this.course_duration=week;
        this.course_hours=hours;
        this.course_prof=auth;
        this.course_subject=company;
    }

    public String getCourse_prof() {
        return course_prof;
    }

    public void setCourse_prof(String course_prof) {
        this.course_prof = course_prof;
    }

    public String getCourse_certifications() {
        return course_certifications;
    }

    public String getCourse_duration() {
        return course_duration;
    }

    public String getCourse_hours() {
        return course_hours;
    }

    public String getCourse_institution() {
        return course_institution;
    }

    public String getCourse_keywords() {
        return course_keywords;
    }

    public String getCourse_lang() {
        return course_lang;
    }

    public String getCourse_link() {
        return course_link;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_provider() {
        return course_provider;
    }

    public String getCourse_subject() {
        return course_subject;
    }

    public String getCourse_val() {
        return course_val;
    }

    public String getSc_url() {
        return sc_url;
    }

    public void setCourse_certifications(String course_certifications) {
        this.course_certifications = course_certifications;
    }

    public void setCourse_duration(String course_duration) {
        this.course_duration = course_duration;
    }

    public void setCourse_hours(String course_hours) {
        this.course_hours = course_hours;
    }

    public void setCourse_institution(String course_institution) {
        this.course_institution = course_institution;
    }

    public void setCourse_keywords(String course_keywords) {
        this.course_keywords = course_keywords;
    }

    public void setCourse_lang(String course_lang) {
        this.course_lang = course_lang;
    }

    public void setCourse_link(String course_link) {
        this.course_link = course_link;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setCourse_provider(String course_provider) {
        this.course_provider = course_provider;
    }

    public void setCourse_subject(String course_subject) {
        this.course_subject = course_subject;
    }

    public void setCourse_val(String course_val) {
        this.course_val = course_val;
    }

    public void setSc_url(String sc_url) {
        this.sc_url = sc_url;
    }

}