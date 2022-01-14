--BUILT IN DATA

INSERT INTO CLIENT (id, username, first_name, last_name, telephone, e_mail) VALUES (1, 'user1', 'bob', 'johns', '+39329123456', 'bobjohns@tui.com');
INSERT INTO CLIENT (id, username, first_name, last_name, telephone, e_mail) VALUES (2, 'user2', 'tom', 'dan', '+39329654321', 'tomdan@tui.com');

insert into ADDRESS (id, street, postcode, city, country) values (1, 'viale monza 1', '20120', 'milano', 'italy');
insert into ADDRESS (id, street, postcode, city, country) values (2, 'corso cavour 20', '70100', 'bari', 'italy');
insert into ADDRESS (id, street, postcode, city, country) values (3, 'plaza mayor', '2802', 'madrid', 'spain');

INSERT INTO PILOTES_ORDER (id, address_id, quantity, order_total, creation_date, client_id) VALUES (1, 1, 5, 6.65, parsedatetime('17-09-2020 18:47:00', 'dd-MM-yyyy hh:mm:ss'), 1);
INSERT INTO PILOTES_ORDER (id, address_id, quantity, order_total, creation_date, client_id) VALUES (2, 2, 10, 13.30, parsedatetime('22-11-2020 19:47:52', 'dd-MM-yyyy hh:mm:ss'), 1);
INSERT INTO PILOTES_ORDER (id, address_id, quantity, order_total, creation_date, client_id) VALUES (3,  3, 15, 19.95, parsedatetime('23-10-2020 17:00:00', 'dd-MM-yyyy hh:mm:ss'), 2);
