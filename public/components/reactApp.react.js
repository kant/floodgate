import React from 'react';
import Header from './header.react';

export default class ReactApp extends React.Component {
    constructor(props) {
        super(props);
    }

    render () {
        return (
            <div className="container">
                <Header />
                {this.props.children}
            </div>
        );
    }
}