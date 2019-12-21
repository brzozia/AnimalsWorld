package main;

import static java.lang.System.out;
 import java.lang.Long;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class World {

    public static void main(String[] args) throws IOException, ParseException {

        JSONParser parser = new JSONParser();

        try //(FileReader reader = new FileReader("src/assets/parameters.json"))
        {
            Object object = parser
                    .parse(new FileReader("src/assets/parameters.json"));

            JSONObject jsonObject = (JSONObject)object;
            int width = ((Long) jsonObject.get("width")).intValue();
            int height = ((Long) jsonObject.get("height")).intValue();
            int startEnergy =((Long) jsonObject.get("startEnergy")).intValue();
            int moveEnergy = ((Long) jsonObject.get("moveEnergy")).intValue();
            int plantEnergy = ((Long) jsonObject.get("plantEnergy")).intValue();
            double jungleRatio = 1/((Long) jsonObject.get("jungleRatio")).doubleValue();
            int daysOfSimulation = ((Long) jsonObject.get("daysOfSimulation")).intValue();
            int noOfAnimals = ((Long) jsonObject.get("noOfAnimals")).intValue();

            out.println("width is:"+ width + "  " + height +jungleRatio);

            WorldMap Earth =new WorldMap(width, height, jungleRatio);
            Earth.Life(daysOfSimulation,noOfAnimals,startEnergy,moveEnergy);

        }
        catch(FileNotFoundException ex){
            out.println( "File not found"); }
        catch(ParseException ex){ex.printStackTrace(); }

    }
}
