--liquibase formatted sql
--changeset nsu:0
--comment: mock data - wallet account

insert into neightec.wallet_account (id, name, balance, creation_timestamp, deletion_timestamp, is_archived) values ('3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c01'::uuid, 'Girokonto', 2450.75, '2026-05-16 05:30:00', null, false);
insert into neightec.wallet_account (id, name, balance, creation_timestamp, deletion_timestamp, is_archived) values ('3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c02'::uuid, 'Sparkonto', 10200.00, '2026-05-16 05:35:00', null, false);
insert into neightec.wallet_account (id, name, balance, creation_timestamp, deletion_timestamp, is_archived) values ('3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c03'::uuid, 'Kreditkarte', -320.40, '2026-05-17 09:12:00', null, false);
insert into neightec.wallet_account (id, name, balance, creation_timestamp, deletion_timestamp, is_archived) values ('3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c04'::uuid, 'Girokonto', 1830.10, '2026-05-08 06:10:00', null, false);
insert into neightec.wallet_account (id, name, balance, creation_timestamp, deletion_timestamp, is_archived) values ('3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c05'::uuid, 'Tagesgeld', 5600.00, '2026-05-08 06:15:00', null, false);
insert into neightec.wallet_account (id, name, balance, creation_timestamp, deletion_timestamp, is_archived) values ('3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c06'::uuid, 'Bargeld', 145.50, '2026-05-09 18:00:00', null, false);
insert into neightec.wallet_account (id, name, balance, creation_timestamp, deletion_timestamp, is_archived) values ('3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c07'::uuid, 'Girokonto', 980.00, '2025-09-28 09:00:00', null, false);
insert into neightec.wallet_account (id, name, balance, creation_timestamp, deletion_timestamp, is_archived) values ('3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c08'::uuid, 'Depot', 7400.25, '2025-10-02 14:20:00', null, false);
insert into neightec.wallet_account (id, name, balance, creation_timestamp, deletion_timestamp, is_archived) values ('3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c09'::uuid, 'Altes Sparkonto', 0.00, '2025-09-28 09:05:00', '2026-01-05 05:00:00', true);

--liquibase formatted sql
--changeset nsu:1
--comment: mock data - wallet session to account

insert into neightec.wallet_session_to_account (id, wallet_session_id, wallet_account_id) values ('7c2e9d41-5f3a-4b86-a1d0-6e4b2c8f9a01'::uuid, 'eac5d5e3-14eb-4f69-9b15-edbc5b1f6cbb'::uuid, '3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c01'::uuid);
insert into neightec.wallet_session_to_account (id, wallet_session_id, wallet_account_id) values ('7c2e9d41-5f3a-4b86-a1d0-6e4b2c8f9a02'::uuid, 'eac5d5e3-14eb-4f69-9b15-edbc5b1f6cbb'::uuid, '3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c02'::uuid);
insert into neightec.wallet_session_to_account (id, wallet_session_id, wallet_account_id) values ('7c2e9d41-5f3a-4b86-a1d0-6e4b2c8f9a03'::uuid, 'eac5d5e3-14eb-4f69-9b15-edbc5b1f6cbb'::uuid, '3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c03'::uuid);
insert into neightec.wallet_session_to_account (id, wallet_session_id, wallet_account_id) values ('7c2e9d41-5f3a-4b86-a1d0-6e4b2c8f9a04'::uuid, 'f40f1143-067a-43a5-870f-dde93ef2f069'::uuid, '3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c04'::uuid);
insert into neightec.wallet_session_to_account (id, wallet_session_id, wallet_account_id) values ('7c2e9d41-5f3a-4b86-a1d0-6e4b2c8f9a05'::uuid, 'f40f1143-067a-43a5-870f-dde93ef2f069'::uuid, '3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c05'::uuid);
insert into neightec.wallet_session_to_account (id, wallet_session_id, wallet_account_id) values ('7c2e9d41-5f3a-4b86-a1d0-6e4b2c8f9a06'::uuid, 'f40f1143-067a-43a5-870f-dde93ef2f069'::uuid, '3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c06'::uuid);
insert into neightec.wallet_session_to_account (id, wallet_session_id, wallet_account_id) values ('7c2e9d41-5f3a-4b86-a1d0-6e4b2c8f9a07'::uuid, 'ab9ffbc0-4cf5-403c-9482-d57f50970651'::uuid, '3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c07'::uuid);
insert into neightec.wallet_session_to_account (id, wallet_session_id, wallet_account_id) values ('7c2e9d41-5f3a-4b86-a1d0-6e4b2c8f9a08'::uuid, 'ab9ffbc0-4cf5-403c-9482-d57f50970651'::uuid, '3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c08'::uuid);
insert into neightec.wallet_session_to_account (id, wallet_session_id, wallet_account_id) values ('7c2e9d41-5f3a-4b86-a1d0-6e4b2c8f9a09'::uuid, 'ab9ffbc0-4cf5-403c-9482-d57f50970651'::uuid, '3a1f6c2e-8b4d-4e7a-9c15-2d6f8a0b1c09'::uuid);
