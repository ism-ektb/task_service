--liquibase formatted sql
--changeset ism:change-tables

ALTER TABLE tasks
    ADD epic_id BIGINT;

ALTER TABLE tasks
    ADD CONSTRAINT fk_epic FOREIGN KEY (epic_id)
        REFERENCES epics (id) ON DELETE CASCADE ;