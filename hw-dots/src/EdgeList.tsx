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

interface EdgeListProps {
    onChange(edges: [[number, number],[number,number],string][]): void;  // called when a new edge list is ready
    size: number;    // size of the grid to display
}

// adds additional state to EdgeList
interface EdgeListState{
    edgeListTextBoxDisplay:string // the text which is displayed on the edge list box
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps,EdgeListState> {
    // initializes state
    constructor(props: EdgeListProps) {
        super(props);
        this.state = ({edgeListTextBoxDisplay: ""})

    }

    // displays the user inputted text in the edge list text box
    onInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const inputString:string = event.target.value;
        this.setState({edgeListTextBoxDisplay: inputString});

    };

    // draws the edges that the user inputs in the edge list text box.
    // alerts the user of bad inputs with a descriptive message.
    drawOnClick = () => {
        let edges:[[number, number],[number,number],string][] = []; // the list of edges which are parsed
        let inputtedEdges:string[] = this.state.edgeListTextBoxDisplay.split("\n");
        for(let i:number = 0; i < inputtedEdges.length;i++){
            let inputEdgeComponents:string[] = inputtedEdges[i].split(" ");
            if(inputEdgeComponents.length > 3){
                alert("Line " + i + ": Extra portion of the line or an extra space.");
            } else if (inputEdgeComponents.length < 3){
                alert("Line " + i + ": Missing a portion of the line, or missing a space.");
            } else { //inputEdge has 3 fields

                let point1Strings:string[] = inputEdgeComponents[0].split(",");
                let point2Strings:string[] = inputEdgeComponents[1].split(",");

                // makes sure that the points have the correct elements in them
                if(point1Strings.length != 2) {
                    alert("Line " + i + ": wrong number of parts to the first coordinate should be of form (x,y)");
                } else if (point2Strings.length != 2){
                    alert("Line " + i + ": wrong number of parts to the second coordinate should be of form (x,y)");
                } else {
                    //parse the start point coordinates of the edge
                    let x1: number = Number.parseInt(point1Strings[0]);
                    let y1: number = Number.parseInt(point1Strings[1])

                    //parse the end point coordinates of the edge
                    let x2: number = Number.parseInt(point2Strings[0]);
                    let y2: number = Number.parseInt(point2Strings[1]);

                    // checks if the coordinates in the points are valid
                    if(Number.isNaN(x1) ||Number.isNaN(y1) || Number.isNaN(x2)  || Number.isNaN(y2)) {
                        alert("Line " + i + ": invalid coordinates(contain non-number characters)");
                    } else if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
                        alert("Line " + i + ": Coordinate(s) contains negative value(s)");
                        break;
                    } else {
                        let point1: [number, number] = [x1, y1];
                        let point2: [number, number] = [x2, y2];
                        let colorString: string = inputEdgeComponents[2];
                        edges[i] = [point1, point2, colorString];
                    }
                }
            }

        }
        this.props.onChange(edges);
    };

    // clears the drawn edges when the user clicks clear
    clearOnClick = () => {
       this.props.onChange([]);
    };

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange= {this.onInputChange}
                    value={this.state.edgeListTextBoxDisplay}
                /> <br/>
                <button onClick={this.drawOnClick}>Draw</button>
                <button onClick={this.clearOnClick}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;