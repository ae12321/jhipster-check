import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAuthor } from 'app/shared/model/author.model';
import { getEntities } from './author.reducer';

export const Author = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const authorList = useAppSelector(state => state.author.entities);
  const loading = useAppSelector(state => state.author.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="author-heading" data-cy="AuthorHeading">
        Authors
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/author/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Author を追加
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {authorList && authorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Col A</th>
                <th>Col B</th>
                <th>Col C</th>
                <th>Col D</th>
                <th>Col E</th>
                <th>Col F</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {authorList.map((author, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/author/${author.id}`} color="link" size="sm">
                      {author.id}
                    </Button>
                  </td>
                  <td>{author.colA}</td>
                  <td>{author.colB}</td>
                  <td>{author.colC}</td>
                  <td>{author.colD ? <TextFormat type="date" value={author.colD} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{author.colE ? 'true' : 'false'}</td>
                  <td>{author.colF}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/author/${author.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">表示</span>
                      </Button>
                      <Button tag={Link} to={`/author/${author.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">編集</span>
                      </Button>
                      <Button tag={Link} to={`/author/${author.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">削除</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Authors found</div>
        )}
      </div>
    </div>
  );
};

export default Author;
