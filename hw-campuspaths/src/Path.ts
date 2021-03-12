import Segment from "./Segment";
import Point from "./Point";
export default interface Path {
    cost:number;
    start:Point;
    path:Segment[];
}

