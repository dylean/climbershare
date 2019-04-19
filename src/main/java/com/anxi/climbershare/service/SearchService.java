package com.anxi.climbershare.service;

import com.anxi.climbershare.pojo.Question;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:AnXi
 */
@Service
public class SearchService {
    //solr服务器地址
    private static final String SOLR_URL = "http://127.0.0.1:8983/solr/demo2";
    private HttpSolrClient client = new HttpSolrClient.Builder(SOLR_URL).build();
    private static final String QUESTION_TITLE = "question_title";
    private static final String QUESTION_CONTENT = "question_content";

    public List<Question> searchQuestion(String keyword, int offset, int count,
                                         String hlPre, String hlPos) throws Exception{
        List<Question> questionList = new ArrayList<>();

        //搜索设置
        SolrQuery query = new SolrQuery(keyword);
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
        query.set("hl.fl",QUESTION_TITLE+","+QUESTION_CONTENT);
        query.setFields("title","content");
        QueryResponse response = client.query(query);
        //取出信息
        for (Map.Entry<String, Map<String, List<String>>> entry : response.getHighlighting().entrySet()) {
            Question q = new Question();
            q.setId(Integer.parseInt(entry.getKey()));

            //问题题目
            if (entry.getValue().containsKey(QUESTION_TITLE)) {
                List<String> titleList = entry.getValue().get(QUESTION_TITLE);
                if (titleList.size() > 0) {
                    q.setTitle(titleList.get(0));
                }
            }
            //问题内容
            if (entry.getValue().containsKey(QUESTION_CONTENT)) {
                List<String> contentList = entry.getValue().get(QUESTION_CONTENT);
                if (contentList.size() > 0) {
                    q.setContent(contentList.get(0));
                }
            }
            questionList.add(q);
        }
        return questionList;
    }

    public boolean addQuestionIndex(int id,String title,String content) throws Exception{
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",id);
        document.setField(QUESTION_TITLE,title);
        document.setField(QUESTION_CONTENT,content);
        UpdateResponse response = client.add(document,1000);
        return response != null && response.getStatus() == 0;
    }


}
