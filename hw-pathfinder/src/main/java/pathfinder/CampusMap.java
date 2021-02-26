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

/**
 * CampusMap represents a map of the University of Washington's campus and it implements ModelAPI. The CampusMap
 * stores the names(both short and long)
 * and locations of buildings on the University of Washington Campus. Ths map is represented
 * by a directed labeled graph with nodes which are points on campus and edges which hold the distance between
 * two points.
 *
 * Abstraction Invariant:
 *      All points in the map are unique(the point is not represented twice)
 *      All buildings in the map are unique(the same building is not represented twice)
 **/
public class CampusMap implements ModelAPI {
    /**A mapping from all the buildings' short names to their long names in this campus map. */
    private final  Map<String,String> buildingsShortToLongName;

    /**A mapping from all the buildings' short names to the point which represents their location on this campus map. */
    private final Map<String,DirectedLabeledGraph.Node<Point>>  buildingsShortNameToNode;

    /** A DLG which represents points and the connections between the points on this campus map */
    private final DirectedLabeledGraph<Point,Double> dlg;

    /** The file path for the tsv file which contains information on the buildings on the campus*/
    private static final String buildingsFilePath = "campus_buildings.tsv";

    /** The file path for the tsv file which contains information on the paths between two points on campus*/
    private static final String pathsFilePath = "campus_paths.tsv";

    // Representation Invariant:
    //      All points in the map are unique(the point is not represented twice)
    //      All buildings in the map are unique(the same building name (both short and long) is not represented twice)
    //      buildingsShortToLongName != null
    //      buildingsShortNameToNode != null
    //      dlg != null

    // Abstraction function
    //  AF(r) = a Campus Map g such that
    //  r.dlg represents the map of points and distances between the points of g
    //  the keys of r.buildingsShortToLongName represent the short names of all of the buildings of g
    //  the values of r.buildingsShortToLongName represent the long names of all of the buildings of g
    //  the  r.buildingsShortNameToNode maps the short name of all of the buildings of g to its corresponding point
    //  on the map.




    /** Constructs a new CampusMap
     * @spec.requires {@code buildingsFilePath} is the path to a valid tsv file, {@code pathsFilePath} is the path to a valid tsv file
     * @spec.modifies {@code buildingsShortToLongName}, {@code buildingsShortNameToNode},{@code dlg}
     * @spec.effects  {@code buildingsShortToLongName} will hold mappings from the short name of buildings to the long names
     *                of the building.
     *                {@code buildingsShortNameToNode} will hold a mapping from all the buildings'
     *                short names to the point which represents their location on this campus map.
     *                {@code dlg} will hold the points on campus and the connections between the points in in this campus
     *                map.
     *
     */
    public CampusMap(){
        buildingsShortToLongName = new HashMap<>();
        buildingsShortNameToNode = new HashMap<>();
        dlg = new DirectedLabeledGraph<>();
        loadBuildings();
        loadPaths();
    }


    @Override

    public boolean shortNameExists(String shortName) {
       return buildingsShortToLongName.containsKey(shortName);
    }

    @Override

    public String longNameForShort(String shortName) {
        if(!shortNameExists(shortName)){
            throw new IllegalArgumentException();
        }
        return buildingsShortToLongName.get(shortName);
    }

    @Override

    public Map<String, String> buildingNames() {
        return Collections.unmodifiableMap(buildingsShortToLongName);
    }

    @Override

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


    /**  Loads in the buildings on campus from the tsv file held in {@code buildingsFilePath}.
     *   @spec.modifies {@code buildingsShortToLongName}, {@code buildingsShortNameToNode}
     *   @spec.requires {@code buildingsFilePath} is the path to a valid tsv file
     *   @spec.effects  {@code buildingsShortToLongName} will hold mappings from the short name of buildings to the long names
     *                  of the building. {@code buildingsShortNameToNode} will hold a mapping from all the buildings'
     *                  short names to the point which represents their location on this campus map
     */
    private void loadBuildings() {
        for(CampusBuilding building :parseCampusBuildings(buildingsFilePath)){
            buildingsShortToLongName.put(building.getShortName(),building.getLongName());
            buildingsShortNameToNode.put(building.getShortName(),new DirectedLabeledGraph.Node<>(
                    new Point(building.getX(), building.getY())));
        }
    }

    /**  Loads in the paths between points on the campus from the tsv file held in {@code pathsFilePath}.
     *   @spec.modifies {@code dlg}
     *   @spec.requires {@code pathsFilePath} is the path to a valid tsv file
     *   @spec.effects  {@code dlg} will hold the points on campus and the connections between the points in in this campus
     *                  map.
     */
    private void loadPaths() {
        for(CampusPath path :parseCampusPaths(pathsFilePath)){
            DirectedLabeledGraph.Node<Point> start = new DirectedLabeledGraph.Node<>(new Point(path.getX1(), path.getY1()));
            DirectedLabeledGraph.Node<Point> end = new DirectedLabeledGraph.Node<>(new Point(path.getX2(), path.getY2()));
            if(!dlg.containsNode(start)){
                dlg.addNode(start);
            }
            if(!dlg.containsNode(end)){
                dlg.addNode(end);
            }
            dlg.addEdge(new DirectedLabeledGraph.Edge<>(path.getDistance(),start,end));

        }
    }

    /**
     * Throws an exception if the representation invariant is violated
     */
    private void checkRep() {
        assert(buildingsShortToLongName != null) : "buildingsShortToLongName == null";
        assert(buildingsShortNameToNode != null) : "buildingsShortNameToNode == null";
        assert(dlg != null): "dlg == null";
        // the types used in buildingsShortToLongName are immutable.
        // Thus, we dont have to check uniqueness as it is guaranteed by the Map class.

        // the types used in buildingsShortNameToNode is immutable.
        // Thus, we dont have to check uniqueness as it is guaranteed by the Map class.

        // the types used in dlg are immutable.
        // Thus, we dont have to check uniqueness as it is guaranteed by the DirectedLabeledGraph class.
    }

}
