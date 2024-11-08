-- insert some trackers
INSERT INTO tracker(id, type) VALUES ('c8beb646-8f39-462b-841e-1993cdc1a957', 'CAT_SMALL');
INSERT INTO tracker(id, type) VALUES ('770cc95c-1ffc-4463-8703-994bb6783dd2', 'CAT_LARGE');
INSERT INTO tracker(id, type) VALUES ('83ea1d7e-d395-4028-b02a-dd493becd8c6', 'DOG_SMALL');
INSERT INTO tracker(id, type) VALUES ('bdd7409c-14c0-4730-b706-e52556a90099', 'DOG_MEDIUM');
INSERT INTO tracker(id, type) VALUES ('912998e9-2372-4981-9d01-465ebc044da4', 'DOG_LARGE');

-- insert some cats
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone, lost_tracker)
VALUES ('d4d99907-c13e-41d3-be31-40b8742265ed', 1, 'c8beb646-8f39-462b-841e-1993cdc1a957', 'CAT', false, false);
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone, lost_tracker)
VALUES ('fe7d2b28-5689-479a-986a-b6e5de68954b', 1, '770cc95c-1ffc-4463-8703-994bb6783dd2', 'CAT', false, false);

-- insert some dogs
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone)
VALUES ('b5e7479a-1367-4a60-94c0-03c1acc4aa1f', 2, '83ea1d7e-d395-4028-b02a-dd493becd8c6', 'DOG', false);
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone)
VALUES ('fcdabe9a-37ed-426e-978a-98661272caf5', 2, 'bdd7409c-14c0-4730-b706-e52556a90099', 'DOG', false);
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone)
VALUES ('d71e49c2-20bf-404a-b967-0f7adad705d2', 3, '912998e9-2372-4981-9d01-465ebc044da4', 'DOG', false);
