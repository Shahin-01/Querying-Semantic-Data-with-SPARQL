
package rdfquery;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
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
public class Question4 {

    /**
     * @param args the command line arguments
     */
    public static void Question4()  {
        // TODO code application logic here

        Model model=ModelFactory.createDefaultModel();
        try {
            //model.read(new FileInputStream("src/a3.txt"),null,"TTL");
            model.read(new FileInputStream("a3.txt"),null,"TTL");
            //teamPlayer(model);
            //teamStadium(model);
            teamStadium2(model);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Question2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  
    
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
          ArrayList<String> output = new ArrayList<String>();

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
            System.out.println(team_stadium);
  
        }
        try {
                
                System.out.println("HEY");
                FileWriter fw = new FileWriter("Question4.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                for(String o : output) {
                    bw.write(o + "\n");
                }
                bw.close();
                fw.close();
                
            }
            catch(IOException e) {
                e.printStackTrace();
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
        for(int i=0; i < stadiums.size(); i++)
            ar.add(stadiums.get(i));
        

        try {
                
                System.out.println("cmput690w16a3_q4_Atakishiyev.tsv created");
                FileWriter fw = new FileWriter("cmput690w16a3_q4_Atakishiyev.tsv", false);
                BufferedWriter bw = new BufferedWriter(fw);
                for(String o : ar) {
                    bw.write(o + "\n");
                }
                bw.close();
                fw.close();
                
            }
            catch(IOException e) {
                e.printStackTrace();
            }
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
              "WHERE {{?y c690:hasDocument ?doc . \n" +             
                "FILTER(contains(?doc, \"stadium\")) } "
                        + "UNION {?y c690:hasDocument ?doc . \n" +             
                "FILTER(contains(?doc, \"named\")) }}";
         
        Query query = QueryFactory.create(queryString);
       String  pattern = "([^\\(\\)\\,\\.]+)(\\(.+\\))?(\\,.+\\,)?\\sis\\s(a\\s|a\\sfootball\\s|the\\shome\\s|a\\smulti\\-purpose\\s|a\\smulti\\-use\\s)?stadium";
       Pattern r = Pattern.compile(pattern);
       ArrayList<String> teams;
       teams = getTeams(model);
       String  teamPattern = "(the|currently|also) home (stadium |ground )?(of|to) (";
       String  namedPattern = " (re)?named.+former.+(president|chairman)";
       teamPattern = teams.stream().map((i) -> i.replace("$002E", "\\.").replace("$002C", ",").replace("$0027", "'").replace("$00ED", "í").replace("$00E9", "é").replace("$00E0", "à").replace("$00FA", "ú").replace("$00F3", "ó").replace("$", "\\$")+"|").reduce(teamPattern, String::concat);
       teamPattern = teamPattern.substring(0, teamPattern.length()-1) + ")";
       //System.out.println(teamPattern);
       Pattern teamPatternR, namedPatternR;
        teamPatternR = Pattern.compile(teamPattern);
        namedPatternR = Pattern.compile(namedPattern);
       
       
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {

            ResultSet results = qexec.execSelect();
            while(results.hasNext()){

                QuerySolution soln = results.nextSolution();
                //String stadium = soln.get("stadium").toString();
                String doc = soln.get("doc").toString();
                //ar.add(doc.replace("_", " "));
                
                Matcher m = r.matcher(doc);
                Matcher named = namedPatternR.matcher(doc);
                Matcher teamM = teamPatternR.matcher(doc);
                if (m.find( ) && named.find( ) && teamM.find() && !"".equals(m.group(1).trim()) && !"It".equals(m.group(1).trim().substring(0,2)) && m.group(1).trim().length()>3) {
                    
                    ar.add(toAscii(teamM.group(4)) + " owns " + toAscii(m.group(1)) + " which was named after a former president.");
                 }
                
            }
        }
        Set<String> hs = new HashSet<>();
        hs.addAll(ar);
        ar.clear();
        ar.addAll(hs);
        return ar;
    }
    public static  String toAscii(String in){
        return in.replace("\n", "").replace("$002E", ".").replace("$002C", ",").replace("$0027", "'").replace("$00ED", "í").replace("$00E9", "é").replace("$00E0", "à").replace("$00FA", "ú").replace("$00F3", "ó").replace("$00E1", "á").replace("_"," ");
    }
}
