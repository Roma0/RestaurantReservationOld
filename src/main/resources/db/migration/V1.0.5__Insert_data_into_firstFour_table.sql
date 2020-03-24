SET timezone = 'Etc/UTC';

insert into restaurants (name, description, address, open_time, close_time, phone ) values
('Agora Tysons', 'Mediterranean, Cocktail Bars, Greek', '7911 Westpark Dr, McLean, VA 22102', '11:00', '22:00', '7036638737'),
('Banh Mi DC Sandwich', 'Vietnamese, Delis, Sandwiches', '3103 Graham Rd, Ste C, Falls Church, VA 22044', '8:00', '20:00','7032059300'),
('Hot N Juicy Crawfish', 'Cajun/Creole, Seafood', '116 W Broad St, Falls Church, VA 22046', '12:00', '22:00','7039928700'),
('Loving Hut', 'Vegan, Vietnamese', '2842 Rogers Dr, Falls Church, VA 22042','11:00','21:00','7039425622')
;
commit;

insert into users (name, first_name, last_name, email, password, phone) values
('Han', 'Han','Wang','hanwang@gmail.com', 'wanghan1234', '5417543010'),
('Wenjia','Wenjia', 'Wang', 'wenjia@hotmail.com', 'wenjiawenjia123', '7093348974'),
('Sugar', 'Ruichen', 'Zhou', 'ruichen@gmail.com', 'ruichensugar789', '7348902345'),
('Hangbo','Hangbo', 'Zhang', 'hangbo@outlook.com', 'hangbozhang543', '2025158931')
;
commit;

insert into reservations (reserved_time, num_persons, reserved_status, restaurant_id, user_id) values
(('2020-03-24 11:30')::timestamp, 2, 1, 1, 1 ),
(('2020-03-26 12:00')::timestamp, 3, 1, 2, 2 ),
(('2020-03-28 19:00')::timestamp, 2, 0, 3, 3 ),
(('2020-03-27 17:30')::timestamp, 2, 2, 1, 1 ),
(('2020-03-27 17:30')::timestamp, 2, 1, 2, 1 )
;
commit;

insert into reviews (description, restaurant_id, user_id) values
('This is a new location in Tysons & I must say the ambiance is very welcoming & modern. The overall vibe of this place is great.', 1, 1),
('My fav banh mi spot, hands down! But 5 get 1, gets me every time! i''d get it with the veggies on the side to avoid soggy bread.', 2, 1),
('I got the tofu banh mi. Wouldn''t get the tofu again just because it was deep fried and a little tasteless.', 2, 2)
;
commit;