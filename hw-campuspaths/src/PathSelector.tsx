import React, {Component} from 'react';
// Contains "interactive" elements of the app. It includes, user selecting a start and end building, a draw button,
// and a clear button

interface PathSelectorProps {
    onChangePath(startShortName:string,endShortName:string):void // the method called to draw a new path
    onChangeClear():void // the method called to clear the path on the map
}

interface PathSelectorState {
    buildingOptions:JSX.Element[], //the list of options on the drop down menu for selecting buildings
    startShortName:string, // the shortName of the start building selected
    endShortName:string // the shortName of the end building selected
}

class PathSelector extends Component<PathSelectorProps,PathSelectorState> {
    // sets the initial state
    constructor(props: PathSelectorProps) {
        super(props);
        this.state= {
            buildingOptions: [],
            startShortName: "empty",
            endShortName: "empty"
        }
    }

    // gets the information about the buildings on campus on mount
    componentDidMount() {
        this.sendRequestForBuildings()
    }

    // sends the request for the set of buildings on campus to the spark server and uses the set of buildings
    // on campus to create a list of options that the user can select in the building selection dropdowns.
    sendRequestForBuildings = async () => {
        try {
            // spark request
            let url:string = `http://localhost:4567/getBuildings`;
            let response = await fetch(url);
            if (!response.ok) {

                alert("Error!");
                return;
            }
            let parsed = await response.json() as Record<string, string>;

            // creating options from parsed info
            let options:JSX.Element[] = [<option value= "empty"> Select a Building</option>];
            for(let buildingEntry of Object.entries(parsed)) {
                options.push(<option value={buildingEntry[0]}>{`${buildingEntry[1]}(${buildingEntry[0]})`}</option>);
            }
            this.setState({
                buildingOptions:options
            });

        } catch (e) {
            console.log(e)
            alert("Error!");
        }
    }

    // allows the user to choose the start building
    startBuildingOnChange = (event:React.ChangeEvent<HTMLSelectElement>) => {
        let startBuildingShortName:string = event.target.value;

        this.setState({startShortName:startBuildingShortName});
    }

    // allows the user to choose the end building
    endBuildingOnChange = (event:React.ChangeEvent<HTMLSelectElement>) => {
        let endBuildingShortName:string = event.target.value;
        this.setState({endShortName:endBuildingShortName});
    }


    // draws the selected path
    drawOnClick = () => {
        if(this.state.startShortName === "empty") {
            alert("choose start building!")
            return;
        } else if(this.state.endShortName === "empty"){
            alert("choose end building!")
            return;
        } else if(this.state.startShortName === this.state.endShortName)  {
            alert("start and end building are equal")
            return;
        } else {
            this.props.onChangePath(this.state.startShortName, this.state.endShortName);
        }
    }

    // clears the path and resets the selected options on the dropdowns
    clearOnClick= () => {
        this.setState({startShortName:"empty",endShortName:"empty"})
        this.props.onChangeClear();
    }


    render() {
        return (
            <div id="path-selector">
                <label htmlFor="startBuildingPicker" >Start Building:</label>
                <select id="startBuildingPicker" onChange={this.startBuildingOnChange} value={this.state.startShortName}>
                    {this.state.buildingOptions}
                </select>

                <label htmlFor="endBuildingPicker">End Building:</label>
                <select  id="endBuildingPicker" onChange={this.endBuildingOnChange} value={this.state.endShortName} >
                    {this.state.buildingOptions}
                </select>
                <button onClick={this.drawOnClick}>Draw</button>
                <button onClick={this.clearOnClick}>Clear</button>
            </div>
        );
    }
}
export default PathSelector;
