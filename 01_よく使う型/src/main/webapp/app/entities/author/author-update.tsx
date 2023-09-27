import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAuthor } from 'app/shared/model/author.model';
import { getEntity, updateEntity, createEntity, reset } from './author.reducer';

export const AuthorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const authorEntity = useAppSelector(state => state.author.entity);
  const loading = useAppSelector(state => state.author.loading);
  const updating = useAppSelector(state => state.author.updating);
  const updateSuccess = useAppSelector(state => state.author.updateSuccess);

  const handleClose = () => {
    navigate('/author');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...authorEntity,
      ...values,
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
          ...authorEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myappApp.author.home.createOrEditLabel" data-cy="AuthorCreateUpdateHeading">
            Authorを追加または編集
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="author-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Col A"
                id="author-colA"
                name="colA"
                data-cy="colA"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 0, message: 'This field is required to be at least 0 characters.' },
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Col B"
                id="author-colB"
                name="colB"
                data-cy="colB"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be more than 0.' },
                  max: { value: 100, message: 'This field cannot be more than 100.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Col C"
                id="author-colC"
                name="colC"
                data-cy="colC"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  min: { value: 0, message: 'This field should be more than 0.' },
                  max: { value: 100, message: 'This field cannot be more than 100.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField label="Col D" id="author-colD" name="colD" data-cy="colD" type="date" />
              <ValidatedField label="Col E" id="author-colE" name="colE" data-cy="colE" check type="checkbox" />
              <ValidatedField label="Col F" id="author-colF" name="colF" data-cy="colF" type="textarea" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/author" replace color="info">
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

export default AuthorUpdate;
