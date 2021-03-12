/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package campuspaths;

import campuspaths.utils.CORSFilter;
import java.util.Map;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Route;
import spark.Spark;

import com.google.gson.Gson;

import spark.Response;


import java.util.ArrayList;
import java.util.List;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();

        CampusMap map = new CampusMap();
        Spark.get("/getBuildings", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String,String> names=  map.buildingNames();
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(names);
                return jsonResponse;
            }
        });



        // Handles Path finding
        Spark.get("/getPath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {

                String startShortName = request.queryParams("startShortName");
                String endShortName = request.queryParams("endShortName");
                if(startShortName == null || endShortName == null ||
                        !map.shortNameExists(startShortName)||!map.shortNameExists(endShortName))  {
                    // You can also have a message in "halt" that is displayed in the page.
                    Spark.halt(400, "invalid start and end buildings inputted");
                }
                Path<Point> shortestPath = map.findShortestPath(startShortName,endShortName);
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(shortestPath);
                return jsonResponse;
            }
        });
    }

}


