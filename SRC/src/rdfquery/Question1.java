
package rdfquery;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;


/**
 *
 * @author shahinatakishiyev
 */
public class Question1 {

    /**
     * @param args the command line arguments 
     */
    public static void Question1()  {
        // TODO code application logic here
        
    

        Model model=ModelFactory.createDefaultModel();
        try {
            //model.read(new FileInputStream("src/a3.txt"),null,"TTL");
            model.read(new FileInputStream("a3.txt"),null,"TTL");
            //teamPlayer(model);
            //teamStadium(model);
            teamStadium2(model);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Question1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     *
     * @param model
     */
    
    
    /**
     *
     * @param model
     */
    public static void teamStadium(Model model){
        String queryString;
        int j = 0;
        ArrayList<String> teams;
        teams = getTeams(model);
        HashSet<String> team_stadium;
        team_stadium = new HashSet<>();

        queryString = 
            "PREFIX fb: <http://rdf.freebase.com/ns/> \n" +
                "PREFIX fbk: <http://rdf.freebase.com/key/> \n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
                "PREFIX c690: <cmput690> " +
                "SELECT ?stadium ?doc \n" +
                "WHERE {?stad c690:hasDocument ?doc . \n" +
                "       ?y fb:sports.sports_team_roster.team ?team . \n" +
                "     ?stad fbk:wikipedia.en ?stadium . \n" +
                "     ?team fbk:wikipedia.en ?team_name \n" +
                "FILTER(regex(?stadium, \".*_Stadium\", \"i\") \n" +
                "&& contains(?doc, ?team_name)) }";
//        queryString = 
//            "PREFIX fb: <http://rdf.freebase.com/ns/> \n" +
//                "PREFIX fbk: <http://rdf.freebase.com/key/> \n" +
//                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
//                "PREFIX c690: <cmput690> " +
//                "SELECT ?doc \n" +
//                "WHERE {{?y c690:hasDocument ?doc . \n" +
//                "FILTER(contains(?doc, \"is a stadium\")) } " +
//                " UNION {?y c690:hasDocument ?doc . \n" +
//                "FILTER(contains(?doc, \"is a football stadium\")) }}";
         
        Query query = QueryFactory.create(queryString);
//        String pattern = "([^\\(\\)\\,\\.]+)(\\(.+\\))?(\\,.+\\,)?\\sis\\sa\\s(football\\s)?stadium";
//        Pattern r = Pattern.compile(pattern);

        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
         
            ResultSet results = qexec.execSelect();
            while(results.hasNext()){

                QuerySolution soln = results.nextSolution();
                String stadium = soln.get("stadium").toString();
                String doc = soln.get("doc").toString();
                for (int i = 0; i < teams.size(); i++) {
			if(doc.contains(teams.get(i))){
                            team_stadium.add(teams.get(i).trim() + "belongs to" + stadium.trim());
                            
                            
                            
                        }
		}
                j++;
//                 Matcher m = r.matcher(stadium);
//                if (m.find( )) {
//                    System.out.println(m.group(1) );
//                 }
                
            }
            try {
                
                FileWriter fw = new FileWriter("Question1.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(team_stadium.toString());
                bw.close();
                fw.close();
                
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            
  
        }
    }
    /**
     *
     * @param model
     * @return 
     */
    public static ArrayList getTeams(Model model){
        String queryString;
        ArrayList<String> ar = new ArrayList<>();
        queryString = 
            "PREFIX fb: <http://rdf.freebase.com/ns/> \n" +
            "PREFIX fbk: <http://rdf.freebase.com/key/> \n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
            "PREFIX c690: <cmput690> " +
            "SELECT ?team_name \n" +
            "WHERE {?y fb:sports.sports_team_roster.team ?team . \n" +
            "     ?team fbk:wikipedia.en ?team_name }";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {

            ResultSet results = qexec.execSelect();
            while(results.hasNext()){

                QuerySolution soln = results.nextSolution();
                String team_name = soln.get("team_name").toString();
                ar.add(team_name.replace("_", " ").replace("$002E", "\\.").replace("$002C", ",").replace("$0027", "'").replace("$00ED", "í").replace("$00E9", "é").replace("$00E0", "à").replace("$00FA", "ú").replace("$00F3", "ó"));
                
            }
        }
        Set<String> hs = new HashSet<>();
        hs.addAll(ar);
        ar.clear();
        ar.addAll(hs);
        return ar;
    }
    
    public static ArrayList teamStadium2(Model model){
        String queryString;
        ArrayList<String> ar = new ArrayList<>();
        ArrayList<String> stadiums;
        stadiums = getStadiums(model);
        //System.out.println(stadiums);
//        queryString = 
//            "PREFIX fb: <http://rdf.freebase.com/ns/> \n" +
//            "PREFIX fbk: <http://rdf.freebase.com/key/> \n" +
//            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
//            "PREFIX c690: <cmput690> " +
//            "SELECT   ?stadium_name\n" +
//            "WHERE {?x fbk:wikipedia.en ?stad . \n" +
//                  
//            "?stad fbk:wikipedia.en ?stadium_name ." +
//            "}";
//        Query query = QueryFactory.create(queryString);
//        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
//
//            ResultSet results = qexec.execSelect();
//            while(results.hasNext()){
//
//                QuerySolution soln = results.nextSolution();
//                String stad_name = soln.get("stadium_name").toString();
//                ar.add(stad_name.replace("_", " "));
//                
//            }
//        }
//        Set<String> hs = new HashSet<>();
//        hs.addAll(ar);
//        ar.clear();
//        ar.addAll(hs);
        return ar;
    }
    /**
     *
     * @param model
     * @return 
     */
    public static ArrayList getStadiums(Model model){
        String queryString;
        ArrayList<String> ar = new ArrayList<>();
        // queryString = 
        //    "PREFIX fb: <http://rdf.freebase.com/ns/> \n" +
        //      "PREFIX fbk: <http://rdf.freebase.com/key/> \n" +
        //      "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
        //     "PREFIX c690: <cmput690> " +
        //    "SELECT ?doc \n" +
        //    "WHERE {{?y c690:hasDocument ?doc . \n" +
        //        "FILTER(contains(?doc, \"is a stadium\")) } " +
        //       " UNION {?y c690:hasDocument ?doc . \n" +
        //       "FILTER(contains(?doc, \"is a football stadium\")) }}";
        
                queryString = 
           "PREFIX fb: <http://rdf.freebase.com/ns/> \n" +
              "PREFIX fbk: <http://rdf.freebase.com/key/> \n" +
             "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n" +
               "PREFIX c690: <cmput690> " +
              "SELECT ?doc \n" +
              "WHERE {?y c690:hasDocument ?doc . \n" +             
                "FILTER(contains(?doc, \"stadium\")) } ";
         
        Query query = QueryFactory.create(queryString);
       String  pattern = "([^\\(\\)\\,\\.]+)(\\(.+\\))?(\\,.+\\,)?\\sis\\s(a\\s|a\\sfootball\\s|the\\shome\\s|a\\smulti\\-purpose\\s|a\\smulti\\-use\\s)?stadium";
       Pattern r = Pattern.compile(pattern);
       ArrayList<String> teams;
       teams = getTeams(model);
       String  teamPattern = "(the|currently|also) home (stadium |ground )?(of|to) (";
       teamPattern = teams.stream().map((i) -> i.replace("$002E", "\\.").replace("$002C", ",").replace("$0027", "'").replace("$00ED", "í").replace("$00E9", "é").replace("$00E0", "à").replace("$00FA", "ú").replace("$00F3", "ó").replace("$", "\\$")+"|").reduce(teamPattern, String::concat);
       teamPattern = teamPattern.substring(0, teamPattern.length()-1) + ")";
       //System.out.println(teamPattern);
       Pattern teamPatternR;
        teamPatternR = Pattern.compile(teamPattern);
        
        ArrayList<String> output = new ArrayList<String>();
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {

            ResultSet results = qexec.execSelect();
            while(results.hasNext()){

                QuerySolution soln = results.nextSolution();
                //String stadium = soln.get("stadium").toString();
                String doc = soln.get("doc").toString();
                //ar.add(doc.replace("_", " "));
                
                Matcher m = r.matcher(doc);
                if (m.find( ) && !"".equals(m.group(1).trim()) && !"It".equals(m.group(1).trim().substring(0,2)) && m.group(1).trim().length()>3) {
                    Matcher teamM = teamPatternR.matcher(doc);
                    
                    output.add(m.group(1).replace("\n", "")+ '\t'+ "belongs to" + '\t' +(teamM.find( ) ? teamM.group(4).replace("\n", ""):"") );
                    ar.add(m.group(1).trim().replace("\n", ""));
                 }
                
            }
        }

        try {
                
                System.out.println("cmput690w16a3_q1_Atakishiyev.tsv created");
                FileWriter fw = new FileWriter("cmput690w16a3_q1_Atakishiyev.tsv", false);
                BufferedWriter bw = new BufferedWriter(fw);
                for(String o : output) {
                    bw.write(o +"\n");
                }
                bw.close();
                fw.close();
                
            }
            catch(IOException e) {
                e.printStackTrace();
            }

        Set<String> hs = new HashSet<>();
        hs.addAll(ar);
        ar.clear();
        ar.addAll(hs);
        return ar;
    }
}
