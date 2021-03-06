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

interface GridProps {
    size: number;    // size of the grid to display
    width: number;   // width of the canvas on which to draw
    height: number;  // height of the canvas on which to draw
    edges: [[number, number],[number,number],string][] // the edges to draw on the grid
}

interface GridState {
    backgroundImage: any,  // image object rendered into the canvas (once loaded)
}

/**
 *  A simple grid with a variable size
 *
 *  Most of the assignment involves changes to this class
 */
class Grid extends Component<GridProps, GridState> {

    canvasReference: React.RefObject<HTMLCanvasElement> // the reference to the canvas which is used for drawing

    // sets the initial state
    constructor(props: GridProps) {
        super(props);
        this.state = {
            backgroundImage: null  // An image object to render into the canvas.
        };
        this.canvasReference = React.createRef();
    }

    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        const background = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./image.jpg";
    }

    // redraws the canvas
    redraw = () => {
        if (this.canvasReference.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvasReference.current.getContext('2d');
        if (ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }

        // First, let's clear the existing drawing so we can start fresh:
        ctx.clearRect(0, 0, this.props.width, this.props.height);

        // Once the image is done loading, it'll be saved inside our state, and we can draw it.
        // Otherwise, we can't draw the image, so skip it.
        if (this.state.backgroundImage !== null) {
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }

        // Draw all the dots.
        const coordinates = this.getCoordinates();
        for (let coordinate of coordinates) {
            this.drawCircle(ctx, coordinate);
        }

        // Draw the edges on the canvas
        for(let edge of this.props.edges) {
            this.drawEdge(ctx,edge);
        }
    };

    /**
     * Returns an array of coordinate pairs that represent all the points where grid dots should
     * be drawn.
     */
    getCoordinates = (): [number, number][] => {
        let result:[number, number][] = [];
        let index:number = 0;
        for(let i:number = 1 ; i <=this.props.size;i++){
            for(let j:number = 1; j <= this.props.size;j++) {
                result[index] = [i *(this.props.width /(this.props.size +1)),
                                j * this.props.height /(this.props.size + 1)];
                index++;
            }
        }
        return result;
    };

    // draws a scaled circle on the canvas with the given context, at the given coordinate
    drawCircle = (ctx: CanvasRenderingContext2D, coordinate: [number, number]) => {
        ctx.fillStyle = "white";
        // Generally use a radius of 4, but when there are lots of dots on the grid (> 50)
        // we slowly scale the radius down so they'll all fit next to each other.
        const radius = Math.min(4, 100 / this.props.size);
        ctx.beginPath();
        ctx.arc(coordinate[0], coordinate[1], radius, 0, 2 * Math.PI);
        ctx.fill();
    };

    // draws an line on the canvas with the given context, using the given edge
    drawEdge = (ctx: CanvasRenderingContext2D, edge: [[number, number],[number,number],string])=> {

        // the factors used to scale the coordinates inputted edges to canvas coordinates
        let xScaleFactor:number = this.props.width /(this.props.size +1);
        let yScaleFactor:number = this.props.height /(this.props.size +1);

        let point1:[number,number] = edge[0];
        let point2:[number,number] = edge[1];

        // calculates the coordinates to draw on the canvas
        let x1ToDraw: number = (point1[0] + 1) * xScaleFactor;
        let y1ToDraw: number = (point1[1] + 1) * yScaleFactor;
        let x2ToDraw: number = (point2[0] + 1) * xScaleFactor;
        let y2ToDraw: number = (point2[1] + 1) * yScaleFactor;

        ctx.lineWidth = 3;
        ctx.strokeStyle = edge[2]; // sets the color of the stroke to what the user inputted in the edge
        ctx.beginPath()
        ctx.moveTo(x1ToDraw, y1ToDraw);
        ctx.lineTo(x2ToDraw, y2ToDraw);
        ctx.stroke()
    };

    render() {
        return (
            <div id="grid">
                <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height}/>
                <p>Current Grid Size: {this.props.size}</p>
            </div>
        );
    }
}

export default Grid;
