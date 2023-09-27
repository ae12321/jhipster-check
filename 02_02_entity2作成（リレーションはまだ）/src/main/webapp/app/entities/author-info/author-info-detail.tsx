import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './author-info.reducer';

export const AuthorInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const authorInfoEntity = useAppSelector(state => state.authorInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="authorInfoDetailsHeading">Author Info</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{authorInfoEntity.id}</dd>
          <dt>
            <span id="age">Age</span>
          </dt>
          <dd>{authorInfoEntity.age}</dd>
          <dt>
            <span id="address">Address</span>
          </dt>
          <dd>{authorInfoEntity.address}</dd>
        </dl>
        <Button tag={Link} to="/author-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">戻る</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/author-info/${authorInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">編集</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AuthorInfoDetail;
