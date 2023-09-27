import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAuthor } from 'app/shared/model/author.model';
import { getEntities as getAuthors } from 'app/entities/author/author.reducer';
import { IAuthorInfo } from 'app/shared/model/author-info.model';
import { getEntity, updateEntity, createEntity, reset } from './author-info.reducer';

export const AuthorInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const authors = useAppSelector(state => state.author.entities);
  const authorInfoEntity = useAppSelector(state => state.authorInfo.entity);
  const loading = useAppSelector(state => state.authorInfo.loading);
  const updating = useAppSelector(state => state.authorInfo.updating);
  const updateSuccess = useAppSelector(state => state.authorInfo.updateSuccess);

  const handleClose = () => {
    navigate('/author-info');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAuthors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...authorInfoEntity,
      ...values,
      author: authors.find(it => it.id.toString() === values.author.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...authorInfoEntity,
          author: authorInfoEntity?.author?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myappApp.authorInfo.home.createOrEditLabel" data-cy="AuthorInfoCreateUpdateHeading">
            Author Infoを追加または編集
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="author-info-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Age" id="author-info-age" name="age" data-cy="age" type="text" />
              <ValidatedField label="Address" id="author-info-address" name="address" data-cy="address" type="text" />
              <ValidatedField id="author-info-author" name="author" data-cy="author" label="Author" type="select">
                <option value="" key="0" />
                {authors
                  ? authors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/author-info" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">戻る</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; 保存
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AuthorInfoUpdate;
