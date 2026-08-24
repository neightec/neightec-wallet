--liquibase formatted sql
--changeset nsu:0
--comment: mock data - wallet user

insert into wallet_session_to_transaction (id, wallet_session_id, wallet_transaction_id) values ('7327a63c-0de8-497d-ad45-c536cb3c9fba'::uuid, 'f40f1143-067a-43a5-870f-dde93ef2f069'::uuid, 'fb066291-2113-45a1-b767-472335972e5d'::uuid);
insert into wallet_session_to_transaction (id, wallet_session_id, wallet_transaction_id) values ('59bcef13-6041-42f0-83dc-eabb1340631e'::uuid, 'f40f1143-067a-43a5-870f-dde93ef2f069'::uuid, '43d5f5f6-48f7-4611-872f-8e7da9f4e2a2'::uuid);
insert into wallet_session_to_transaction (id, wallet_session_id, wallet_transaction_id) values ('12f7a2c7-5e32-4a6f-a0ad-ac1e74245523'::uuid, 'f40f1143-067a-43a5-870f-dde93ef2f069'::uuid, '47a1933a-3610-4534-932e-f2b6a416129a'::uuid);
insert into wallet_session_to_transaction (id, wallet_session_id, wallet_transaction_id) values ('e5b42fa9-68dc-4b41-bfd1-b7212800a040'::uuid, 'f40f1143-067a-43a5-870f-dde93ef2f069'::uuid, '33948e70-f63e-4db1-a739-ab58a8849761'::uuid);
insert into wallet_session_to_transaction (id, wallet_session_id, wallet_transaction_id) values ('78adf536-0e6e-460c-b0ac-41145f48913e'::uuid, 'eac5d5e3-14eb-4f69-9b15-edbc5b1f6cbb'::uuid, 'ec689072-a458-40a9-b6e5-60aed5439463'::uuid);
