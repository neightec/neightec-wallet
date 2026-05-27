--liquibase formatted sql
--changeset nsu:0
--comment: mock data - file types
INSERT INTO neight.file_types (id, name, extension, is_valid) VALUES ('ac100175-8046-1131-8180-461139420000'::uuid,'DOCX','docx', true);
INSERT INTO neight.file_types (id, name, extension, is_valid) VALUES ('452a9bc5-fb75-4c18-9c93-55d986dd18dc'::uuid,'PPTX','pptx', true);
INSERT INTO neight.file_types (id, name, extension, is_valid) VALUES ('c62de85b-ba12-4794-bac8-fc2ce8df8af0'::uuid,'XLSX','xlsx', true);
INSERT INTO neight.file_types (id, name, extension, is_valid) VALUES ('bb6029ba-ad01-40f9-b683-388c81468dc7'::uuid,'JAVA','java', true);
INSERT INTO neight.file_types (id, name, extension, is_valid) VALUES ('bb6029ba-ad02-20f4-b683-488c81468dc7'::uuid, 'TXT', 'txt', true);