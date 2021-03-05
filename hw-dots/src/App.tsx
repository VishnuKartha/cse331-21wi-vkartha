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
import EdgeList from "./EdgeList";
import Grid from "./Grid";
import GridSizePicker from "./GridSizePicker";

// Allows us to write CSS styles inside App.css, any any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    gridSize: number;  // size of the grid to display
    edges: [[number, number],[number,number],string][] //the edges used to store data internally
    // requiredGridSize: number // the grid size required to be able to draw the edges
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);
        this.state = {
            gridSize: 4,
            edges: [],
        };
    }

    updateGridSize = (newSize: number) => {
        this.setState({
            gridSize: newSize,
        });
    };

    updateEdges = (newEdges : [[number, number],[number,number],string][]) => {

        console.log("edges: " + newEdges);

        let requiredGridSize:number =this.getRequiredGridSize(newEdges);
        console.log("actualSize: " + this.state.gridSize)

        if (requiredGridSize > this.state.gridSize) {
            let customMessage: string = ""
            if (requiredGridSize <= 100) {
                customMessage = " grid must be at least size " + requiredGridSize;
            } else {
                customMessage = " grid cannot fit inputted edges"
                this.setState({
                    edges: []
                });
            }
            alert("cannot draw edges:" + customMessage + "Board Cleared");
        } else {
            this.setState({
                edges: newEdges
            });
        }
    }

     getRequiredGridSize = (edges : [[number, number],[number,number],string][]):number => {
        let globalMax:number = 0;
        for(let edge of edges) {
            let currMax: number = Math.max(edge[0][0], edge[0][1], edge[1][0], edge[1][1]) + 1;
            globalMax = Math.max(globalMax, currMax);
        }
        console.log("GLOBAL MAX: " + globalMax);
         return globalMax

     }

    render() {
        const canvas_size = 500;
        return (
            <div>
                <p id="app-title">Connect the Dots!</p>
                <GridSizePicker value={this.state.gridSize.toString()} onChange={this.updateGridSize}/>
                <Grid edges={this.state.edges} size={this.state.gridSize} width={canvas_size} height={canvas_size}/>
                <EdgeList onChange={this.updateEdges}size= {this.state.gridSize}/>
            </div>

        );
    }
}

export default App;