package com.anxi.climbershare.service;

import com.anxi.climbershare.dao.QuestionDao;
import com.anxi.climbershare.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import com.anxi.climbershare.utils.RedisCacheUtil;
import com.anxi.climbershare.utils.filter.SensitiveWordFilter;

import java.util.List;

/**
 * @Author:AnXi
 */
@Service
public class QuestionService {
    @Autowired
    RedisCacheUtil redisCacheUtil;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    SensitiveWordFilter sensitiveWordFilter;

    /**
     * 发布问题
     * @param question
     * @return
     */
    public int postQuestion(Question question){
        //HTML过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));

        //敏感词过滤
        question.setContent(sensitiveWordFilter.filter(question.getContent()));
        question.setTitle(sensitiveWordFilter.filter(question.getTitle()));
        //...


        //发布
        return  questionDao.insertQuestion(question) > 0 ? question.getId() : 0;
    }

    /**
     * 根据id查找问题
     * @param id
     * @return
     */
    public Question findQuestionById(int id){

        return  questionDao.getById(id);
    }

    /**
     * 查找最近的问题
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<Question> findLatestQuestion(int userId,int offset,int limit){

        return questionDao.selectLatestQuestionsByUserId(userId,offset,limit);
    }

    /**
     * 查找最近的问题
     * @param offset
     * @param limit
     * @return
     */
    public List<Question> findLatestQuestion(int offset,int limit){

        return questionDao.selectLatestQuestions(offset,limit);
    }

    public int updateCommentCount(int id, int count) {
        return questionDao.updateCommentCount(id, count);
    }

    public int updateStatus(int id, int commentId) {
        return questionDao.updateStatus(id, commentId);
    }


}
