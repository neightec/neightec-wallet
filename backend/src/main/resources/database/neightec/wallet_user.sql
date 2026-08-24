--liquibase formatted sql
--changeset nsu:0
--comment: mock data - wallet user

insert into neightec.wallet_user (id, username, wallet_session_id) values ('1d7a2420-96dd-4e1f-a820-e95abba624c1'::uuid, 'jpottinger0', 'eac5d5e3-14eb-4f69-9b15-edbc5b1f6cbb'::uuid);
insert into neightec.wallet_user (id, username, wallet_session_id) values ('cf2992e2-bad8-4d8a-b84f-ee97c094eced'::uuid, 'bjurek1', 'f40f1143-067a-43a5-870f-dde93ef2f069'::uuid);
insert into neightec.wallet_user (id, username, wallet_session_id) values ('80ac8042-efdb-4215-bef2-9371b2d1999a'::uuid, 'skillingback2', null);
insert into neightec.wallet_user (id, username, wallet_session_id) values ('4b48e2ab-2209-4958-830b-f0f10c2c7e89'::uuid, 'mtuckwell3', null);
insert into neightec.wallet_user (id, username, wallet_session_id) values ('f3d1498d-d0c1-44ac-8901-e5520a69e62a'::uuid, 'cnaire4', null);
insert into neightec.wallet_user (id, username, wallet_session_id) values ('0d17ffda-5ed5-4dc1-a123-caf5bdb4cd61'::uuid, 'ccamm5', null);
insert into neightec.wallet_user (id, username, wallet_session_id) values ('4782b859-2819-4284-aaed-3e31d97e3969'::uuid, 'ddarmody6', null);
insert into neightec.wallet_user (id, username, wallet_session_id) values ('b4c1d1eb-366f-425f-9507-d9e347b81e0e'::uuid, 'anegal7', null);
insert into neightec.wallet_user (id, username, wallet_session_id) values ('474e2280-de94-4ee6-be95-4a0dc126c942'::uuid, 'apauleit8', null);
insert into neightec.wallet_user (id, username, wallet_session_id) values ('8046425d-6a16-47f4-8495-a7f4af8b8d93'::uuid, 'ahierro9', null);
