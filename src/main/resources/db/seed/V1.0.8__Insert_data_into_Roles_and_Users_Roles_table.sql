INSERT INTO Roles (role, allowed_read_resources,allowed_create_resources,
                   allowed_update_resources, allowed_delete_resources) VALUES
('admin', '\', '\', '\', '\'),
('manager', '\', '\', '\', ''),
('dealer', '\restaurants,\reviews,\reservations', '\restaurants', '\reservations', ''),
('user', '\restaurants,\reviews,\reservations', '\reviews,\reservations', '\reviews,\reservations', '')
;
commit;

INSERT INTO Authority (role, allowed_resource, allowed_read, allowed_create, allowed_update, allowed_delete) VALUES
('admin', '\', 'Y', 'Y', 'Y', 'Y'),
('manager', '\', 'Y', 'Y', 'Y', 'N'),
('dealer', '\restaurants', 'Y', 'Y', 'Y', 'N'),
('dealer', '\reviews', 'Y', 'N', 'Y', 'N'),
('dealer', '\reservations',  'Y', 'N', 'Y', 'N'),
('dealer', '\restaurants', 'Y', 'N', 'N', 'N'),
('user', '\reviews', 'Y', 'Y', 'Y', 'N'),
('user', '\reservations', 'Y', 'Y', 'Y', 'N')
;
commit;

INSERT INTO Users_Roles (user_id, role_id) VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(1,2),
(1,3),
(1,4)
;
commit;