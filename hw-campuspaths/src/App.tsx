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

import React, {Component} from 'react';
import Path from "./Path";
import Point from "./Point";
import Segment from "./Segment";
import Map from "./Map"
import PathSelector from "./PathSelector";

import "./App.css";

interface AppState {
    path:Path
    emptyPath:Path
}

class App extends Component<{}, AppState> {

    // returns a Point with the given x and y
    createPoint = (x:number,y:number):Point => ({
        x:x,
        y:y
    })

    // returns a Segment with the start Point, end Point, and cost
    createSegment = (start:Point,end:Point,cost:number ):Segment => ({
        start: start,
        end:end,
        cost:cost
    })

    // returns a Path with the given start Point, list of segments, and cost
    createPath = (start:Point, segments:Segment[],cost:number): Path => ({
        start: start,
        path: segments,
        cost:cost

    })

    // updates the path in App's state to be
    // the path between the given startBuildingShortName and endBuildingShortName, via the spark server.
     sendRequestForPath = async (startBuildingShortName:string, endBuildingShortName:string) => {
        try {
            let url:string = `http://localhost:4567/getPath?startShortName=${startBuildingShortName}&endShortName=${endBuildingShortName}`;
            let response = await fetch(url);
            if (!response.ok) {
                alert("Error in request for Path!");
                return;
            }
            let parsed = await response.json() as Path;
             this.setState({
                path:parsed
            });
        } catch (e) {
            alert("Error in request for Path!");
        }
    }

    // sets the starting state
    constructor(props: any) {
        super(props);

        // creates the empty path object
        let emptyPoint:Point = this.createPoint(0,0);
        let emptyCost:number = 0;
        let emptySegments:Segment[] = [this.createSegment(emptyPoint, emptyPoint,emptyCost)];
        let empty:Path = this.createPath(emptyPoint,emptySegments,emptyCost);
        this.state = {
            path:empty,
            emptyPath:empty
        };
    }

    // clears the path
    clear = () => {
        this.setState({
            path: this.state.emptyPath
        });
    }

    render() {
        return (
             <div>
                 <PathSelector onChangePath = {this.sendRequestForPath} onChangeClear={this.clear}/>
                 <Map path={this.state.path} />
             </div>
        );
    }
}

export default App;
