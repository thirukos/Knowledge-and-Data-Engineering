package org.example;

import org.apache.jena.*;
import org.apache.jena.ontology.OntModel;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import io.github.galbiston.geosparql_jena.configuration.GeoSPARQLConfig;

import java.io.InputStream;


public class LoadOnto {


    public static OntModel Ontologymodel = null;
    public static String localSource = "test1.ttl";

    public static Model loadAllClassesOnt() {
        String inputFileName = localSource;
        GeoSPARQLConfig.setupMemoryIndex();
        Model model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open(inputFileName);
        if (in == null)
        {
            throw new IllegalArgumentException("File: " + inputFileName + " not found");
        }

        model.read(in, "","TTL");

        return model;
    }


    public static String queryResult(String queryString) {

        Model m = loadAllClassesOnt();


        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, m);
        ResultSet results = queryExecution.execSelect();

        String resultTxt = ResultSetFormatter.asText(results);

        queryExecution.close();
        System.out.println(resultTxt);


        return resultTxt;


    }

    public static String getQueryResult(int id){
        String query = "";
        switch(id){
            case 0:
                query="PREFIX owl: <http://www.w3.org/2002/07/ow1#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX omgeo:<http://www.ontotext.com/owlim/geo#>\n" +
                        "SELECT ?x ?name2\n" +
                        "WHERE {\n" +
                        "?a a ns0:RecreationFacilities.\n" +
                        "?x a ns0:BikeStand.\n" +
                        "?a ns0:hasName ?name1. \n" +
                        "?a ns0:hasArea ?area1.\n" +
                        "?x ns0:hasArea ?area2.\n" +
                        " ?x ns0:hasName ?name2.\n" +
                        "FILTER(?name1 = \"Swords Library\" && ?area1 = ?area2 )\n" +
                        "}Limit 3"  ;

                break;

            case 1:
                query="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX geof: <http://www.opengis.net/def/function/geosparql/> \n" +
                        "PREFIX uom: <http://www.opengis.net/def/uom/OGC/1.0/>\n" +
                        "SELECT DISTINCT ?name ?distance\n" +
                        "WHERE {\n" +
                        "?a a ns0:RecreationFacilities.\n" +
                        "?x a ns0:BikeStand.\n" +
                        "?a ns0:hasArea ?area.\n" +
                        "?a ns0:asWKT ?point1.\n" +
                        "?a ns0:hasLatitude ?Lat1.\n" +
                        "?a ns0:hasLongtitude ?Long1.\n" +
                        "?x ns0:hasLatitude ?Lat2.\n" +
                        "?x ns0:hasLongtitude ?Long2.\n" +
                        "?x ns0:hasName ?name.\n" +
                        "?x ns0:asWKT ?point2.\n" +
                        "    BIND(geof:distance(?point1,?point2,uom:metre) as ?distance )\n" +
                        " FILTER(?area = \"Swords\" && ?distance < 20000)\n" +
                        "} LIMIT 1";
                break;

            case 2:
                query="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX geof: <http://www.opengis.net/def/function/geosparql/> \n" +
                        "SELECT DISTINCT ?name ?hasTennis ?HeritageVenue ?cafe ?BicycelParking ?Parking ?Toilets\n" +
                        "WHERE {\n" +
                        "\t?a a ns0:BikeStand.\n" +
                        "    ?x a ns0:PubParkingPlayGround.\n" +
                        "    ?a ns0:extendInfo ?extendInfo.\n" +
                        "    ?a ns0:hasArea ?area.\n" +
                        "    ?x ns0:hasName ?name.\n" +
                        "    ?x ns0:hasTennis ?hasTennis.\n" +
                        "    ?x ns0:hasHeritageVenue ?HeritageVenue.\n" +
                        "    ?x ns0:hasCafe ?cafe.\n" +
                        "    ?x ns0:hasBicycleParking ?BicycelParking.\n" +
                        "    ?x ns0:hasParking ?Parking.\n" +
                        "    ?x ns0:hasToilets ?Toilets.\n" +
                        "      FILTER (?extendInfo = ?x && ?area = \"Swords\")\n" +
                        "  }";
                break;

            case 3:
                query="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX geof: <http://www.opengis.net/def/function/geosparql/> \n" +
                        "SELECT (COUNT(DISTINCT *) as ?count)\n" +
                        "WHERE  {{\n" +
                        "?a a ns0:BleeperBikesParking.\n" +
                        "?a ns0:hasArea ?area1.\n" +
                        "FILTER(?area1 = \"Swords\")\n" +
                        "} union {\n" +
                        "?x a ns0:BikeStand.\n" +
                        "?x ns0:hasArea ?area2.\n" +
                        "FILTER(?area2 = \"Swords\")\n" +
                        "} union {\n" +
                        " ?s a ns0:PubParkingPlayGround.\n" +
                        "?s ns0:hasBicycleParking ?bicycleParking.\n" +
                        " FILTER(?bicycleParking = \"Yes\")\n" +
                        "}\n" +
                        "}";
                break;
            case 4:
                query="PREFIX owl: <http://www.w3.org/2002/07/ow1#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX omgeo:<http://www.ontotext.com/owlim/geo#>\n" +
                        "SELECT ?x ?name\n" +
                        "WHERE { {\n" +
                        "?x a ns0:RecreationFacilities.\n" +
                        "?x ns0:hasSportType ?sportType.\n" +
                        "?x ns0:hasName ?name.\n" +
                        "FILTER(?sportType = \"Playground, MUGA, Outdoor Gyms, Tennis\")\n" +
                        "} union {\n" +
                        "?x a ns0:PubParkingPlayGround.\n" +
                        "?x ns0:hasName ?name.\n" +
                        "?x ns0:hasTennis ?hasTennis.\n" +
                        "FILTER(?hasTennis = \"Yes\")}\n" +
                        "}";
                break;
            case 5:
                query="PREFIX owl: <http://www.w3.org/2002/07/ow1#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX omgeo:<http://www.ontotext.com/owlim/geo#>\n" +
                        "SELECT (COUNT(DISTINCT ?name1) + COUNT(DISTINCT ?name2) as ?count) WHERE {\n" +
                        "?a a ns0:RecreationFacilities.\n" +
                        "?a ns0:hasActivityType ?type1.\n" +
                        "?a ns0:hasName ?name1.\n" +
                        " ?x a ns0:PlayingPitch.\n" +
                        "?x ns0:hasActivityType ?type2.\n" +
                        "?x ns0:hasName ?name2.\n" +
                        "?x ns0:sameActivityTypeWithPC ?sportTypeArea\n" +
                        "FILTER (?type1 = \"Running Track\" && ?a = ?sportTypeArea)\n" +
                        "}";
                break;
            case 6:
                query="PREFIX owl: <http://www.w3.org/2002/07/ow1#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX omgeo:<http://www.ontotext.com/owlim/geo#>\n" +
                        "SELECT ?name2 ?sportType ?activityType ?activityDescription ?chain ?fee ?openning ?accessibility ?access ?hasTennis ?HeritageVenue ?cafe ?BicycelParking ?Parking ?Toilets\n" +
                        "WHERE { {\n" +
                        "?a a ns0:RecreationFacilities.\n" +
                        "?a ns0:hasSportType ?sportType.\n" +
                        "?a ns0:hasName ?name1.\n" +
                        "?a ns0:hasFee ?fee.\n" +
                        "?a ns0:hasActivityType ?activityType.\n" +
                        "?a ns0:hasActivityDescription ?activityDescription.\n" +
                        "?a ns0:haschain ?chain.\n" +
                        "?a ns0:hasOpening ?openning.\n" +
                        "?a ns0:hasAccessiblity ?accessibility.\n" +
                        "?a ns0:hasAccess ?access.\n" +
                        "?x a ns0:PubParkingPlayGround.\n" +
                        "?x ns0:hasName ?name2.\n" +
                        "?x ns0:hasTennis ?hasTennis.\n" +
                        "?x ns0:hasHeritageVenue ?HeritageVenue.\n" +
                        "?x ns0:hasCafe ?cafe.\n" +
                        "?x ns0:hasBicycleParking ?BicycelParking.\n" +
                        " ?x ns0:hasParking ?Parking.\n" +
                        "?x ns0:hasToilets ?Toilets.\n" +
                        "FILTER\n" +
                        " (?name1 =\"Swords Town Park\" && ?name2 = \"Swords Town Park\")\n" +
                        "}\n" +
                        "}";
                break;
            case 7:
                query="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX geof: <http://www.opengis.net/def/function/geosparql/> \n" +
                        "PREFIX uom: <http://www.opengis.net/def/uom/OGC/1.0/>\n" +
                        "SELECT DISTINCT ?x ?point2\n" +
                        "WHERE {\n" +
                        "?a a ns0:RecreationFacilities.\n" +
                        "?x a ns0:MobyBikeLocation.\n" +
                        "?a ns0:hasName ?name1.\n" +
                        "?a ns0:asWKT ?point1.\n" +
                        "?x ns0:asWKT ?point2.\n" +
                        "BIND(geof:distance(?point1,?point2,uom:metre) as ?distance )\n" +
                        "FILTER(?name1 = \"Lusk United FC pitches\" && ?distance < 15000 )\n" +
                        "} ORDER BY DESC(?distance) LIMIT 5";
                break;
            case 8:
                query="PREFIX owl: <http://www.w3.org/2002/07/ow1#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX omgeo:<http://www.ontotext.com/owlim/geo#>\n" +
                        "SELECT ?a ?name1\n" +
                        "WHERE {\n" +
                        "?a a ns0:RecreationFacilities.\n" +
                        "?a ns0:hasArea ?area1.\n" +
                        "?a ns0:hasAccessiblity ?accessibility.\n" +
                        "?a ns0:hasName ?name1.\n" +
                        "?x a ns0:GreenSchool.\n" +
                        "?x ns0:hasName ?name2.\n" +
                        "?x ns0:nearBy ?nearByRecreation.\n" +
                        "FILTER(?a = ?nearByRecreation && ?name2 = \"Fingal Community College\" && ?accessibility = \"Wheelchair access\")\n" +
                        "}";
                break;
            case 9:
                query="PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "PREFIX ns0: <http://www.semanticweb.org/26690/ontologies/2022/10/untitled-ontology-2/>\n" +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "\n" +
                        "SELECT ?time ?count\n" +
                        "WHERE {\n" +
                        "    ?x a ns0:CycleCounter.\n" +
                        " ?x ns0:hasTime ?time.\n" +
                        "    ?x ns0:hasMortonStadium ?count1.\n" +
                        " ?x ns0:hasCoastRoad ?count2.\n" +
                        "    ?x ns0:hasCastleknock ?count3.\n" +
                        "    {SELECT distinct ?o4 WHERE {\n" +
                        " ?a a ns0:RecreationFacilities.\n" +
                        "   ?a ns0:hasName ?name.\n" +
                        " ?a ns0:hasActivityType ?activityType.\n" +
                        " ?a ns0:haschain ?chain.\n" +
                        "    ?a ns0:hasArea ?area1.\n" +
                        "   FILTER( ?chain = \"No\"&& ?activityType = \"Running Track\" )\n" +
                        "   BIND(replace(?name, \" \",\"\") AS ?o2)\n" +
                        "   BIND(replace(?o2, \"M\",\"hasM\") AS ?o3)\n" +
                        "   BIND(replace(?o3, \"C\",\"hasC\") AS ?o4)\n" +
                        "} LIMIT 1}\n" +
                        "bind(IF(?o4 = \"hasMortonStadium\",?count1, (IF(?o4 = \"hasCoastRoad\",?coun2,(IF(?o4 = \"hasCastleknock\",?coun3,0)))))  as ?count)\n" +
                        "FILTER(?time > 8 && ?time < 20)\n" +
                        "}ORDER BY DESC(?count) LIMIT 2";
                break;
        }

        return query;
    }

}