package cn.edu.pku.sei.SnowView.servlet;
import cn.edu.pku.sei.SnowView.utils.PostUtil;
import graphdb.extractors.parsers.word.corpus.WordSegmenter;
import graphsearcher.SearchResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.graphdb.GraphDatabaseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;

import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import graphsearcher.GraphSearcher;

import java.util.Set;

/**
 * Created by Administrator on 2017/5/26.
 */
public class CypherQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WordSegmenter.demo("邮件").forEach(n->{System.out.println(n);});
        request.setCharacterEncoding("UTF-8");
        System.out.println("start query");
        String query = request.getParameter("query");
        String type = request.getParameter("params");
       /* type = new String(type.getBytes("GBK") , "GBK");
        System.out.println(type);*/

        File databasePath = new File("I:\\graphdb\\graphdb");
        GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(databasePath);

        GraphSearcher searcher = new GraphSearcher(db);
        SearchResult results = searcher.query(type);
        JSONObject result = results.toJSON(db);

        System.out.println("end query");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(result.toString());


        //region original code
        /*try {
            JSONObject jsobj = new JSONObject();
            JSONObject paraobj = new JSONObject(type);
            jsobj.put("query", query);
            jsobj.put("params", paraobj);
            System.out.println(query);
            System.out.println(paraobj.toString());
            String p = PostUtil.sendPost("http://neo4j:1@127.0.0.1:7474/db/data/cypher", jsobj.toString());
            //String p = PostUtil.sendGet("http://neo4j:123@127.0.0.1:7474/db/data/cypher");
            //System.out.println(p);
            response.setContentType("application/json");
            response.getWriter().print(p);
        }catch(Exception e){
            System.out.println(e.getStackTrace());
        }*/
        //endregion
    }
}