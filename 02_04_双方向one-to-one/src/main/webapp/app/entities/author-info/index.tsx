import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AuthorInfo from './author-info';
import AuthorInfoDetail from './author-info-detail';
import AuthorInfoUpdate from './author-info-update';
import AuthorInfoDeleteDialog from './author-info-delete-dialog';

const AuthorInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AuthorInfo />} />
    <Route path="new" element={<AuthorInfoUpdate />} />
    <Route path=":id">
      <Route index element={<AuthorInfoDetail />} />
      <Route path="edit" element={<AuthorInfoUpdate />} />
      <Route path="delete" element={<AuthorInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AuthorInfoRoutes;
