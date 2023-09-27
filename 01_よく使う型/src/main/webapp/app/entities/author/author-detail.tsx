import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './author.reducer';

export const AuthorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const authorEntity = useAppSelector(state => state.author.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="authorDetailsHeading">Author</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{authorEntity.id}</dd>
          <dt>
            <span id="colA">Col A</span>
          </dt>
          <dd>{authorEntity.colA}</dd>
          <dt>
            <span id="colB">Col B</span>
          </dt>
          <dd>{authorEntity.colB}</dd>
          <dt>
            <span id="colC">Col C</span>
          </dt>
          <dd>{authorEntity.colC}</dd>
          <dt>
            <span id="colD">Col D</span>
          </dt>
          <dd>{authorEntity.colD ? <TextFormat value={authorEntity.colD} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="colE">Col E</span>
          </dt>
          <dd>{authorEntity.colE ? 'true' : 'false'}</dd>
          <dt>
            <span id="colF">Col F</span>
          </dt>
          <dd>{authorEntity.colF}</dd>
        </dl>
        <Button tag={Link} to="/author" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">戻る</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/author/${authorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">編集</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AuthorDetail;
