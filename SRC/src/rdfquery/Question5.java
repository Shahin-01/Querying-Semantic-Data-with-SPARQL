
package rdfquery;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
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
public class Question5 {

    /**
     * @param args the command line arguments
     */
    public static void Question5() {
        // TODO code application logic here

        Model model = ModelFactory.createDefaultModel();
        HashMap<String, String> teams = new HashMap<String, String>();
        TreeMap<String, Integer> nationalities = new TreeMap<String, Integer>();
        String team;
        String[] split;
        try {
            model.read(new FileInputStream("a3.txt"), null, "TTL");
            String queryString;
            //System.out.println("Completed");
            queryString
                    = "PREFIX fb: <http://rdf.freebase.com/ns/> \n"
                    + "PREFIX fbk: <http://rdf.freebase.com/key/> \n"
                    + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
                    + "PREFIX c690: <cmput690> "
                    + "SELECT  ?team_name ?player_name ?doc \n"
                    + "WHERE {?x fb:sports.sports_team_roster.player ?player . \n"
                    + "       ?x fb:sports.sports_team_roster.team ?team . \n"
                    + "     ?player fbk:wikipedia.en ?player_name . \n"
                    + "     ?team fbk:wikipedia.en ?team_name . \n"
                    + "     ?player c690:hasDocument ?doc \n"
                    + " FILTER (contains(?doc, \"footballer\")) }";

            Query query = QueryFactory.create(queryString);
            String pattern = "is an? (\\w{3,20}) footballer";
            Pattern r = Pattern.compile(pattern);
            ArrayList<String> output = new ArrayList<String>();
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {

                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String team_name = soln.get("team_name").toString();
                    String player_name = soln.get("player_name").toString();
                    String doc = soln.get("doc").toString();
                    Matcher m = r.matcher(doc);
                    if (m.find()) {
                        team = toAscii(team_name);
                        //System.out.println(toAscii(team_name) + " has a " + m.group(1) + " player.");
                        if (teams.containsKey(team)) {
                            if (!teams.get(team).contains(m.group(1))) {
                                teams.put(team, teams.get(team) + "|" + m.group(1));
                            }
                        } else {
                            teams.put(team, m.group(1));
                        }
                    }
                }
                Iterator it = teams.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    //System.out.println(pair.getKey() + " = " + StringUtils.countMatches(pair.getValue().toString(),"|"));
                    if (!nationalities.containsKey(pair.getKey())) {
                        nationalities.put(pair.getKey().toString(), StringUtils.countMatches(pair.getValue().toString(), "|"));
                    }
                    it.remove(); // avoids a ConcurrentModificationException
                }
                SortedSet f = entriesSortedByValues(nationalities);
                //System.out.println(entriesSortedByValues(nationalities));
                int j = 0;
                for (Object s : f) {
                    if (j++ < 120) {
                       output.add(s.toString());
                    }
                }
            }
             try {
                
                System.out.println("cmput690w16a3_q5_Atakishiyev.tsv created");
                FileWriter fw = new FileWriter("cmput690w16a3_q5_Atakishiyev.tsv", false);
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
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Question2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String toAscii(String in) {
        return in.replace("$002F", "/").replace("$00E8", "è").replace("$0028", "(").replace("$0029", ")").replace("$002E", ".").replace("$00E7", "ç").replace("$00F1", "ñ").replace("$0026", "&").replace("$002C", ",").replace("$0027", "'").replace("$00ED", "í").replace("$00E9", "é").replace("$00E0", "à").replace("$00FA", "ú").replace("$00F3", "ó").replace("$00E1", "á").replace("_", " ");
    }

    static <K, V extends Comparable<? super V>>
            SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries;
        sortedEntries = new TreeSet<>(
                (Map.Entry<K, V> e1, Map.Entry<K, V> e2) -> {
                    int res = e2.getValue().compareTo(e1.getValue());
                    return res != 0 ? res : 1;
                });
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

}
