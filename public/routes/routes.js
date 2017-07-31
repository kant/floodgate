import React from 'react';
import { Route, IndexRoute } from 'react-router';

import ReactApp from '../components/reactApp.react';
import ReindexController from '../components/reindexController.react.js';
import Register from '../components/register.react.js';
import Home from '../components/home.react';
import BulkReindexController from '../components/bulkReindexController.react.js';

export default [
    <Route path="/" component={ReactApp}>
        <IndexRoute component={BulkReindexController}/>
        <Route name="bulk reindexer" path="/bulk" component={BulkReindexController} />
        <Route name="reindex" path="/reindex/:id/environment/:environment" component={ReindexController} />
        <Route name="register" path="/register" component={Register} />
    </Route>
];