package apps;

import org.apache.commons.io.FileUtils;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import searcher.DocSearcher;
import searcher.graph.GraphSearcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {

	static private String neo4jBoltUrl = null;
	static private Driver neo4jBoltConnection = null;
	static private String exampleFilePath = null;
	static private String lucenePath = null;
	static private GraphSearcher graphSearcher = null;
	static private DocSearcher docSearcher = null;
	static private boolean flag = false;

	public static void init() {
		flag = true;
		List<String> lines = new ArrayList<>();
		try {
			lines = FileUtils.readLines(new File(Config.class.getResource("/").getPath() + "conf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String line : lines) {
			int p = line.indexOf(' ');
			if (p > 0) {
				String pre = line.substring(0, p);
				String suf = line.substring(p + 1);
				if (pre.equals("neo4jBoltUrl"))
					neo4jBoltUrl = suf;
				if (pre.equals("dataPath")) {
					exampleFilePath = suf+"/qaexamples";
					lucenePath = suf+"/index";
				}
			}
		}
		neo4jBoltConnection= GraphDatabase.driver(neo4jBoltUrl, AuthTokens.basic("neo4j", "123"));
		graphSearcher = new GraphSearcher();
		docSearcher = new DocSearcher(graphSearcher);
	}

	static public String getExampleFilePath() {
		if (!flag)
			init();
		return exampleFilePath;
	}

	static public String getLucenePath() {
		if (!flag)
			init();
		return lucenePath;
	}

	static public GraphSearcher getGraphSearcher() {
		if (!flag)
			init();
		return graphSearcher;
	}

	static public DocSearcher getDocSearcher() {
		if (!flag)
			init();
		return docSearcher;
	}

	public static Driver getNeo4jBoltDriver() {
		if (!flag)
			init();
		return neo4jBoltConnection;
	}

}