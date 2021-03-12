import Segment from "./Segment";
import Point from "./Point";

// Path interface
export default interface Path {
    cost:number;
    start:Point;
    path:Segment[];
}

