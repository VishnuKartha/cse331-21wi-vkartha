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
import "./Map.css";
import Path from "./Path";
import Segment from "./Segment";


interface MapProps {
    path:Path   // the path to draw on the map
}

interface MapState {
    backgroundImage: HTMLImageElement | null;
}

class Map extends Component<MapProps, MapState> {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    canvas: React.RefObject<HTMLCanvasElement>;

    // sets the initial state
    constructor(props: MapProps) {
        super(props);
        this.state = {
            backgroundImage: null
        };
        this.canvas = React.createRef();
    }

    // draws the map with no paths on mount
    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
    }

    // redraws the canvas
    redraw = () => {
        if (this.canvas.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvas.current.getContext('2d');
        if (ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }

        // First, let's clear the existing drawing so we can start fresh:
        let canvas = this.canvas.current;
        ctx.clearRect(0,0,canvas.width,canvas.height);

        // let us redraw the background image
        this.drawBackgroundImage();

        // let us draw the path
        this.drawPath(ctx);
    };


    // draws the given path on the map via the canvas context
    drawPath = (ctx: CanvasRenderingContext2D)=> {
        const segmentsToDraw:Segment[] = this.props.path.path;
        for(let currSegment of segmentsToDraw) {
            this.drawSegment(ctx,currSegment);
        }
    };

    // draws a single segment of a path on the map via the canvas context
    drawSegment= (ctx: CanvasRenderingContext2D, segment:Segment)=> {
        ctx.lineWidth = 15;
        ctx.strokeStyle = "red"; // sets the color of the stroke to what the user inputted in the edge
        ctx.beginPath()
        ctx.moveTo(segment.start.x,segment.start.y);
        ctx.lineTo(segment.end.x,segment.end.y);
        ctx.stroke()
    }


    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    drawBackgroundImage() {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");
        //
        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
    }

    render() {
        return (
            <canvas ref={this.canvas}/>
        )
    }
}

export default Map;