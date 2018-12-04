
package rdfquery;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
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
public class Question3 {

    /**
     * @param args the command line arguments
     */
    public static void Question3() {
        // TODO code application logic here

        Model model = ModelFactory.createDefaultModel();
        try {
            //model.read(new FileInputStream("src/a3.txt"),null,"TTL");
            model.read(new FileInputStream("a3.txt"), null, "TTL");
            teamPlayer(model);
            //teamStadium(model);
            //      teamStadium2(model);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Question2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param model
     */
    public static void teamPlayer(Model model) {
        String queryString;
        //System.out.println("Completed");
        queryString
                = "PREFIX fb: <http://rdf.freebase.com/ns/> \n"
                + "PREFIX fbk: <http://rdf.freebase.com/key/> \n"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
                + "PREFIX c690: <cmput690> "
                + "SELECT  (SAMPLE(?team_name) AS ?teamName) (SAMPLE(?player_name) AS ?playerName) ?manager \n"
                + "WHERE {?x fb:sports.sports_team_roster.player ?player . \n"
                + "       ?x fb:sports.sports_team_roster.team ?team . \n"
                + "       ?mngr fb:soccer.football_team_management_tenure.manager ?manager . \n"
                + "     ?player fbk:wikipedia.en ?player_name . \n"
                + "     ?team fbk:wikipedia.en ?team_name . \n"
                + "     ?mngr fb:soccer.football_team_management_tenure.team ?team . \n"
                + "     ?player c690:hasDocument ?doc \n"
                + " FILTER (contains(?doc, \"Spanish footballer\")) }"
                + " GROUP BY ?manager";

        Query query = QueryFactory.create(queryString);

        ArrayList<String> output = new ArrayList<String>();
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {

            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {

                QuerySolution soln = results.nextSolution();
                String team_name = soln.get("teamName").toString();
                String player_name = soln.get("playerName").toString();
                String manager = soln.get("manager").toString();
                output.add(manager.replace("http://rdf.freebase.com/ns/", "") + " manages " + toAscii(team_name) + " where " + toAscii(player_name) + " plays." );
            }
        }

         try {
                
                System.out.println("cmput690w16a3_q3_Atakishiyev.tsv created");
                FileWriter fw = new FileWriter("cmput690w16a3_q3_Atakishiyev.tsv", false);
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

    public static  String toAscii(String in){
        return in.replace("$002E", ".").replace("$002C", ",").replace("$0027", "'").replace("$00ED", "í").replace("$00E9", "é").replace("$00E0", "à").replace("$00FA", "ú").replace("$00F3", "ó").replace("$00E1", "á").replace("_"," ");
    }

}

/**
 *
 * @param model
 * @return
 */

