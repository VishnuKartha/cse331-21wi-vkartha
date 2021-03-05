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
                                 // once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't any
}

interface EdgeListState{
    edgeListTextBoxDisplay:string
    edges:[[number, number],[number,number],string][]
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps,EdgeListState> {
    constructor(props: EdgeListProps) {
        super(props);
        this.state = ({edgeListTextBoxDisplay: "",edges:[]})
    }
    onInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const inputString:string = event.target.value;
        this.setState({edgeListTextBoxDisplay: inputString});

    };

    drawOnClick = () => {
        this.setState({edges: []});
        let inputtedEdges:string[] = this.state.edgeListTextBoxDisplay.split("\n");
        for(let i:number = 0; i < inputtedEdges.length;i++){
            console.log("INPUT:" + inputtedEdges[i]);

            let inputEdgeComponents:string[] = inputtedEdges[i].split(" ");
            if(inputEdgeComponents.length != 3){
                alert("Line " + i + ": Extra portion of the line or an extra space.");
            } else { //inputEdge has 3 fields
                let point1Strings:string[] = inputEdgeComponents[0].split(",");
                let point2Strings:string[] = inputEdgeComponents[1].split(",");
                let x1:number = Number.parseInt(point1Strings[0]);
                let y1:number = Number.parseInt(point1Strings[1])
                let x2:number = Number.parseInt(point2Strings[0]);
                let y2:number = Number.parseInt(point2Strings[1]);
                if(x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0 ) {
                    alert("Line " + i + ": Coordinate(s) contains negative value(s)");
                } else {
                    let point1: [number, number] = [x1, y1];
                    let point2: [number, number] = [x2, y2];
                    let colorString: string = inputEdgeComponents[2];
                    this.state.edges[i] = [point1, point2, colorString];
                    console.log("STORED:" + this.state.edges[i]);
                }
            }

        }
        this.props.onChange(this.state.edges);
    };

    clearOnClick = () => {
       this.setState({edges: []});
       this.props.onChange(this.state.edges);
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