import React, {Component} from 'react';


interface PathSelectorProps {
    onChangePath(startShortName:string,endShortName:string):void
    onChangeClear():void
}

interface PathSelectorState {
    buildingOptions:JSX.Element[],
    startShortName:string,
    endShortName:string
}

class PathSelector extends Component<PathSelectorProps,PathSelectorState> {
    constructor(props: PathSelectorProps) {
        super(props);
        this.state= {
            buildingOptions: [],
            startShortName: "empty",
            endShortName: "empty"
        }
    }

    componentDidMount() {
        this.sendRequestForBuildings()
    }

    sendRequestForBuildings = async () => {
        try {
            let url:string = `http://localhost:4567/getBuildings`;
            let response = await fetch(url);
            if (!response.ok) {

                alert("Error!");
                return;
            }
            let parsed = await response.json() as Record<string, string>;

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

    startBuildingOnChange = (event:React.ChangeEvent<HTMLSelectElement>) => {
        let startBuildingShortName:string = event.target.value;

        this.setState({startShortName:startBuildingShortName});
    }

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

    // clears the path
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
