--liquibase formatted sql
--changeset nsu:0
--comment: mock data - wallet transaction currency, type and category
INSERT INTO neightec.wallet_transaction_type (id, name) values ('3c733ec2-cd26-47c1-9b5a-9d743cc4ec48'::uuid, 'Einnahmen');
INSERT INTO neightec.wallet_transaction_type (id, name) values ('3b4e9c6d-b318-46fa-ab96-435632942d35'::uuid, 'Ausgaben');
INSERT INTO neightec.wallet_transaction_type (id, name) values ('3f0c995b-1aae-4ab9-b0d2-929505bb9471'::uuid, 'Ersparnisse');

INSERT INTO neightec.wallet_transaction_category (id, name, icon) values ('64fd5b51-1be5-4ca0-a3f4-64333f6d2603', 'Haus', 'haus');
INSERT INTO neightec.wallet_transaction_category (id, name, icon) values ('abeba594-c662-4fb4-a911-4eac8317783e', 'Lebensmittel', 'seife');
INSERT INTO neightec.wallet_transaction_category (id, name, icon) values ('6377b2b4-a19e-4095-abe8-cd0632561ba1', 'Outdoor', 'seife');
INSERT INTO neightec.wallet_transaction_category (id, name, icon) values ('4bc33add-c744-4124-94e5-d191aa37c388', 'Auto', 'auto');

INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('ef812f6d-5b5c-45d7-a0d9-ea924a367ea9', 'Ruble', 'RUB');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('1eb8ef41-4241-4b41-846b-692a0cfa6b3c', 'Peso', 'COP');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('d99b4478-189b-4538-a785-07832e642553', 'Koruna', 'CZK');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('60f7d363-55ba-4a67-a86a-54b2abc89fc5', 'Peso', 'MXN');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('7250a9a1-9854-4108-95ac-e6a92aae2e05', 'Dong', 'VND');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('fe344459-6eb2-48e7-b823-fbeebed2929a', 'Krona', 'SEK');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('6b2ed28b-d5f2-41a5-a8ae-94ef23bd9d13', 'Naira', 'NGN');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('bb7f0abf-2b49-48aa-9016-3a1661114323', 'Peso', 'PHP');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('4552b95c-5b8a-456c-8abb-1b06b8672c63', 'Pound', 'SYP');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('54cf1533-145f-4c9b-8f1f-8b4c36fb8945', 'Hryvnia', 'UAH');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('6837ca0c-33d1-4fc6-a01f-11d03c0ebe70', 'Euro', 'EUR');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('1efee4a9-7a0c-430d-8440-a78523825253', 'Manat', 'AZN');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('657275ce-29a7-429e-9d89-fc342593bddc', 'Real', 'BRL');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('0ca79ea8-73b4-409d-b123-b7e7f5bf6deb', 'Yuan Renminbi', 'CNY');
INSERT INTO neightec.wallet_transaction_currency (id, currency, currency_code) values ('1521b929-ad6a-4597-98ba-ca260eb74328', 'Dollar', 'USD');
