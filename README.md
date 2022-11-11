### Knowledge-and-Data-Engineering

## Student information query system for entertainment/study places in Fingal County Council
*Motivation:* Help students aware of the recreational activities in FCC and help them with time management

Uplifting Part:
1. prepare the csv dataset file
2. create the xx_mapping.ttl file base on the dataset
3. create the xx.properties file to run the r2rml.jar
4. use the command line: java -jar r2rml/r2rml.jar xx.properties to do the uplifting

Sparql queries:
Question 1:

SELECT ?x ?name
WHERE {
	?a a ns0:RecreationFacilities.
    ?x a ns0:BikeStand.
    ?a ns0:hasName ?name.
    ?a ns0:hasArea ?area1.
    ?x ns0:hasArea ?area2.
  FILTER(?name = "Swords Library" && ?area1 = ?area2 )
}Limit 1


Reference:
1. datasets source - https://data.gov.ie/dataset/..
