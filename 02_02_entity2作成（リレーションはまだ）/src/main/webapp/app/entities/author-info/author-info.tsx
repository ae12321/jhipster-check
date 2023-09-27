import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAuthorInfo } from 'app/shared/model/author-info.model';
import { getEntities } from './author-info.reducer';

export const AuthorInfo = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const authorInfoList = useAppSelector(state => state.authorInfo.entities);
  const loading = useAppSelector(state => state.authorInfo.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="author-info-heading" data-cy="AuthorInfoHeading">
        AuthorInfos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/author-info/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Author Info を追加
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {authorInfoList && authorInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Age</th>
                <th>Address</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {authorInfoList.map((authorInfo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/author-info/${authorInfo.id}`} color="link" size="sm">
                      {authorInfo.id}
                    </Button>
                  </td>
                  <td>{authorInfo.age}</td>
                  <td>{authorInfo.address}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/author-info/${authorInfo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">表示</span>
                      </Button>
                      <Button tag={Link} to={`/author-info/${authorInfo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">編集</span>
                      </Button>
                      <Button tag={Link} to={`/author-info/${authorInfo.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">削除</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Author Infos found</div>
        )}
      </div>
    </div>
  );
};

export default AuthorInfo;
