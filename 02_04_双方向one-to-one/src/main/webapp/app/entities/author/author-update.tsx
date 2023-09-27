import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAuthorInfo } from 'app/shared/model/author-info.model';
import { getEntities as getAuthorInfos } from 'app/entities/author-info/author-info.reducer';
import { IAuthor } from 'app/shared/model/author.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { getEntity, updateEntity, createEntity, reset } from './author.reducer';

export const AuthorUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const authorInfos = useAppSelector(state => state.authorInfo.entities);
  const authorEntity = useAppSelector(state => state.author.entity);
  const loading = useAppSelector(state => state.author.loading);
  const updating = useAppSelector(state => state.author.updating);
  const updateSuccess = useAppSelector(state => state.author.updateSuccess);
  const genderValues = Object.keys(Gender);

  const handleClose = () => {
    navigate('/author');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAuthorInfos({}));
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
      authorInfo: authorInfos.find(it => it.id.toString() === values.authorInfo.toString()),
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
          gender: 'MALE',
          ...authorEntity,
          authorInfo: authorEntity?.authorInfo?.id,
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
              <ValidatedField label="Name" id="author-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Gender" id="author-gender" name="gender" data-cy="gender" type="select">
                {genderValues.map(gender => (
                  <option value={gender} key={gender}>
                    {gender}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="author-authorInfo" name="authorInfo" data-cy="authorInfo" label="Author Info" type="select">
                <option value="" key="0" />
                {authorInfos
                  ? authorInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
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
