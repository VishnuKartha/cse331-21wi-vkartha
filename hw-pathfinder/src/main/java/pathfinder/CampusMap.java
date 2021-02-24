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

package pathfinder;

import graph.DirectedLabeledGraph;
import java.util.Collections;
import java.util.HashMap;
import pathfinder.datastructures.Path;

import java.util.Map;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;

import static pathfinder.parser.CampusPathsParser.parseCampusBuildings;
import static pathfinder.parser.CampusPathsParser.parseCampusPaths;

public class CampusMap implements ModelAPI {
    private Map<String,String> buildingsShortToLongName;
    private Map<String,DirectedLabeledGraph.Node<Point>>  buildingsShortNameToNode;
    private DirectedLabeledGraph<Point,Double> dlg;



    public CampusMap(){
        buildingsShortToLongName = new HashMap<>();
        buildingsShortNameToNode = new HashMap<>();
        dlg = new DirectedLabeledGraph<>();

        for(CampusBuilding building :parseCampusBuildings("campus_buildings.tsv")){
            buildingsShortToLongName.put(building.getShortName(),building.getLongName());
            buildingsShortNameToNode.put(building.getShortName(),new DirectedLabeledGraph.Node<>(
                                                                new Point(building.getX(), building.getY())));
        }

        for(CampusPath path :parseCampusPaths("campus_paths.tsv")){
            DirectedLabeledGraph.Node<Point> start = new DirectedLabeledGraph.Node<>(new Point(path.getX1(), path.getY1()));
            DirectedLabeledGraph.Node<Point> end = new DirectedLabeledGraph.Node<>(new Point(path.getX2(), path.getY2()));
            if(!dlg.containsNode(start)){
                dlg.addNode(start);
            }
            if(!dlg.containsNode(end)){
                dlg.addNode(start);
            }
            dlg.addEdge(new DirectedLabeledGraph.Edge<>(path.getDistance(),start,end));

        }

    }
    @Override
    /**
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     */
    public boolean shortNameExists(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
       return buildingsShortToLongName.containsKey(shortName);
    }

    @Override
    /**
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    public String longNameForShort(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        if(!shortNameExists(shortName)){
            throw new IllegalArgumentException();
        }
        return buildingsShortToLongName.get(shortName);
    }

    @Override
    /**
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    public Map<String, String> buildingNames() {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        return Collections.unmodifiableMap(buildingsShortToLongName);
    }

    @Override
    /**
     * Finds the shortest path, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if(startShortName == null || endShortName == null
                || !shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            throw  new IllegalArgumentException();
        } else {
           DirectedLabeledGraph.Node<Point>startNode = buildingsShortNameToNode.get(startShortName);
           DirectedLabeledGraph.Node<Point>endNode = buildingsShortNameToNode.get(endShortName);
            return Dijkstra.findPath(dlg,startNode,endNode);
        }
    }

}
