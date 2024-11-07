-- insert some trackers
INSERT INTO tracker(id, type) VALUES (1, 'CAT_SMALL');
INSERT INTO tracker(id, type) VALUES (2, 'CAT_LARGE');
INSERT INTO tracker(id, type) VALUES (3, 'DOG_SMALL');
INSERT INTO tracker(id, type) VALUES (4, 'DOG_MEDIUM');
INSERT INTO tracker(id, type) VALUES (5, 'DOG_LARGE');

-- insert some cats
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone) VALUES(1, 1, 1, 'CAT', false);
INSERT INTO CAT(id, lost_tracker) VALUES(1, false);
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone) VALUES(2, 1, 2, 'CAT', false);
INSERT INTO CAT(id, lost_tracker) VALUES(2, false);

-- insert some dogs
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone) VALUES(3, 2, 3, 'DOG', false);
INSERT INTO DOG(id) VALUES(3);
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone) VALUES(4, 2, 4, 'DOG', false);
INSERT INTO DOG(id) VALUES(4);
INSERT INTO PET(id, owner_id, tracker_id, type, in_zone) VALUES(5, 3, 5, 'DOG', false);
INSERT INTO DOG(id) VALUES(5);

