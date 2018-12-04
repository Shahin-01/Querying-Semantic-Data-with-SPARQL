
package rdfquery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
public class Question2 {

    /**
     * @param args the command line arguments
     */
    public static void Question2() throws IOException {
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
                + "SELECT  ?team_name ?player_name\n"
                + "WHERE {?x fb:sports.sports_team_roster.player ?player . \n"
                + "       ?x fb:sports.sports_team_roster.team ?team . "
                + "     ?player fbk:wikipedia.en ?player_name ."
                + "     ?team fbk:wikipedia.en ?team_name  }";

        Query query = QueryFactory.create(queryString);

        ArrayList<String> output = new ArrayList<String>();

        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {

            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {

                QuerySolution soln = results.nextSolution();
                String team_name = soln.get("team_name").toString();
                String player_name = soln.get("player_name").toString();
                output.add(player_name + " plays for " + team_name);
               
            }
            
        }

        try {
                
                System.out.println("cmput690w16a3_q2_Atakishiyev.tsv created");
                FileWriter fw = new FileWriter("cmput690w16a3_q2_Atakishiyev.tsv", false);
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
     */
    //public static void teamStadium(Model model){
    String queryString;
    int j = 0;
    ArrayList<String> teams;
    //  teams = getTeams(model);
    HashSet<String> team_stadium;
    //  team_stadium = new HashSet<>();

    Query query = QueryFactory.create(queryString);

}

/**
 *
 * @param model
 * @return
 */
